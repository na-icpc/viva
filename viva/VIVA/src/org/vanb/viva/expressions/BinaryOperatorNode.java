/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;

/**
 * The Class BinaryOperatorNode.
 */
public abstract class BinaryOperatorNode implements ExpressionNode
{
    
    /** The operator. */
    public String operator;
    
    /** The right and left expressions. */
    protected ExpressionNode left, right;
    
    /** The node result type. */
    protected String nodetype;
        
    /**
     * Instantiate.
     *
     * @param lhs the lhs
     * @param rhs the rhs
     * @throws ParseException the parse exception
     */
    public abstract void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException;
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return left + " " + operator + " " + right;
    }
}
