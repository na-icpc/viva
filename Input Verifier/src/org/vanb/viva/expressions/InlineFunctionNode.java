package org.vanb.viva.expressions;

import java.util.*;

import org.vanb.viva.functions.Function;
import org.vanb.viva.utils.*;

public class InlineFunctionNode extends FunctionNode
{
    public InlineFunctionNode( String name, Class<?> type, Function f, LinkedList<ExpressionNode> parms )
    {
        super( name, type, f, parms );
    }
    

    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        LinkedList<Object> parmvalues = new LinkedList<Object>();
        for( ExpressionNode parm : parameters )
        {
            parmvalues.add( parm.evaluate( context ) );
        }
        return function.run( context, parmvalues );   
    }

}
