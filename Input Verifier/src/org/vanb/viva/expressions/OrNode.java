package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.*;

public class OrNode extends BinaryOperatorNode
{
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        operator = "||";
        left = lhs;
        right = rhs;
        if( !lhs.returnType().equals( Boolean.class )) 
        {
            throw new ParseException( "Bad left operand to || operator: Expecting Boolean, got " + lhs.returnType().toString() );
        }
        if( !rhs.returnType().equals( Boolean.class ))
        {
            throw new ParseException( "Bad right operand to || operator: Expecting Boolean, got " + lhs.returnType().toString() );
        }
    }

    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Boolean result = (Boolean)left.evaluate( context );
        if( !result )
        {
            result = (Boolean)right.evaluate( context );
        }

        return result;
    }

    @Override
    public Class<?> returnType()
    {
        return Boolean.class;
    }

}