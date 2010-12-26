package org.vanb.viva.expressions;

public class SubscriptNode extends VariableNode
{
    private ExpressionNode subscript;
    
    public SubscriptNode( String name, Class<?> type, ExpressionNode sub )
    {
        super( name, type );
        subscript = sub;
    }
    
    public String toString()
    {
        return name + "[" + subscript + "]";
    }
}
