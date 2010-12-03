package org.vanb.viva.expressions;

import org.vanb.viva.utils.*;

public class VariableNode implements ExpressionNode
{
    private String name;
    private Class<?> type;
    
    public VariableNode( String n, Class<?> t )
    {
        name = n;
        type = t;
    }
    
    @Override
    public Object evaluate( VIVAContext context )
    {
        return context.values.lookup( name ).getCurrentValue();
    }

    @Override
    public Class<?> returnType()
    {
        return type;
    }

}
