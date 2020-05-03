package org.vanb.viva.expressions;

import java.util.ArrayList;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class ScalarFunctionNode.
 */
public class ScalarFunctionNode extends FunctionNode
{
    
    /** The function. */
    private ScalarFunction function;
    
    /**
     * Instantiates a new scalar function node.
     *
     * @param name the name
     * @param type the type
     * @param f the f
     * @param parms the parms
     */
    public ScalarFunctionNode( String name, Class<?> type, ScalarFunction f, List<ExpressionNode> parms )
    {
        super( name, type, parms );
        function = f;
    }
   
    /**
     * Gets the value.
     *
     * @param context the context
     * @return the value
     * @throws VIVAException the VIVA exception
     */
    public Object getValue( VIVAContext context ) throws VIVAException
    {
        List<Object> parmvalues = new ArrayList<Object>(parameters.size());
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
