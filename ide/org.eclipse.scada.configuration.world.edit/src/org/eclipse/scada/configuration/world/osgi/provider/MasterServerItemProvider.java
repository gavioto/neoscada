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
package org.eclipse.scada.configuration.world.osgi.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.scada.configuration.world.WorldPackage;
import org.eclipse.scada.configuration.world.osgi.MasterServer;
import org.eclipse.scada.configuration.world.osgi.OsgiFactory;
import org.eclipse.scada.configuration.world.osgi.OsgiPackage;
import org.eclipse.scada.configuration.world.osgi.profile.ProfileFactory;

/**
 * This is the item provider adapter for a {@link org.eclipse.scada.configuration.world.osgi.MasterServer} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MasterServerItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, ITableItemLabelProvider, IItemStyledLabelProvider
{
    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MasterServerItemProvider ( AdapterFactory adapterFactory )
    {
        super ( adapterFactory );
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors ( Object object )
    {
        if ( itemPropertyDescriptors == null )
        {
            super.getPropertyDescriptors ( object );

            addShortDescriptionPropertyDescriptor ( object );
            addNamePropertyDescriptor ( object );
            addCustomizationProfilePropertyDescriptor ( object );
            addSecurityConfigurationPropertyDescriptor ( object );
            addAeServerInformationPrefixPropertyDescriptor ( object );
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Short Description feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addShortDescriptionPropertyDescriptor ( Object object )
    {
        itemPropertyDescriptors.add ( createItemPropertyDescriptor ( ( (ComposeableAdapterFactory)adapterFactory ).getRootAdapterFactory (), getResourceLocator (), getString ( "_UI_Documentable_shortDescription_feature" ), //$NON-NLS-1$
                getString ( "_UI_PropertyDescriptor_description", "_UI_Documentable_shortDescription_feature", "_UI_Documentable_type" ), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                WorldPackage.Literals.DOCUMENTABLE__SHORT_DESCRIPTION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString ( "_UI_namingPropertyCategory" ), //$NON-NLS-1$
                null ) );
    }

    /**
     * This adds a property descriptor for the Name feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNamePropertyDescriptor ( Object object )
    {
        itemPropertyDescriptors.add ( createItemPropertyDescriptor ( ( (ComposeableAdapterFactory)adapterFactory ).getRootAdapterFactory (), getResourceLocator (), getString ( "_UI_NamedDocumentable_name_feature" ), //$NON-NLS-1$
                getString ( "_UI_PropertyDescriptor_description", "_UI_NamedDocumentable_name_feature", "_UI_NamedDocumentable_type" ), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                WorldPackage.Literals.NAMED_DOCUMENTABLE__NAME, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString ( "_UI_namingPropertyCategory" ), //$NON-NLS-1$
                null ) );
    }

    /**
     * This adds a property descriptor for the Customization Profile feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addCustomizationProfilePropertyDescriptor ( Object object )
    {
        itemPropertyDescriptors.add ( createItemPropertyDescriptor ( ( (ComposeableAdapterFactory)adapterFactory ).getRootAdapterFactory (), getResourceLocator (), getString ( "_UI_EquinoxApplication_customizationProfile_feature" ), //$NON-NLS-1$
                getString ( "_UI_PropertyDescriptor_description", "_UI_EquinoxApplication_customizationProfile_feature", "_UI_EquinoxApplication_type" ), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                OsgiPackage.Literals.EQUINOX_APPLICATION__CUSTOMIZATION_PROFILE, true, false, true, null, null, null ) );
    }

    /**
     * This adds a property descriptor for the Security Configuration feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSecurityConfigurationPropertyDescriptor ( Object object )
    {
        itemPropertyDescriptors.add ( createItemPropertyDescriptor ( ( (ComposeableAdapterFactory)adapterFactory ).getRootAdapterFactory (), getResourceLocator (), getString ( "_UI_EquinoxApplication_securityConfiguration_feature" ), //$NON-NLS-1$
                getString ( "_UI_PropertyDescriptor_description", "_UI_EquinoxApplication_securityConfiguration_feature", "_UI_EquinoxApplication_type" ), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                OsgiPackage.Literals.EQUINOX_APPLICATION__SECURITY_CONFIGURATION, true, false, true, null, null, null ) );
    }

    /**
     * This adds a property descriptor for the Ae Server Information Prefix feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAeServerInformationPrefixPropertyDescriptor ( Object object )
    {
        itemPropertyDescriptors.add ( createItemPropertyDescriptor ( ( (ComposeableAdapterFactory)adapterFactory ).getRootAdapterFactory (), getResourceLocator (), getString ( "_UI_MasterServer_aeServerInformationPrefix_feature" ), //$NON-NLS-1$
                getString ( "_UI_PropertyDescriptor_description", "_UI_MasterServer_aeServerInformationPrefix_feature", "_UI_MasterServer_type" ), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                OsgiPackage.Literals.MASTER_SERVER__AE_SERVER_INFORMATION_PREFIX, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null ) );
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures ( Object object )
    {
        if ( childrenFeatures == null )
        {
            super.getChildrenFeatures ( object );
            childrenFeatures.add ( OsgiPackage.Literals.EQUINOX_APPLICATION__CONNECTIONS );
            childrenFeatures.add ( OsgiPackage.Literals.EQUINOX_APPLICATION__EXPORTER );
            childrenFeatures.add ( OsgiPackage.Literals.EQUINOX_APPLICATION__CUSTOMIZATION_PROFILE );
            childrenFeatures.add ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__SUMMARY_GROUPS );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__ITEMS );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__MARKERS );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__MONITOR_POOLS );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__EVENT_POOLS );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__DATA_MAPPER );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__EXTERNAL_EVENT_MONITORS );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__EXTERNAL_EVENT_FILTERS );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__AVERAGES );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__MOVING_AVERAGES );
            childrenFeatures.add ( OsgiPackage.Literals.MASTER_SERVER__BUFFERED_VALUES );
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature ( Object object, Object child )
    {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature ( object, child );
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage ()
    {
        return true;
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText ( Object object )
    {
        return ( (StyledString)getStyledText ( object ) ).getString ();
    }

    /**
     * This returns the label styled text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getStyledText ( Object object )
    {
        String label = ( (MasterServer)object ).getName ();
        StyledString styledLabel = new StyledString ();
        if ( label == null || label.length () == 0 )
        {
            styledLabel.append ( getString ( "_UI_MasterServer_type" ), StyledString.Style.QUALIFIER_STYLER ); //$NON-NLS-1$
        }
        else
        {
            styledLabel.append ( getString ( "_UI_MasterServer_type" ), StyledString.Style.QUALIFIER_STYLER ).append ( " " + label ); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return styledLabel;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged ( Notification notification )
    {
        updateChildren ( notification );

        switch ( notification.getFeatureID ( MasterServer.class ) )
        {
            case OsgiPackage.MASTER_SERVER__SHORT_DESCRIPTION:
            case OsgiPackage.MASTER_SERVER__NAME:
            case OsgiPackage.MASTER_SERVER__AE_SERVER_INFORMATION_PREFIX:
                fireNotifyChanged ( new ViewerNotification ( notification, notification.getNotifier (), false, true ) );
                return;
            case OsgiPackage.MASTER_SERVER__CONNECTIONS:
            case OsgiPackage.MASTER_SERVER__EXPORTER:
            case OsgiPackage.MASTER_SERVER__CUSTOMIZATION_PROFILE:
            case OsgiPackage.MASTER_SERVER__MODULES:
            case OsgiPackage.MASTER_SERVER__SUMMARY_GROUPS:
            case OsgiPackage.MASTER_SERVER__ITEMS:
            case OsgiPackage.MASTER_SERVER__MARKERS:
            case OsgiPackage.MASTER_SERVER__MONITOR_POOLS:
            case OsgiPackage.MASTER_SERVER__EVENT_POOLS:
            case OsgiPackage.MASTER_SERVER__DATA_MAPPER:
            case OsgiPackage.MASTER_SERVER__EXTERNAL_EVENT_MONITORS:
            case OsgiPackage.MASTER_SERVER__EXTERNAL_EVENT_FILTERS:
            case OsgiPackage.MASTER_SERVER__AVERAGES:
            case OsgiPackage.MASTER_SERVER__MOVING_AVERAGES:
            case OsgiPackage.MASTER_SERVER__BUFFERED_VALUES:
                fireNotifyChanged ( new ViewerNotification ( notification, notification.getNotifier (), true, false ) );
                return;
        }
        super.notifyChanged ( notification );
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors ( Collection<Object> newChildDescriptors, Object object )
    {
        super.collectNewChildDescriptors ( newChildDescriptors, object );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__CONNECTIONS, OsgiFactory.eINSTANCE.createDataAccessConnection () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__CONNECTIONS, OsgiFactory.eINSTANCE.createAlarmsEventsConnection () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__CONNECTIONS, OsgiFactory.eINSTANCE.createSfpDataAccessConnection () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__EXPORTER, OsgiFactory.eINSTANCE.createDataAccessExporter () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__EXPORTER, OsgiFactory.eINSTANCE.createAlarmsEventsExporter () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__EXPORTER, OsgiFactory.eINSTANCE.createHistoricalDataExporter () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__EXPORTER, OsgiFactory.eINSTANCE.createConfigurationAdministratorExporter () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__CUSTOMIZATION_PROFILE, ProfileFactory.eINSTANCE.createProfile () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createBlockings () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createAlarmsEventsModule () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createJdbcUserServiceModule () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createEventStorageJdbc () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createEventStoragePostgres () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createRestExporter () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createHttpService () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createTelnetConsole () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createEventInjectorPostgres () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createEventInjectorJdbc () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createProfileConfiguration () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createEventInjectorSyslog () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.EQUINOX_APPLICATION__MODULES, OsgiFactory.eINSTANCE.createEventInjectorManager () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__SUMMARY_GROUPS, OsgiFactory.eINSTANCE.createSummaryGroup () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createSourceItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createScriptItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createImportItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createConstantItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createSummaryItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createPersistentItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createProxyItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createFormulaItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createAverageItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createMovingAverageItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createGlobalSummaryItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createWeakReferenceDataSourceItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createReferenceItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createTransientItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createChangeCounterItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__ITEMS, OsgiFactory.eINSTANCE.createDeltaItem () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__MARKERS, OsgiFactory.eINSTANCE.createMarkers () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__MONITOR_POOLS, OsgiFactory.eINSTANCE.createMonitorPool () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__EVENT_POOLS, OsgiFactory.eINSTANCE.createEventPool () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__DATA_MAPPER, OsgiFactory.eINSTANCE.createSimpleDataMapper () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__DATA_MAPPER, OsgiFactory.eINSTANCE.createJdbcDataMapper () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__EXTERNAL_EVENT_MONITORS, OsgiFactory.eINSTANCE.createExternalEventMonitor () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__EXTERNAL_EVENT_FILTERS, OsgiFactory.eINSTANCE.createStaticExternalEventFilter () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__EXTERNAL_EVENT_FILTERS, OsgiFactory.eINSTANCE.createSimpleExternalEventFilter () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__AVERAGES, OsgiFactory.eINSTANCE.createAverage () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__MOVING_AVERAGES, OsgiFactory.eINSTANCE.createMovingAverage () ) );

        newChildDescriptors.add ( createChildParameter ( OsgiPackage.Literals.MASTER_SERVER__BUFFERED_VALUES, OsgiFactory.eINSTANCE.createBufferedValue () ) );
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator ()
    {
        return ( (IChildCreationExtender)adapterFactory ).getResourceLocator ();
    }

}
