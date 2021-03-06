/*******************************************************************************
 * Copyright (c) 2012, 2013 TH4 SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TH4 SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.scada.core.ui.connection.views.tree.node;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scada.core.ui.connection.ConnectionDescriptor;

public class TargetGroupingProvider implements GroupingProvider
{

    @Override
    public List<String> getGroups ( final ConnectionDescriptor connectionDescriptor )
    {
        return Arrays.asList ( connectionDescriptor.getConnectionInformation ().getTarget () );
    }

}
