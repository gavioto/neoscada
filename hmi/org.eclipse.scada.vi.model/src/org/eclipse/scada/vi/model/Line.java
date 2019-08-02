/**
 * Copyright (c) 2011, 2012 TH4 SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     TH4 SYSTEMS GmbH - initial API and implementation
 */
package org.eclipse.scada.vi.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Line</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.scada.vi.model.Line#getPoints <em>Points</em>}</li>
 * </ul>
 *
 * @see org.eclipse.scada.vi.model.VisualInterfacePackage#getLine()
 * @model
 * @generated
 */
public interface Line extends Shape
{
    /**
     * Returns the value of the '<em><b>Points</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.scada.vi.model.Position}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Points</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Points</em>' containment reference list.
     * @see org.eclipse.scada.vi.model.VisualInterfacePackage#getLine_Points()
     * @model containment="true"
     * @generated
     */
    EList<Position> getPoints ();

} // Line
