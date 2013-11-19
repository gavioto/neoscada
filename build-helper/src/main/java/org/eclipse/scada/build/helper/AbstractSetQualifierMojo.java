/*******************************************************************************
 * Copyright (c) 2013 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.scada.build.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Manifest;

import org.apache.maven.model.Model;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Abstract set qualifier
 * 
 * @author Jens Reimann
 * @since 0.0.1
 */
public abstract class AbstractSetQualifierMojo extends AbstractHelperMojo
{
    /**
     * A set of properties that should be set to the qualifier value if already
     * present
     * 
     * @parameter expression="${qualifierProperties}"
     */
    private Set<String> qualifierProperties;

    /**
     * A set of properties that should overridden if already present
     * 
     * @parameter expression="${additionalProperties}"
     */
    private final Map<String, String> additionalProperties = new HashMap<String, String> ();

    /**
     * Perform a dry run
     * 
     * @parameter expression="${dryRun}"
     */
    private boolean dryRun;

    private ChangeManager changeManager;

    protected abstract String getQualifier ();

    public void setAdditionalProperties ( final String string )
    {
        getLog ().debug ( "Set property: " + string );
        addAdditionalProperties ( string );
    }

    public void addAdditionalProperties ( final String string )
    {
        getLog ().debug ( "Adding property: " + string );
        final String[] tok = string.split ( "=", 2 );
        if ( tok.length == 1 )
        {
            this.additionalProperties.put ( tok[0], null );
        }
        else
        {
            this.additionalProperties.put ( tok[0], tok[1] );
        }
    }

    public synchronized void execute () throws MojoExecutionException
    {
        this.changeManager = new ChangeManager ( getLog () );

        try
        {
            getLog ().info ( "Overwriting qualifier properties: " + this.qualifierProperties );
            getLog ().info ( "Overwriting properties: " + this.additionalProperties );

            if ( this.dryRun )
            {
                getLog ().info ( "This is a dry run" );
            }

            final Collection<MavenProject> projects = PomHelper.expandProjects ( getReactorProjects () );

            getLog ().info ( String.format ( "Processing %s modules", projects.size () ) );

            for ( final MavenProject project : projects )
            {
                getLog ().debug ( String.format ( " -> %s", project ) );
                process ( projects, project );
            }

            if ( !this.dryRun )
            {
                this.changeManager.applyAll ();
            }
        }
        catch ( final Exception e )
        {
            throw new MojoExecutionException ( "Failed to set qualifier", e );
        }
    }

    private void process ( final Collection<MavenProject> projects, final MavenProject project ) throws Exception
    {
        getLog ().debug ( "Processing: " + project + " / " + project.getVersion () );

        final String version = makeVersion ( project.getVersion () );

        addChange ( project.getFile (), new ModelModifier () {

            public boolean apply ( final Model model )
            {
                if ( version != null && !version.equals ( project.getVersion () ) )
                {
                    getLog ().info ( String.format ( "Update from %s to %s on project %s", project.getVersion (), version, project ) );
                    model.setVersion ( version );
                    return true;
                }
                return false;
            }
        } );

        addChange ( project.getFile (), new ModelModifier () {

            public boolean apply ( final Model model )
            {
                boolean change = false;
                final Properties p = model.getProperties ();

                getLog ().debug ( "Project Properties: " + p );

                final String qualifier = getQualifier ();

                for ( final String prop : AbstractSetQualifierMojo.this.qualifierProperties )
                {
                    if ( p.containsKey ( prop ) )
                    {
                        getLog ().info ( String.format ( "%s: Setting property - %s -> %s", project, prop, qualifier ) );
                        p.put ( prop, qualifier );
                        change = true;
                    }
                }

                for ( final Map.Entry<String, String> entry : AbstractSetQualifierMojo.this.additionalProperties.entrySet () )
                {
                    if ( p.containsKey ( entry.getKey () ) )
                    {
                        if ( entry.getValue () != null )
                        {
                            getLog ().info ( String.format ( "%s: Setting property - %s -> %s", project, entry.getKey (), entry.getValue () ) );
                            p.put ( entry.getKey (), entry.getValue () );
                            change = true;
                        }
                        else
                        {
                            getLog ().info ( String.format ( "%s: Removing property - %s", entry.getKey () ) );
                            p.remove ( entry.getKey () );
                            change = true;
                        }
                    }
                }

                return change;
            }

            @Override
            public String toString ()
            {
                return String.format ( "Change properties: " + project );
            };
        } );

        // visit all modules that have this project as a parent
        PomHelper.visitModulesWithParent ( projects, project, new VisitorChange ( this.changeManager ) {
            @Override
            protected boolean performChange ( final Model model )
            {
                getLog ().debug ( String.format ( "Update parent version in module: %s", model ) );
                model.getParent ().setVersion ( version );
                return true;
            }
        } );

        // this is only called when the version changed ... for now
        if ( !this.dryRun )
        {
            syncModule ( project, version );
        }
    }

