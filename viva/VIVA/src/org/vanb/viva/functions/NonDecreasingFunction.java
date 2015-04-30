package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

public class NonDecreasingFunction implements VectorFunction
{
    
    @Override
    public String getName()
    {
        return "nondecreasing";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && Number.class.isAssignableFrom( params[0] ) ? Boolean.class : null;
    }

    @Override
    public String getUsage()
    {
        return "nondecreasing( int or long or double or float )";
    }

    @Override
    public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
    {
        double deps = context.deps;
        float feps = context.feps;

        Number last = null;
        boolean success = true;
        Class<?> type = null;
        if( !parameters.isEmpty() ) type = parameters.get(0).get(0).getClass();
        for( List<Object> row : parameters )
        {
            Number next = (Number)row.get( 0 );
            if( last!=null )
            {
                if( type==Integer.class ) success = last.intValue() <= next.intValue(); 
                else if( type==Long.class ) success = last.longValue() <= next.longValue(); 
                else if( type==Double.class ) success = last.doubleValue() <= next.doubleValue() + deps; 
                else if( type==Float.class ) success = last.floatValue() <= next.floatValue() + feps;     
                if( !success ) break;
            }
            
            last = next;
        }
        
        return success;
    }

}
