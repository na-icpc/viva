package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;

import org.vanb.viva.*;
import org.vanb.viva.utils.*;

public abstract class NumberBinaryOperatorNode extends BinaryOperatorNode
{
    private Class<?> type;
    protected double deps = VIVA.DEPS;
    protected float feps = VIVA.FEPS;
    
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        left =  lhs;
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
                ArithmeticFunction.nanCheck( ((Double)result).doubleValue(), toString() + " (" + left + " " + operator + " " + right + ")" );                
            }
            else if( result instanceof Float )
            {
                ArithmeticFunction.nanCheck( ((Float)result).floatValue(), toString() + " (" + left + " " + operator + " " + right + ")" );
            }
        }
        catch( Exception e )
        {
            context.err.println( this );
            context.throwException( e.getMessage() );  
        }
        
        return result;
    }
    
    public abstract Object evaluate( double l, double r ) throws Exception;
    public abstract Object evaluate( float l, float r ) throws Exception;
    public abstract Object evaluate( long l, long r ) throws Exception;
    public abstract Object evaluate( int l, int r ) throws Exception;

    @Override
    public Class<?> getReturnType()
    {
        return type;
    }

}
