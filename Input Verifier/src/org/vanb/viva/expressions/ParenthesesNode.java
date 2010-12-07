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
        // TODO Auto-generated method stub
        return exp.evaluate( context );
    }

    @Override
    public Class<?> returnType()
    {
        // TODO Auto-generated method stub
        return exp.returnType();
    }
    
    public String toString()
    {
        return "(" + exp + ")";
    }

}
