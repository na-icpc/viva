package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;

public abstract class BinaryOperatorNode implements ExpressionNode
{
    public String operator;
    protected ExpressionNode left, right;
    protected String nodetype;
        
    public abstract void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException;
    
    public String toString()
    {
        return left + " " + operator + " " + right;
    }
}
