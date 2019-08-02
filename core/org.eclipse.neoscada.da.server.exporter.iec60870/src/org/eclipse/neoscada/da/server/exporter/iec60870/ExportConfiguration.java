/*******************************************************************************
 * Copyright (c) 2014, 2016 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.neoscada.da.server.exporter.iec60870;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.neoscada.core.common.iec60870.Configurations;
import org.eclipse.neoscada.da.server.exporter.iec60870.MappingEntry.ValueType;
import org.eclipse.neoscada.protocol.iec60870.ProtocolOptions;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.ASDUAddress;
import org.eclipse.neoscada.protocol.iec60870.asdu.types.InformationObjectAddress;
import org.eclipse.neoscada.protocol.iec60870.server.data.DataModuleOptions;
import org.eclipse.scada.ca.ConfigurationDataHelper;

public class ExportConfiguration
{
    private short port = 2404;

    private Long spontaneousBufferWindow;

    private Long cyclicPeriod;

    private ProtocolOptions protocolOptions;

    private DataModuleOptions dataModuleOptions;

    private Set<MappingEntry> entries = new HashSet<> ();

    private Properties hiveProperties = new Properties ();

    public ExportConfiguration ()
    {
    }

    public void setDataModuleOptions ( final DataModuleOptions dataModuleOptions )
    {
        this.dataModuleOptions = dataModuleOptions;
    }

    public DataModuleOptions getDataModuleOptions ()
    {
        return this.dataModuleOptions;
    }

    public void setHiveProperties ( final Properties hiveProperties )
    {
        this.hiveProperties = hiveProperties;
    }

    public Properties getHiveProperties ()
    {
        return this.hiveProperties;
    }

    public void setEntries ( final Set<MappingEntry> entries )
    {
        this.entries = entries;
    }

    public Set<MappingEntry> getEntries ()
    {
        return this.entries;
    }

    public void setProtocolOptions ( final ProtocolOptions options )
    {
        this.protocolOptions = options;
    }

    public ProtocolOptions getProtocolOptions ()
    {
        return this.protocolOptions;
    }

    public short getPort ()
    {
        return this.port;
    }

    public void setPort ( final short port )
    {
        this.port = port;
    }

    public void setSpontaneousBufferWindow ( final Long spontaneousBufferWindow )
    {
        this.spontaneousBufferWindow = spontaneousBufferWindow;
    }

    public Long getSpontaneousBufferWindow ()
    {
        return this.spontaneousBufferWindow;
    }

    public void setCyclicPeriod ( Long cyclicPeriod )
    {
        this.cyclicPeriod = cyclicPeriod;
    }
    
    public Long getCyclicPeriod ()
    {
        return cyclicPeriod;
    }

    /**
     * Parse the configuration from the CA properties <br/>
     * <code>
     * entry.1.1=INT32#data.item.1
     * </code>
     *
     * @param parameters
     *            the parameters
     * @return the exporter configuration
     */
    public static ExportConfiguration parse ( final Map<String, String> parameters )
    {
        final ConfigurationDataHelper cfg = new ConfigurationDataHelper ( parameters );
        final ExportConfiguration result = new ExportConfiguration ();

        result.setPort ( (short)cfg.getInteger ( "port", 2404 /* IEC 60870-5-104 default */ ) ); //$NON-NLS-1$
        result.setSpontaneousBufferWindow ( cfg.getLong ( "spontaneousBufferWindow" ) ); //$NON-NLS-1$
        result.setCyclicPeriod ( cfg.getLong ( "cyclicPeriod" ) ); //$NON-NLS-1$

        final ProtocolOptions.Builder optionsBuilder = Configurations.parseProtocolOptions ( cfg );

        result.setProtocolOptions ( optionsBuilder.build () );

        final DataModuleOptions.Builder dataModuleOptions = new DataModuleOptions.Builder ();
        dataModuleOptions.setBackgroundScanPeriod ( cfg.getInteger ( "backgroundScanPeriod", 60_000 ) ); //$NON-NLS-1$
        dataModuleOptions.setSpontaneousDuplicates ( cfg.getInteger ( "spontaneousItemBuffer", 100 ) ); //$NON-NLS-1$
        dataModuleOptions.setFloatsWithTimestamp ( cfg.getBoolean ( "withTimestamp.float", true ) ); //$NON-NLS-1$
        dataModuleOptions.setBooleansWithTimestamp ( cfg.getBoolean ( "withTimestamp.boolean", true ) ); //$NON-NLS-1$

        result.setDataModuleOptions ( dataModuleOptions.build () );

        final Set<MappingEntry> entries = new HashSet<> ();

        for ( final Map.Entry<String, String> entry : cfg.getPrefixed ( "entry." ).entrySet () ) //$NON-NLS-1$
        {
            entries.add ( createEntry ( entry.getKey (), entry.getValue () ) );
        }

        result.setEntries ( entries );
        result.setHiveProperties ( cfg.getPrefixedProperties ( "hive." ) ); //$NON-NLS-1$

        return result;
    }

    private static MappingEntry createEntry ( final String key, final String value )
    {
        final String addr[] = key.split ( "\\." ); //$NON-NLS-1$
        final String item[] = value.split ( "#", 2 ); //$NON-NLS-1$

        if ( addr.length != 2 )
        {
            throw new IllegalArgumentException ( String.format ( "Address must be of the format '<asduAddress>.<informationObjectAddress>': '%s'", key ) );
        }

        final int asduAddress = convertAddress ( addr[0] );
        final int infoAddress = convertAddress ( addr[1] );

        final ValueType type;
        final String itemId;
        if ( item.length > 1 )
        {
            type = ValueType.valueOf ( item[0] );
            itemId = item[1];
        }
        else
        {
            type = null;
            itemId = item[0];
        }

        return new MappingEntry ( itemId, ASDUAddress.valueOf ( asduAddress ), InformationObjectAddress.valueOf ( infoAddress ), type );
    }

    private static int convertAddress ( final String string )
    {
        final String toks[] = string.split ( "[\\s-]" );
        int value = 0;

        for ( final String tok : toks )
        {
            final int v = Integer.parseInt ( tok );
            value = value << 8;
            value |= v;
        }

        return value;
    }

    @Override
    public int hashCode ()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( this.dataModuleOptions == null ? 0 : this.dataModuleOptions.hashCode () );
        result = prime * result + ( this.entries == null ? 0 : this.entries.hashCode () );
        result = prime * result + ( this.hiveProperties == null ? 0 : this.hiveProperties.hashCode () );
        result = prime * result + this.port;
        result = prime * result + ( this.protocolOptions == null ? 0 : this.protocolOptions.hashCode () );
        result = prime * result + ( this.spontaneousBufferWindow == null ? 0 : this.spontaneousBufferWindow.hashCode () );
        return result;
    }

    @Override
    public boolean equals ( final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass () != obj.getClass () )
        {
            return false;
        }
        final ExportConfiguration other = (ExportConfiguration)obj;
        if ( this.dataModuleOptions == null )
        {
            if ( other.dataModuleOptions != null )
            {
                return false;
            }
        }
        else if ( !this.dataModuleOptions.equals ( other.dataModuleOptions ) )
        {
            return false;
        }
        if ( this.entries == null )
        {
            if ( other.entries != null )
            {
                return false;
            }
        }
        else if ( !this.entries.equals ( other.entries ) )
        {
            return false;
        }
        if ( this.hiveProperties == null )
        {
            if ( other.hiveProperties != null )
            {
                return false;
            }
        }
        else if ( !this.hiveProperties.equals ( other.hiveProperties ) )
        {
            return false;
        }
        if ( this.port != other.port )
        {
            return false;
        }
        if ( this.protocolOptions == null )
        {
            if ( other.protocolOptions != null )
            {
                return false;
            }
        }
        else if ( !this.protocolOptions.equals ( other.protocolOptions ) )
        {
            return false;
        }
        if ( this.spontaneousBufferWindow == null )
        {
            if ( other.spontaneousBufferWindow != null )
            {
                return false;
            }
        }
        else if ( !this.spontaneousBufferWindow.equals ( other.spontaneousBufferWindow ) )
        {
            return false;
        }
        return true;
    }

}
