package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;

/**
 * The Class UnaryOperatorNode.
 */
public abstract class UnaryOperatorNode implements ExpressionNode
{
    
    /** The argument. */
    protected ExpressionNode argument;
    
    /** The operator. */
    public String operator;
    
    /**
     * Instantiate.
     *
     * @param arg the arg
     * @throws ParseException the parse exception
     */
    public abstract void instantiate( ExpressionNode arg ) throws ParseException;
        
    /**
     * To string.
     *
     * @return the string
     */
    public String toString()
    {
        return operator + argument;
    }
    
    /**
     * Gets the return type.
     *
     * @return the return type
     */
    public Class<?> getReturnType()
    {
        return argument.getReturnType();
    }

}
