/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class XorNode.
 */
public class XorNode extends BinaryOperatorNode
{
    
    /**
     * Instantiate.
     *
     * @param lhs the lhs
     * @param rhs the rhs
     * @throws ParseException the parse exception
     */
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        operator = "^^";
        left = lhs;
        right = rhs;
        if( !lhs.getReturnType().equals( Boolean.class )) 
        {
            throw new ParseException( "Bad left operand to ^^ operator: Expecting Boolean, got " + lhs.getReturnType().getSimpleName() );
        }
        if( !rhs.getReturnType().equals( Boolean.class ))
        {
            throw new ParseException( "Bad right operand to ^^ operator: Expecting Boolean, got " + lhs.getReturnType().getSimpleName() );
        }
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
        Boolean l = (Boolean)left.evaluate( context );
        Boolean r = (Boolean)right.evaluate( context );
        
        return new Boolean( l ^ r );
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public Class<?> getReturnType()
    {
        return Boolean.class;
    }

}
