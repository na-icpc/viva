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
 * The Class NotNode.
 */
public class NotNode extends UnaryOperatorNode
{

    /**
     * Instantiate.
     *
     * @param arg the arg
     * @throws ParseException the parse exception
     */
    @Override
    public void instantiate( ExpressionNode arg ) throws ParseException
    {
        if( arg.getReturnType()!=Boolean.class )
        {
            throw new ParseException( "Bad operand to " + operator + " operator: Expecting Boolean, got " + arg.getReturnType().getSimpleName() );  
        }
        argument = arg;
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
        return ! (Boolean)argument.evaluate( context );
    }
}
