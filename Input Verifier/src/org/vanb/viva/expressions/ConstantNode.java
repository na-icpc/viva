package org.vanb.viva.expressions;
import org.vanb.viva.utils.*;

public class ConstantNode implements ExpressionNode
{
    private Object value;
    
    public ConstantNode( Object v )
    {
        value = v;
    }
    @Override
    public Object evaluate( SymbolTable<ValueManager> values )
    {
        return value;
    }

    @Override
    public Class<?> returnType()
    {
        return value.getClass();
    }

}
