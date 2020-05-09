/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * Base type of a node of an Expression tree.
 *
 * @author vanb
 */
public interface ExpressionNode 
{
	
	/**
	 * Evaluate the expression rooted at this node.
	 *
	 * @param context the context
	 * @return The value of this expression
	 * @throws VIVAException the VIVA exception
	 */
    public Object evaluate( VIVAContext context ) throws VIVAException;
    
    /**
     * Tell the return type of this expression.
     * 
     * @return The return type of this expression.
     */
    public Class<?> getReturnType();
}
