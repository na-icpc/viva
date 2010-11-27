package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;

public abstract class BinaryOperatorNode implements ExpressionNode
{
    protected ExpressionNode left, right;
    protected String nodetype;
    public abstract void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException;
}
