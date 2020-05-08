package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class ParenthesesNode.
 */
public class ParenthesesNode implements ExpressionNode
{
    
    /** The exp. */
    private ExpressionNode exp;
    
    /**
     * Instantiates a new parentheses node.
     *
     * @param expression the expression
     */
    public ParenthesesNode( ExpressionNode expression )
    {
        exp = expression;
    }
    
    /**
     * Evaluate.
     *
     * @param context the context
     * @return the object
     * @throws VIVAException the VIVA exception
     */
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        return exp.evaluate( context );
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public Class<?> getReturnType()
    {
        return exp.getReturnType();
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    public String toString()
    {
        return "(" + exp + ")";
    }

}