    private void addChange ( final File file, final ModelModifier modelModifier )
    {
        this.changeManager.addChange ( file, modelModifier );
    }

    protected void syncModule ( final MavenProject project, final String version ) throws Exception
    {
        final String packaging = project.getPackaging ();
        getLog ().debug ( "Project type: " + packaging );
        if ( "eclipse-plugin".equals ( packaging ) )
        {
            handleBundle ( project, version );
        }
        else if ( "eclipse-feature".equals ( packaging ) )
        {
            handleFeature ( project, version );
        }
        else if ( "eclipse-repository".equals ( packaging ) )
        {
            handleRepository ( project, version );
        }
        else if ( "eclipse-test-plugin".equals ( packaging ) )
        {
            handleBundle ( project, version );
        }
    }

    private void handleRepository ( final MavenProject project, final String version ) throws Exception
    {
        final File file = new File ( project.getBasedir (), "category.xml" );
        if ( !file.exists () )
        {
            return;
        }

        final Document doc = XmlHelper.parse ( file );
        // XmlHelper.updateValue ( doc, "/site/feature/@version", version );

        for ( final Node node : XmlHelper.findNodes ( doc, "/site/feature" ) )
        {
            final Element ele = (Element)node;
            ele.setAttribute ( "version", version );
            final String featureId = ele.getAttribute ( "id" );
            ele.setAttribute ( "url", String.format ( "features/%s_%s.jar", featureId, version ) );
        }

        XmlHelper.write ( doc, file );
    }

    private void handleBundle ( final MavenProject project, final String version ) throws Exception
    {
        final File file = new File ( project.getBasedir (), "META-INF/MANIFEST.MF" );

        final Manifest manifest = new Manifest ( new FileInputStream ( file ) );

        // process

        manifest.getMainAttributes ().putValue ( "Bundle-Version", version );

        // write

        final FileOutputStream out = new FileOutputStream ( file );
        try
        {
            manifest.write ( out );
        }
        finally
        {
            out.close ();
        }
    }

    private void handleFeature ( final MavenProject project, final String version ) throws Exception
    {
        final File file = new File ( project.getBasedir (), "feature.xml" );
        final Document doc = XmlHelper.parse ( file );
        XmlHelper.updateValue ( doc, "/feature/@version", version );
        XmlHelper.write ( doc, file );
    }

    private String makeVersion ( final String version )
    {
        if ( version == null )
        {
            // no version at all
            return null;
        }

        final LinkedList<String> toks = new LinkedList<String> ( Arrays.asList ( version.split ( "(\\.|-)" ) ) );

        getLog ().debug ( "Version split: " + toks );

        while ( toks.size () > 3 )
        {
            toks.removeLast ();
        }
        while ( toks.size () < 3 )
        {
            toks.add ( "0" );
        }

        toks.add ( getQualifier () );

        return String.format ( "%s.%s.%s.%s", toks.pollFirst (), toks.pollFirst (), toks.pollFirst (), toks.pollFirst () );
    }

}
