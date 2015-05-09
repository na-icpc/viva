package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class XorNode extends BinaryOperatorNode
{
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        operator = "^^";
        left = lhs;
        right = rhs;
        if( !lhs.getReturnType().equals( Boolean.class )) 
        {
            throw new ParseException( "Bad left operand to ^^ operator: Expecting Boolean, got " + lhs.getReturnType().getSimpleName() );
        }
        if( !rhs.getReturnType().equals( Boolean.class ))
        {
            throw new ParseException( "Bad right operand to ^^ operator: Expecting Boolean, got " + lhs.getReturnType().getSimpleName() );
        }
    }

    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Boolean l = (Boolean)left.evaluate( context );
        Boolean r = (Boolean)right.evaluate( context );
        
        return new Boolean( l ^ r );
    }

    @Override
    public Class<?> getReturnType()
    {
        return Boolean.class;
    }

}
