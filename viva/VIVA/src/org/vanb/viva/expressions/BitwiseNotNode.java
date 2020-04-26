package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class BitwiseNotNode.
 */
public class BitwiseNotNode extends UnaryOperatorNode
{
    
    /** The type. */
    Class<?> type;
    
    /**
     * Instantiate.
     *
     * @param arg the operand
     * @throws ParseException the parse exception
     */
    @Override
    public void instantiate( ExpressionNode arg ) throws ParseException
    {
        type = arg.getReturnType();
        if( type!=Integer.class && type!=Long.class )
        {
            throw new ParseException( "Bad operand to " + operator + " operator: Expecting Integer or Long, got " + arg.getReturnType().getSimpleName() );  
        }
        argument = arg;
    }

    /**
     * Evaluate.
     *
     * @param context the context
     * @return the result
     * @throws VIVAException the VIVA exception
     */
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Object arg = argument.evaluate( context );
        Object result = (type==Long.class) ? ~(Long)arg : ~(Integer)arg;
        return  result;
    }
}
