package org.vanb.viva.expressions;

public class ConstantNode implements ExpressionNode
{
    private Object value;
    
    public ConstantNode( Object v )
    {
        value = v;
    }
    @Override
    public Object evaluate()
    {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    public Class<?> returnType()
    {
        // TODO Auto-generated method stub
        return value.getClass();
    }

}
