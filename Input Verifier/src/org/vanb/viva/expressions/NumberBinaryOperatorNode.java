package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;

import org.vanb.viva.utils.*;

public abstract class NumberBinaryOperatorNode extends BinaryOperatorNode
{
    private Class<?> type;
    protected static final double deps = 0.000001;
    protected static final float feps = 0.000001F;
    
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        left =  lhs;
        right = rhs;
        
        if( !lhs.returnType().equals( Double.class )
                && !lhs.returnType().equals( Float.class )
                && !lhs.returnType().equals( Integer.class )
                && !lhs.returnType().equals( Long.class ) ) 
        {
            throw new ParseException( "Bad left operand to " + operator + " operator: Expecting Double, Float, Long or Integer, got " + lhs.returnType().toString() );
        }
        if( !rhs.returnType().equals( Double.class )
                && !rhs.returnType().equals( Float.class )
                && !rhs.returnType().equals( Integer.class )
                && !rhs.returnType().equals( Long.class ) ) 
        {
            throw new ParseException( "Bad right operand to numerical  operator: Expecting Double, Float, Long or Integer, got " + rhs.returnType().toString() );
        }
        
        if( Double.class.equals( lhs.returnType() ) 
                || Double.class.equals( rhs.returnType() ))
        {
            type = Double.class;
        }
        else if( Float.class.equals( lhs.returnType() ) 
                || Float.class.equals( rhs.returnType() ))
        {
            type = Float.class;
        }
        else if( Long.class.equals( lhs.returnType() ) 
                || Long.class.equals( rhs.returnType() ))
        {
            type = Long.class;
        }
        else if( Integer.class.equals( lhs.returnType() ) 
                || Integer.class.equals( rhs.returnType() ))
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
        
        return result;
    }
    
    public abstract Object evaluate( double l, double r );
    public abstract Object evaluate( float l, float r );
    public abstract Object evaluate( long l, long r );
    public abstract Object evaluate( int l, int r );

    @Override
    public Class<?> returnType()
    {
        return type;
    }

}
