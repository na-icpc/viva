package org.vanb.viva.expressions;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class NotNode extends UnaryOperatorNode
{

    @Override
    public void instantiate( ExpressionNode arg ) throws ParseException
    {
        if( arg.getReturnType()!=Boolean.class )
        {
            throw new ParseException( "Bad operand to " + operator + " operator: Expecting Boolean, got " + arg.getReturnType().getSimpleName() );  
        }
        argument = arg;
    }

    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        return ! (Boolean)argument.evaluate( context );
    }
}
