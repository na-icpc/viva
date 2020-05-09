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
 * The Class ConstantNode.
 */
public class ConstantNode implements ExpressionNode
{
    
    /** The value. */
    private Object value;
    
    /**
     * Instantiates a new constant node.
     *
     * @param v the value
     */
    public ConstantNode( Object v )
    {
        value = v;
    }
    
    /**
     * Evaluate.
     *
     * @param context the context
     * @return the object
     * @throws VIVAException the VIVA exception
     */
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        return value;
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public Class<?> getReturnType()
    {
        return value.getClass();
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return value instanceof String ? "\"" + value + "\"" : value.toString();
    }

}
