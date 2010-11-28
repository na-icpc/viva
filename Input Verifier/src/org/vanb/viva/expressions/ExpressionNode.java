package org.vanb.viva.expressions;

import org.vanb.viva.utils.*;

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
	 * @param values The current list of variable values
	 * @return The value of this expression
	 */
    public Object evaluate( SymbolTable<ValueManager> values );
    
    /**
     * Tell the return type of this expression.
     * 
     * @return The return type of this expression.
     */
    public Class<?> returnType();
}
