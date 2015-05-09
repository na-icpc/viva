package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class ShiftNode extends BinaryOperatorNode
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
    
    @Override
    public Class<?> getReturnType()
    {
        return type;
    }

}
