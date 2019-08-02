/*******************************************************************************
 * Copyright (c) 2014, 2015 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.neoscada.da.server.iec60870;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.neoscada.protocol.iec60870.ProtocolOptions;
import org.eclipse.neoscada.protocol.iec60870.asdu.ASDUHeader;
import org.eclipse.neoscada.protocol.iec60870.asdu.message.SetPointCommandScaledValue;
import org.eclipse.neoscada.protocol.iec60870.asdu.message.SetPointCommandShortFloatingPoint;
import org.eclipse.neoscada.protocol.iec60870.asdu.message.SingleCommand;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.ASDUAddress;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.CauseOfTransmission;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.DoublePoint;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.InformationObjectAddress;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.StandardCause;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.Value;
import org.eclipse.neoscada.protocol.iec60870.client.AutoConnectClient;
import org.eclipse.neoscada.protocol.iec60870.client.ClientModule;
import org.eclipse.neoscada.protocol.iec60870.client.AutoConnectClient.ModulesFactory;
import org.eclipse.neoscada.protocol.iec60870.client.AutoConnectClient.State;
import org.eclipse.neoscada.protocol.iec60870.client.AutoConnectClient.StateListener;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataHandler;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataListener;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataModule;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataModuleOptions;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataProcessor;
import org.eclipse.scada.core.NotConvertableException;
import org.eclipse.scada.core.NullValueException;
import org.eclipse.scada.core.Variant;
import org.eclipse.scada.core.server.OperationParameters;
import org.eclipse.scada.da.core.DataItemInformation;
import org.eclipse.scada.da.core.WriteResult;
import org.eclipse.scada.da.data.IODirection;
import org.eclipse.scada.da.server.browser.common.FolderCommon;
import org.eclipse.scada.da.server.browser.common.query.AttributeNameProvider;
import org.eclipse.scada.da.server.browser.common.query.GroupFolder;
import org.eclipse.scada.da.server.browser.common.query.IDNameProvider;
import org.eclipse.scada.da.server.browser.common.query.InvisibleStorage;
import org.eclipse.scada.da.server.browser.common.query.ItemDescriptor;
import org.eclipse.scada.da.server.browser.common.query.SplitGroupProvider;
import org.eclipse.scada.da.server.browser.common.query.SplitNameProvider;
import org.eclipse.scada.da.server.common.AttributeMode;
import org.eclipse.scada.da.server.common.DataItem;
import org.eclipse.scada.da.server.common.DataItemInformationBase;
import org.eclipse.scada.da.server.common.chain.DataItemInputOutputChained;
import org.eclipse.scada.da.server.common.chain.item.SumErrorChainItem;
import org.eclipse.scada.da.server.common.exporter.ObjectExporter;
import org.eclipse.scada.da.server.common.item.factory.DefaultChainItemFactory;
import org.eclipse.scada.utils.concurrent.InstantErrorFuture;
import org.eclipse.scada.utils.concurrent.InstantFuture;
import org.eclipse.scada.utils.concurrent.NotifyFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection
{
    private final static Logger logger = LoggerFactory.getLogger ( Connection.class );

    private final DataHandler handler;

    private final AutoConnectClient client;

    private final FolderCommon folder;

    private final AtomicBoolean disposed = new AtomicBoolean ( false );

    private final Hive hive;

    private final DefaultChainItemFactory stateFactory;

    private final GroupFolder dataFolder;

    private final InvisibleStorage storage;

    private final ObjectExporter clientExporter;

    private final ClientState clientState;

    private final StateListener clientListener = new StateListener () {

        @Override
        public void stateChanged ( final State state, final Throwable e )
        {
            handleStateChanged ( state, e );
        }
    };

    private final ModulesFactory modulesFactory;

    private final DataListener dataListener = new DataListener () {

        @Override
        public void started ()
        {
            setStarted ( true );
        }

        @Override
        public void update ( final ASDUAddress commonAddress, final InformationObjectAddress objectAddress, final Value<?> value )
        {
            handleDataUpdate ( commonAddress, objectAddress, value );
        }

        @Override
        public void disconnected ()
        {
            handleDisconnected ();
        }
    };

    private final ProtocolOptions protocolOptions;

    private final Executor executor;

    private final Map<String, DataItemInputOutputChained> itemCache = new HashMap<> ();

    private final String id;

    private final DataModuleOptions dataModuleOptions;

    private final Map<String, QualifiedCommandMessage> itemTypes;

    public Connection ( final String id, final Hive hive, final Executor executor, final ConnectionConfiguration configuration )
    {
        this.hive = hive;

        this.id = id;

        this.executor = executor;

        this.dataModuleOptions = configuration.getDataModuleOptions ();
        this.handler = new DataProcessor ( executor, this.dataListener, configuration.getDataModuleOptions ().isDelayStart () );
        final DataModule dataModule = new DataModule ( this.handler, this.dataModuleOptions );

        this.protocolOptions = configuration.getProtocolOptions ();
        this.itemTypes = configuration.getItemTypes ();

        this.folder = new FolderCommon ();
        hive.getRootFolder ().add ( id, this.folder, null );

        this.stateFactory = new DefaultChainItemFactory ( hive, this.folder, id + ".state", "state" );
        this.dataFolder = new GroupFolder ( new SplitGroupProvider ( new AttributeNameProvider ( "iec.60870.address" ), "\\.", 0, 1 ), new SplitNameProvider ( new IDNameProvider (), "\\.", -2, 0, "." ) );

        this.storage = new InvisibleStorage ();
        this.storage.addChild ( this.dataFolder );
        this.folder.add ( "data", this.dataFolder, null );

        this.clientExporter = new ObjectExporter ( this.stateFactory.createSubFolderFactory ( "client" ) );
        this.clientExporter.attachTarget ( this.clientState = new ClientState ( this ) );

        this.modulesFactory = new ModulesFactory () {
            @Override
            public List<ClientModule> createModules ()
            {
                return Collections.singletonList ( (ClientModule)dataModule );
            }
        };

        this.client = new AutoConnectClient ( configuration.getHost (), configuration.getPort (), configuration.getProtocolOptions (), this.modulesFactory, this.clientListener );
    }

    protected void setStarted ( final boolean value )
    {
        this.clientState.setDataStarted ( value );
    }

    protected synchronized void handleDisconnected ()
    {
        setStarted ( false );

        this.storage.clear ();
        for ( final DataItem item : this.itemCache.values () )
        {
            this.hive.unregisterItem ( item );
        }
        this.itemCache.clear ();
    }

    protected void handleDataUpdate ( final ASDUAddress commonAddress, final InformationObjectAddress objectAddress, final Value<?> value )
    {
        logger.trace ( "data update - {}-{} = {}", commonAddress, objectAddress, value );

        final DataItemInputOutputChained item = getItem ( commonAddress, objectAddress );
        updateItem ( item, value );
    }

    private void updateItem ( final DataItemInputOutputChained item, final Value<?> value )
    {
        final Variant variant = convertValue ( value );

        final Object o = value.getValue ();

        final Map<String, Variant> attributes = new HashMap<> ();
        attributes.put ( "timestamp", Variant.valueOf ( value.getTimestamp () ) );

        if ( o != null )
        {
            attributes.put ( "iec.data.type", Variant.valueOf ( o.getClass () ) );
        }

        if ( value.isOverflow () )
        {
            attributes.put ( "overflow.error", Variant.TRUE );
        }
        if ( value.getQualityInformation ().isSubstituted () )
        {
            attributes.put ( "manual", Variant.TRUE );
        }
        if ( !value.getQualityInformation ().isValid () )
        {
            attributes.put ( "iec60870.data.error", Variant.TRUE );
        }
        if ( value.getQualityInformation ().isBlocked () )
        {
            attributes.put ( "blocked", Variant.TRUE );
        }
        if ( !value.getQualityInformation ().isTopical () )
        {
            attributes.put ( "iec60870.topical.error", Variant.TRUE );
        }

        item.updateData ( variant, attributes, AttributeMode.SET );
    }

    private Variant convertValue ( final Value<?> value )
    {
        final Object o = value.getValue ();
        if ( o instanceof DoublePoint )
        {
            final DoublePoint dp = (DoublePoint)o;
            switch ( dp )
            {
                case OFF:
                    return Variant.FALSE;
                case ON:
                    return Variant.TRUE;
                default:
                    return Variant.NULL;
            }
        }
        return Variant.valueOf ( value.getValue () );
    }

    private synchronized DataItemInputOutputChained getItem ( final ASDUAddress commonAddress, final InformationObjectAddress objectAddress )
    {
        final String localId = makeLocalId ( commonAddress, objectAddress );

        final DataItemInputOutputChained item = this.itemCache.get ( localId );

        if ( item == null )
        {
            return createItem ( localId, commonAddress, objectAddress );
        }
        else
        {
            return item;
        }
    }

    private DataItemInputOutputChained createItem ( final String localId, final ASDUAddress commonAddress, final InformationObjectAddress objectAddress )
    {
        final String id = this.id + ".data." + localId;

        final DataItemInformation di = new DataItemInformationBase ( id, EnumSet.of ( IODirection.INPUT, IODirection.OUTPUT ) );

        final DataItemInputOutputChained item = new DataItemInputOutputChained ( di, this.executor ) {

            @Override
            protected NotifyFuture<WriteResult> startWriteCalculatedValue ( final Variant value, final OperationParameters operationParameters )
            {
                return handleStartWriteValue ( commonAddress, objectAddress, value, operationParameters );
            }
        };

        item.addChainElement ( IODirection.INPUT, new SumErrorChainItem () );

        this.itemCache.put ( localId, item );

        final Map<String, Variant> attributes = new HashMap<String, Variant> ();
        attributes.put ( "iec.60870.address", Variant.valueOf ( localId ) );

        this.hive.registerItem ( item );
        this.storage.added ( new ItemDescriptor ( item, attributes ) );

        return item;
    }

    protected NotifyFuture<WriteResult> handleStartWriteValue ( final ASDUAddress commonAddress, final InformationObjectAddress objectAddress, final Variant value, final OperationParameters operationParameters )
    {
        final Object command = makeCommand ( commonAddress, objectAddress, value );

        if ( command == null )
        {
            return new InstantErrorFuture<> ( new IllegalArgumentException ( String.format ( "Unable to write value: %s", value ) ) );
        }

        final boolean didWrite = this.client.writeCommand ( command );

        if ( didWrite )
        {
            return new InstantFuture<WriteResult> ( WriteResult.OK );
        }
        else
        {
            return new InstantErrorFuture<WriteResult> ( new IllegalStateException ( "Client is not connected" ) );
        }
    }

    private Object makeCommand ( final ASDUAddress commonAddress, final InformationObjectAddress objectAddress, final Variant value )
    {
        if ( value == null )
        {
            return null;
        }

        Byte csa = this.dataModuleOptions.getCauseSourceAddress ();
        if ( csa == null )
        {
            csa = (byte)0;
        }
        final QualifiedCommandMessage cm = this.itemTypes.get ( makeLocalId ( commonAddress, objectAddress ) );

        final ASDUHeader header = new ASDUHeader ( new CauseOfTransmission ( StandardCause.ACTIVATED, csa ), commonAddress );

        if ( cm != null )
        {
            return cm.getCommandMessage ().createMessage ( header, objectAddress, value, System.currentTimeMillis (), cm.getQualifierOfCommand () );
        }
        else if ( value.isString () && value.asString ( "" ).startsWith ( "C_S" ) )
        {
            return CommandMessage.createMessage ( header, objectAddress, value.asString ( "" ) );
        }
        else
        {
            try
            {
                switch ( value.getType () )
                {
                    case BOOLEAN:
                        return new SingleCommand ( header, objectAddress, value.asBoolean () );

                    case STRING:
                    case DOUBLE:
                        return new SetPointCommandShortFloatingPoint ( header, objectAddress, (float)value.asDouble () );

                    case INT32:
                    case INT64:
                        return new SetPointCommandScaledValue ( header, objectAddress, (short)value.asInteger () );

                    default:
                        return null;
                }
            }
            catch ( final NotConvertableException | NullValueException e )
            {
                // should never happen
            }
        }
        return null;
    }

    public static class FullAddress
    {
        ASDUAddress commonAddress;

        InformationObjectAddress objectAddress;
    }

    public FullAddress parseFullAddress ( final String address )
    {
        try
        {
            final LinkedList<Integer> segs = new LinkedList<> ();

            // first split into integers

            for ( final String tok : address.split ( "\\." ) )
            {
                segs.add ( Integer.parseInt ( tok ) );
            }

            // convert to full address

            final FullAddress result = new FullAddress ();

            switch ( this.protocolOptions.getAdsuAddressType () )
            {
                case SIZE_1:
                    result.commonAddress = ASDUAddress.fromArray ( new int[] { segs.poll () } );
                    break;
                case SIZE_2:
                    result.commonAddress = ASDUAddress.fromArray ( new int[] { segs.poll (), segs.poll () } );
                    break;
                default:
                    throw new IllegalArgumentException ( String.format ( "ASDU address size type %s unkown", this.protocolOptions.getAdsuAddressType () ) );
            }
            switch ( this.protocolOptions.getInformationObjectAddressType () )
            {
                case SIZE_1:
                    result.objectAddress = InformationObjectAddress.fromArray ( new int[] { segs.poll () } );
                    break;
                case SIZE_2:
                    result.objectAddress = InformationObjectAddress.fromArray ( new int[] { segs.poll (), segs.poll () } );
                    break;
                case SIZE_3:
                    result.objectAddress = InformationObjectAddress.fromArray ( new int[] { segs.poll (), segs.poll (), segs.poll () } );
                    break;
                default:
                    throw new IllegalArgumentException ( String.format ( "Information object address size type %s unkown", this.protocolOptions.getInformationObjectAddressType () ) );
            }

            // return result

            return result;
        }
        catch ( final Exception e )
        {
            // this will also catch the two type exception earlier, but this is ok
            throw new IllegalArgumentException ( String.format ( "'%s' is not a valid IEC address for this configuration", address ), e );
        }
    }

    private String makeLocalId ( final ASDUAddress commonAddress, final InformationObjectAddress objectAddress )
    {
        final StringBuilder sb = new StringBuilder ();

        switch ( this.protocolOptions.getAdsuAddressType () )
        {
            case SIZE_1:
            {
                sb.append ( String.format ( "%d", commonAddress.getAddress () & 0xFF ) );
                break;
            }
            default:
            {
                final int a = commonAddress.getAddress ();
                sb.append ( String.format ( "%d.%d", a >> 8 & 0xFF, a & 0xFF ) );
                break;
            }
        }

        sb.append ( '.' );

        switch ( this.protocolOptions.getInformationObjectAddressType () )
        {
            case SIZE_1:
            {
                sb.append ( String.format ( "%d", objectAddress.getAddress () & 0xFF ) );
                break;
            }
            case SIZE_2:
            {
                final int a = objectAddress.getAddress ();
                sb.append ( String.format ( "%d.%d", a >> 8 & 0xFF, a & 0xFF ) );
                break;
            }
            default:
            {
                final int a = objectAddress.getAddress ();
                sb.append ( String.format ( "%d.%d.%d", a >> 16 & 0xFF, a >> 8 & 0xFF, a & 0xFF ) );
                break;
            }
        }

        return sb.toString ();
    }

    protected void handleStateChanged ( final State state, final Throwable e )
    {
        logger.info ( "Connection state changed: {}", state );

        this.clientState.setConnectionState ( "" + state );
    }

    public void dispose ()
    {
        if ( !this.disposed.compareAndSet ( false, true ) )
        {
            return;
        }

        this.stateFactory.dispose ();
        this.hive.getRootFolder ().remove ( this.folder );

        this.client.close ();
    }

    /**
     * Terminate the connection and let the auto reconnect controller re-connect
     */
    public void reconnect ()
    {
        this.client.reconnect ();
    }

    public void requestStartData ()
    {
        if ( this.dataModuleOptions.isDelayStart () )
        {
            this.client.requestStartData ();
        }
    }
}
