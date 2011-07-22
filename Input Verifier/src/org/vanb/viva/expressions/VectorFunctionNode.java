package org.vanb.viva.expressions;

import java.util.*;

import org.vanb.viva.*;
import org.vanb.viva.utils.*;

public class VectorFunctionNode extends FunctionNode
{
    private VectorFunction function;
    
    public VectorFunctionNode( String name, Class<?> type, VectorFunction f, LinkedList<ExpressionNode> parms )
    {
        super( name, type, parms );
        function = f;
    }
    
    public Object getValue( VIVAContext context ) throws VIVAException
    {
        int saveindex = context.index;
        List<List<Object>> rows = new LinkedList<List<Object>>();
        for( context.index=0; context.index < context.values.getCount(); ++context.index )
        {
            List<Object> parmvalues = new LinkedList<Object>();
            for( ExpressionNode parm : parameters )
            {
                parmvalues.add( parm.evaluate( context ) );
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
