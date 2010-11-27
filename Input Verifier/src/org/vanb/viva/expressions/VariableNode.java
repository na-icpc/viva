package org.vanb.viva.expressions;

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
    public Object evaluate()
    {
        // TODO Auto-generated method stub
        // Look up value somehow
        return null;
    }

    @Override
    public Class<?> returnType()
    {
        // TODO Auto-generated method stub
        return type;
    }

}
