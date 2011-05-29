package org.vanb.viva.expressions;

import java.util.*;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.functions.*;
import org.vanb.viva.utils.*;

public class VectorFunctionNode extends FunctionNode
{
    private VectorFunction function;
    
    public VectorFunctionNode( String name, Class<?> type, VectorFunction f, LinkedList<ExpressionNode> parms )
    {
        super( name, type, parms );
        function = f;
    }
    
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
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
        
        context.index = -1;
        
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
