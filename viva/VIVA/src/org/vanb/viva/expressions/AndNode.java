package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class AndNode.
 */
public class AndNode extends BinaryOperatorNode
{
    
    /**
     * Instantiate.
     *
     * @param lhs the lhs
     * @param rhs the rhs
     * @throws ParseException the parse exception
     */
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs ) throws ParseException
    {
        operator = "&&";
        left = lhs;
        right = rhs;
        if( !lhs.getReturnType().equals( Boolean.class )) 
        {
            throw new ParseException( "Bad left operand to && operator: Expecting Boolean, got " + lhs.getReturnType().getSimpleName() );
        }
        if( !rhs.getReturnType().equals( Boolean.class ))
        {
            throw new ParseException( "Bad right operand to && operator: Expecting Boolean, got " + rhs.getReturnType().getSimpleName() );
        }
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
        Boolean result = (Boolean)left.evaluate( context );
        if( result )
        {
            result = (Boolean)right.evaluate( context );
        }
        return result;
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public Class<?> getReturnType()
    {
        return Boolean.class;
    }

}
