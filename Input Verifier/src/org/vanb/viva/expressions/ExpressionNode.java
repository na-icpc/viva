package org.vanb.viva.expressions;

/**
 * Base type of a node of an Expression tree
 * 
 * @author vanb
 */
public interface ExpressionNode 
{
	/**
	 * Evaluate the expression rooted at this node.
	 * 
	 * @return The value of this expression
	 */
    public Object evaluate();
    
    /**
     * Tell the return type of this expression.
     * 
     * @return The return type of this expression.
     */
    public Class<?> returnType();
}
