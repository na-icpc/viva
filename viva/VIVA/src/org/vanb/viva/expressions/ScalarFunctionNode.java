package org.vanb.viva.expressions;

import java.util.LinkedList;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class ScalarFunctionNode extends FunctionNode
{
    private ScalarFunction function;
    
    public ScalarFunctionNode( String name, Class<?> type, ScalarFunction f, LinkedList<ExpressionNode> parms )
    {
        super( name, type, parms );
        function = f;
    }
    

    public Object getValue( VIVAContext context ) throws VIVAException
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
