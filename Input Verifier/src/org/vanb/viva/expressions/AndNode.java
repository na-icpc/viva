package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;

public class AndNode extends BinaryOperatorNode
{

    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        left = lhs;
        right = rhs;
        if( !lhs.returnType().equals( Boolean.class )) 
        {
            throw new ParseException( "Bad left operand to && operator: Expecting Boolean, got " + lhs.returnType().toString() );
        }
        if( !rhs.returnType().equals( Boolean.class ))
        {
            throw new ParseException( "Bad right operand to && operator: Expecting Boolean, got " + lhs.returnType().toString() );
        }
    }

    @Override
    public Object evaluate()
    {
        Boolean result = (Boolean)left.evaluate();
        if( result )
        {
            result = (Boolean)right.evaluate();
        }
        return result;
    }

    @Override
    public Class<?> returnType()
    {
        return Boolean.class;
    }

}
