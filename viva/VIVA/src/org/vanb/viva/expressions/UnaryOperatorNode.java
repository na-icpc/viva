package org.vanb.viva.expressions;

import org.vanb.viva.parser.*;

public abstract class UnaryOperatorNode implements ExpressionNode
{
    protected ExpressionNode argument;
    public String operator;
    
    public abstract void instantiate( ExpressionNode arg ) throws ParseException;
        
    public String toString()
    {
        return operator + argument;
    }
    
    public Class<?> getReturnType()
    {
        return argument.getReturnType();
    }

}
