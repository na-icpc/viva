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
 * The Class NegativeNode.
 */
public class NegativeNode extends UnaryOperatorNode
{
    
    /** The type. */
    Class<?> type;
    
    /**
     * Instantiate.
     *
     * @param arg the arg
     * @throws ParseException the parse exception
     */
    @Override
    public void instantiate( ExpressionNode arg ) throws ParseException
    {
        type = arg.getReturnType();
        if( !Number.class.isAssignableFrom( type ) )
        {
            throw new ParseException( "Bad operand to " + operator + " operator: Expecting Integer, Long, Double or Float, got " + arg.getReturnType().getSimpleName() );  
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
        Object arg = argument.evaluate( context );
        Object result;
        
        if( type==Long.class ) result = Long.valueOf( -(Long)arg );
        else if( type==Integer.class ) result = Integer.valueOf( -(Integer)arg );
        else if( type==Double.class ) result = Double.valueOf( -(Double)arg );
        else result = Float.valueOf( -(Float)arg );
        
        return  result;
    }
}
