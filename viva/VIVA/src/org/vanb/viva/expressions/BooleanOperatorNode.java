/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * Superclass for Boolean operators.
 * This one is a little tricky. It's just like a NumberBinaryOperatorNode, except that it can handle Strings.
 * So, Strings are handled as a special case here. If the comparison isn't on Strings, it goes to the superclass.
 */
public abstract class BooleanOperatorNode extends NumberBinaryOperatorNode
{
    
    /**
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#instantiate(org.vanb.viva.expressions.ExpressionNode, org.vanb.viva.expressions.ExpressionNode)
     */
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        if( String.class.equals( lhs.getReturnType() ) || String.class.equals( rhs.getReturnType() ) )
        {
            // Can't compare a String to anything other than another String
            if( !lhs.getReturnType().equals( rhs.getReturnType() ) )
            {
                throw new ParseException( "Bad operands to " + operator + " operator: cannot compare " + lhs.getReturnType().getSimpleName() + " to " + rhs.getReturnType().getSimpleName() );
            }
            left = lhs;
            right = rhs;
            type = String.class;
        }
        else super.instantiate( lhs, rhs );
    }

    /**
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(org.vanb.viva.utils.VIVAContext)
     */
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Object result = null;
        if( String.class.equals( type ) )
        {
            try
            {
                result = evaluate( (String)left.evaluate(context), (String)right.evaluate(context) );
            }
            catch( Exception e )
            {
                context.err.println( this );
                context.throwException( e.getMessage() );                  
            }
        }
        else result = super.evaluate( context );
        
        return result;
    }
    
    /**
     * Evaluate a comparison on Strings.
     *
     * @param l the left-hand expression
     * @param r the right-hand expression
     * @return A Boolean, true if the comparison is satisfied
     * @throws Exception If anything goes wrong
     */
    public abstract Object evaluate( String l, String r ) throws Exception;
    
    /**
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#getReturnType()
     */
    @Override
    public Class<?> getReturnType()
    {
        return Boolean.class;
    }
}
