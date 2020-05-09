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
 * The Class ShiftNode.
 */
public class ShiftNode extends BinaryOperatorNode
{
    
    /** The type. */
    private Class<?> type;
    
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
        left =  lhs;
        right = rhs;
        
        if( !lhs.getReturnType().equals( Integer.class )
                && !lhs.getReturnType().equals( Long.class ) ) 
        {
            throw new ParseException( "Bad left operand to " + operator + " operator: Expecting Long or Integer, got " + lhs.getReturnType().getSimpleName() );
        }
        if( !rhs.getReturnType().equals( Integer.class ) ) 
        {
            throw new ParseException( "Bad right operand to " + operator + " operator: Expecting Integer, got " + rhs.getReturnType().getSimpleName() );
        }
        
        if( Long.class.equals( lhs.getReturnType() ) )
        {
            type = Long.class;
        }
        else 
        {
            type = Integer.class;
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
        Object result = null;
        Number leftnum = (Number)left.evaluate( context );
        Number rightnum = (Number)right.evaluate( context );
        
        try
        {
            if( type.equals( Long.class ) )
            {
                long l = leftnum.longValue();
                int r = rightnum.intValue();
                long longresult = operator.equals( "<<" ) ? (l<<r) :
                    operator.equals( ">>" ) ? (l>>r) : (l>>>r);
                result = new Long( longresult );               
            }
            else
            {
                int l = leftnum.intValue();
                int r = rightnum.intValue();
                int intresult = operator.equals( "<<" ) ? (l<<r) :
                    operator.equals( ">>" ) ? (l>>r) : (l>>>r);
                result = new Integer( intresult );               
            }
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );  
        }
        
        return result;
    }
    
    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public Class<?> getReturnType()
    {
        return type;
    }

}
