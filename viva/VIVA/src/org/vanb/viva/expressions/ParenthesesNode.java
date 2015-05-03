package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class ParenthesesNode implements ExpressionNode
{
    private ExpressionNode exp;
    
    public ParenthesesNode( ExpressionNode expression )
    {
        exp = expression;
    }
    
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        return exp.evaluate( context );
    }

    @Override
    public Class<?> getReturnType()
    {
        return exp.getReturnType();
    }
    
    public String toString()
    {
        return "(" + exp + ")";
    }

}
