package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class ConstantNode implements ExpressionNode
{
    private Object value;
    
    public ConstantNode( Object v )
    {
        value = v;
    }
    
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        return value;
    }

    @Override
    public Class<?> getReturnType()
    {
        return value.getClass();
    }
    
    public String toString()
    {
        return value instanceof String ? "\"" + value + "\"" : value.toString();
    }

}
