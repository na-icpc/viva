package org.vanb.viva.expressions;

import java.util.*;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.*;

public class ScalarFunctionNode extends FunctionNode
{
    private ScalarFunction function;
    
    public ScalarFunctionNode( String name, Class<?> type, ScalarFunction f, LinkedList<ExpressionNode> parms )
    {
        super( name, type, parms );
        function = f;
    }
    

    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        LinkedList<Object> parmvalues = new LinkedList<Object>();
        for( ExpressionNode parm : parameters )
        {
            parmvalues.add( parm.evaluate( context ) );
        }
        
        Object result = null;
        try
        {
            result = function.run( context, parmvalues );
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );   
        }
        
        return result;    
    }

}
