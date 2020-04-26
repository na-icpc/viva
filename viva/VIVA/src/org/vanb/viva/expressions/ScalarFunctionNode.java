package org.vanb.viva.expressions;

import java.util.LinkedList;

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
    public ScalarFunctionNode( String name, Class<?> type, ScalarFunction f, LinkedList<ExpressionNode> parms )
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
            e.printStackTrace();
            context.throwException( e.getMessage() );   
        }
        
        return result;    
    }

}
