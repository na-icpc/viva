/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import java.util.ArrayList;
import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class VectorFunctionNode.
 */
public class VectorFunctionNode extends FunctionNode
{
    
    /** The function. */
    private VectorFunction function;
    
    /**
     * Instantiates a new vector function node.
     *
     * @param name the name
     * @param type the type
     * @param f the function
     * @param parms the parms
     */
    public VectorFunctionNode( String name, Class<?> type, VectorFunction f, List<ExpressionNode> parms )
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
    @Override
    public Object getValue( VIVAContext context ) throws VIVAException
    {
        int saveindex = context.index;
        List<List<Object>> rows = new ArrayList<List<Object>>(context.values.getCount());
        for( context.index=0; context.index < context.values.getCount(); ++context.index )
        {
            List<Object> parmvalues = new ArrayList<Object>(parameters.size());
            for( ExpressionNode parm : parameters )
            {
                try
                {
                    parmvalues.add( parm.evaluate( context ) );
                }
                catch( NullPointerException npe )
                {
                    parmvalues.add( null );    
                }
            }
            rows.add( parmvalues );
        }
        
        context.index = saveindex;
        
        Object result = null;
        try
        {
            result = function.run( context, rows );
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );   
        }
        
        return result;    
    }

}
