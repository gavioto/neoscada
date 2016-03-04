/*******************************************************************************
 * Copyright (c) 2013, 2014 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.scada.configuration.world.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.Disposable;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.scada.configuration.world.WorldPackage;
import org.eclipse.scada.configuration.world.util.WorldAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class WorldItemProviderAdapterFactory extends WorldAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender
{
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier ();

    /**
     * This keeps track of all the item providers created, so that they can be {@link #dispose disposed}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Disposable disposable = new Disposable ();

    /**
     * This helps manage the child creation extenders.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager ( WorldEditPlugin.INSTANCE, WorldPackage.eNS_URI );

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<Object> ();

    /**
     * This constructs an instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorldItemProviderAdapterFactory ()
    {
        supportedTypes.add ( IEditingDomainItemProvider.class );
        supportedTypes.add ( IStructuredItemContentProvider.class );
        supportedTypes.add ( ITreeItemContentProvider.class );
        supportedTypes.add ( IItemLabelProvider.class );
        supportedTypes.add ( IItemPropertySource.class );
        supportedTypes.add ( ITableItemLabelProvider.class );
        supportedTypes.add ( IItemStyledLabelProvider.class );
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.World} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorldItemProvider worldItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.World}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createWorldAdapter ()
    {
        if ( worldItemProvider == null )
        {
            worldItemProvider = new WorldItemProvider ( this );
        }

        return worldItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.ApplicationNode} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ApplicationNodeItemProvider applicationNodeItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.ApplicationNode}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createApplicationNodeAdapter ()
    {
        if ( applicationNodeItemProvider == null )
        {
            applicationNodeItemProvider = new ApplicationNodeItemProvider ( this );
        }

        return applicationNodeItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.Endpoint} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EndpointItemProvider endpointItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.Endpoint}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createEndpointAdapter ()
    {
        if ( endpointItemProvider == null )
        {
            endpointItemProvider = new EndpointItemProvider ( this );
        }

        return endpointItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.ExecDriver} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExecDriverItemProvider execDriverItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.ExecDriver}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createExecDriverAdapter ()
    {
        if ( execDriverItemProvider == null )
        {
            execDriverItemProvider = new ExecDriverItemProvider ( this );
        }

        return execDriverItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.HandlerPriorityRule} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HandlerPriorityRuleItemProvider handlerPriorityRuleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.HandlerPriorityRule}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createHandlerPriorityRuleAdapter ()
    {
        if ( handlerPriorityRuleItemProvider == null )
        {
            handlerPriorityRuleItemProvider = new HandlerPriorityRuleItemProvider ( this );
        }

        return handlerPriorityRuleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.MasterHandlerPriorities} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MasterHandlerPrioritiesItemProvider masterHandlerPrioritiesItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.MasterHandlerPriorities}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createMasterHandlerPrioritiesAdapter ()
    {
        if ( masterHandlerPrioritiesItemProvider == null )
        {
            masterHandlerPrioritiesItemProvider = new MasterHandlerPrioritiesItemProvider ( this );
        }

        return masterHandlerPrioritiesItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.Options} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OptionsItemProvider optionsItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.Options}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createOptionsAdapter ()
    {
        if ( optionsItemProvider == null )
        {
            optionsItemProvider = new OptionsItemProvider ( this );
        }

        return optionsItemProvider;
    }

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.UsernamePasswordCredentials}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createUsernamePasswordCredentialsAdapter ()
    {
        return new UsernamePasswordCredentialsItemProvider ( this );
    }

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.PasswordCredentials}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createPasswordCredentialsAdapter ()
    {
        return new PasswordCredentialsItemProvider ( this );
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.ExternalNode} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExternalNodeItemProvider externalNodeItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.ExternalNode}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createExternalNodeAdapter ()
    {
        if ( externalNodeItemProvider == null )
        {
            externalNodeItemProvider = new ExternalNodeItemProvider ( this );
        }

        return externalNodeItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.PropertyEntry} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PropertyEntryItemProvider propertyEntryItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.PropertyEntry}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createPropertyEntryAdapter ()
    {
        if ( propertyEntryItemProvider == null )
        {
            propertyEntryItemProvider = new PropertyEntryItemProvider ( this );
        }

        return propertyEntryItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.ContainedServiceBinding} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ContainedServiceBindingItemProvider containedServiceBindingItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.ContainedServiceBinding}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createContainedServiceBindingAdapter ()
    {
        if ( containedServiceBindingItemProvider == null )
        {
            containedServiceBindingItemProvider = new ContainedServiceBindingItemProvider ( this );
        }

        return containedServiceBindingItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.ReferencedServiceBinding} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ReferencedServiceBindingItemProvider referencedServiceBindingItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.ReferencedServiceBinding}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createReferencedServiceBindingAdapter ()
    {
        if ( referencedServiceBindingItemProvider == null )
        {
            referencedServiceBindingItemProvider = new ReferencedServiceBindingItemProvider ( this );
        }

        return referencedServiceBindingItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.GenericSettingsContainer} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GenericSettingsContainerItemProvider genericSettingsContainerItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.GenericSettingsContainer}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createGenericSettingsContainerAdapter ()
    {
        if ( genericSettingsContainerItemProvider == null )
        {
            genericSettingsContainerItemProvider = new GenericSettingsContainerItemProvider ( this );
        }

        return genericSettingsContainerItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.GenericDatabaseSettings} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GenericDatabaseSettingsItemProvider genericDatabaseSettingsItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.GenericDatabaseSettings}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createGenericDatabaseSettingsAdapter ()
    {
        if ( genericDatabaseSettingsItemProvider == null )
        {
            genericDatabaseSettingsItemProvider = new GenericDatabaseSettingsItemProvider ( this );
        }

        return genericDatabaseSettingsItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.scada.configuration.world.PostgresDatabaseSettings} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PostgresDatabaseSettingsItemProvider postgresDatabaseSettingsItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.scada.configuration.world.PostgresDatabaseSettings}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createPostgresDatabaseSettingsAdapter ()
    {
        if ( postgresDatabaseSettingsItemProvider == null )
        {
            postgresDatabaseSettingsItemProvider = new PostgresDatabaseSettingsItemProvider ( this );
        }

        return postgresDatabaseSettingsItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ComposeableAdapterFactory getRootAdapterFactory ()
    {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory ();
    }

    /**
     * This sets the composed adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParentAdapterFactory ( ComposedAdapterFactory parentAdapterFactory )
    {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isFactoryForType ( Object type )
    {
        return supportedTypes.contains ( type ) || super.isFactoryForType ( type );
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter adapt ( Notifier notifier, Object type )
    {
        return super.adapt ( notifier, this );
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object adapt ( Object object, Object type )
    {
        if ( isFactoryForType ( type ) )
        {
            Object adapter = super.adapt ( object, type );
            if ( ! ( type instanceof Class<?> ) || ( ( (Class<?>)type ).isInstance ( adapter ) ) )
            {
                return adapter;
            }
        }

        return null;
    }

    /**
     * Associates an adapter with a notifier via the base implementation, then records it to ensure it will be disposed.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void associate ( Adapter adapter, Notifier target )
    {
        super.associate ( adapter, target );
        if ( adapter != null )
        {
            disposable.add ( adapter );
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public List<IChildCreationExtender> getChildCreationExtenders ()
    {
        return childCreationExtenderManager.getChildCreationExtenders ();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Collection<?> getNewChildDescriptors ( Object object, EditingDomain editingDomain )
    {
        return childCreationExtenderManager.getNewChildDescriptors ( object, editingDomain );
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceLocator getResourceLocator ()
    {
        return childCreationExtenderManager;
    }

    /**
     * This adds a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void addListener ( INotifyChangedListener notifyChangedListener )
    {
        changeNotifier.addListener ( notifyChangedListener );
    }

    /**
     * This removes a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void removeListener ( INotifyChangedListener notifyChangedListener )
    {
        changeNotifier.removeListener ( notifyChangedListener );
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void fireNotifyChanged ( Notification notification )
    {
        changeNotifier.fireNotifyChanged ( notification );

        if ( parentAdapterFactory != null )
        {
            parentAdapterFactory.fireNotifyChanged ( notification );
        }
    }

    /**
     * This disposes all of the item providers created by this factory. 
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void dispose ()
    {
        disposable.dispose ();
    }

}
