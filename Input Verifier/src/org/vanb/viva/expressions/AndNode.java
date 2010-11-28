package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.*;

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
    public Object evaluate( SymbolTable<ValueManager> values )
    {
        Boolean result = (Boolean)left.evaluate( values );
        if( result )
        {
            result = (Boolean)right.evaluate( values );
        }
        return result;
    }

    @Override
    public Class<?> returnType()
    {
        return Boolean.class;
    }

}
