package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public abstract class IntegerLongBinaryOperatorNode extends BinaryOperatorNode
{
    private Class<?> type;
    
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
        if( !rhs.getReturnType().equals( Integer.class )
                && !rhs.getReturnType().equals( Long.class ) ) 
        {
            throw new ParseException( "Bad right operand to " + operator + " operator: Expecting Long or Integer, got " + rhs.getReturnType().getSimpleName() );
        }
        
        if( Long.class.equals( lhs.getReturnType() ) 
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
        
        try
        {
            if( type.equals( Long.class ) )
            {
                result = evaluate( l.longValue(), r.longValue() );               
            }
            else
            {
                result = evaluate( l.intValue(), r.intValue() );                           
            }
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );  
        }
        
        return result;
    }
    
    public abstract Object evaluate( long l, long r ) throws Exception;
    public abstract Object evaluate( int l, int r ) throws Exception;

    @Override
    public Class<?> getReturnType()
    {
        return type;
    }

}
