package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.Utilities;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * Abstract class for binary operators with number parameters.
 */
public abstract class NumberBinaryOperatorNode extends BinaryOperatorNode
{
    
    /** The return type. */
    protected Class<?> type;
    
    /** The epsilon for doubles. */
    protected double deps;
    
    /** The epsilon for floats. */
    protected float feps;
    
    /** 
     * @see org.vanb.viva.expressions.BinaryOperatorNode#instantiate(org.vanb.viva.expressions.ExpressionNode, org.vanb.viva.expressions.ExpressionNode)
     */
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        left = lhs;
        right = rhs;
        
        if( !lhs.getReturnType().equals( Double.class )
                && !lhs.getReturnType().equals( Float.class )
                && !lhs.getReturnType().equals( Integer.class )
                && !lhs.getReturnType().equals( Long.class ) ) 
        {
            throw new ParseException( "Bad left operand to " + operator + " operator: Expecting Double, Float, Long or Integer, got " + lhs.getReturnType().getSimpleName() );
        }
        if( !rhs.getReturnType().equals( Double.class )
                && !rhs.getReturnType().equals( Float.class )
                && !rhs.getReturnType().equals( Integer.class )
                && !rhs.getReturnType().equals( Long.class ) ) 
        {
            throw new ParseException( "Bad right operand to " + operator + " operator: Expecting Double, Float, Long or Integer, got " + rhs.getReturnType().getSimpleName() );
        }
        
        if( Double.class.equals( lhs.getReturnType() ) 
                || Double.class.equals( rhs.getReturnType() ))
        {
            type = Double.class;
        }
        else if( Float.class.equals( lhs.getReturnType() ) 
                || Float.class.equals( rhs.getReturnType() ))
        {
            type = Float.class;
        }
        else if( Long.class.equals( lhs.getReturnType() ) 
                || Long.class.equals( rhs.getReturnType() ))
        {
            type = Long.class;
        }
        else if( Integer.class.equals( lhs.getReturnType() ) 
                || Integer.class.equals( rhs.getReturnType() ))
        {
            type = Integer.class;
        }
    }

    /** 
     * @see org.vanb.viva.expressions.ExpressionNode#evaluate(org.vanb.viva.utils.VIVAContext)
     */
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Object result = null;
        Number l = (Number)left.evaluate( context );
        Number r = (Number)right.evaluate( context );
        
        deps = context.deps;
        feps = context.feps;
        
        try
        {
            if( type.equals( Double.class ))
            {
                result = evaluate( l.doubleValue(), r.doubleValue() );
            }
            else if( type.equals( Float.class ) )
            {
                result = evaluate( l.floatValue(), r.floatValue() );
            }
            else if( type.equals( Long.class ) )
            {
                result = evaluate( l.longValue(), r.longValue() );               
            }
            else
            {
                result = evaluate( l.intValue(), r.intValue() );                           
            }
            
            if( result instanceof Double )
            {
                Utilities.nanCheck( ((Double)result).doubleValue(), toString() + " (" + left + " " + operator + " " + right + ")" );                
            }
            else if( result instanceof Float )
            {
                Utilities.nanCheck( ((Float)result).floatValue(), toString() + " (" + left + " " + operator + " " + right + ")" );
            }
        }
        catch( Exception e )
        {
            context.err.println( this );
            context.throwException( e.getMessage() );  
        }
        
        return result;
    }
    
    /**
     * Evaluate with double parameters.
     *
     * @param l the left-hand value
     * @param r the right-hand value
     * @return the result
     * @throws Exception the exception
     */
    public abstract Object evaluate( double l, double r ) throws Exception;
    
    /**
     * Evaluate with float parameters.
     *
     * @param l the left-hand value
     * @param r the right-hand value
     * @return the result
     * @throws Exception the exception
     */
    public abstract Object evaluate( float l, float r ) throws Exception;
    
    /**
     * Evaluate with long parameters.
     *
     * @param l the left-hand value
     * @param r the right-hand value
     * @return the result
     * @throws Exception the exception
     */
    public abstract Object evaluate( long l, long r ) throws Exception;
    
    /**
     * Evaluate with int parameters.
     *
     * @param l the left-hand value
     * @param r the right-hand value
     * @return the result
     * @throws Exception the exception
     */
    public abstract Object evaluate( int l, int r ) throws Exception;

    /** 
     * @see org.vanb.viva.expressions.ExpressionNode#getReturnType()
     */
    @Override
    public Class<?> getReturnType()
    {
        return type;
    }
}
