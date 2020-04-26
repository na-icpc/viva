package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

public class IncreasingFunction implements VectorFunction
{
    
    @Override
    public String getName()
    {
        return "increasing";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 ? Boolean.class : null;
    }

    @Override
    public String getUsage()
    {
        return "increasing( int or long or double or float )";
    }

    @Override
    public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
    {
        Object last = null;
        boolean success = true;
        Class<?> type = null;
        if( !parameters.isEmpty() ) type = parameters.get(0).get(0).getClass();
        for( List<Object> row : parameters )
        {
            Object next = row.get( 0 );
            if( last!=null )
            {
                if( type==Integer.class ) success = ((Number)last).intValue() < ((Number)next).intValue(); 
                else if( type==Long.class ) success = ((Number)last).longValue() < ((Number)next).longValue(); 
                else if( type==Double.class ) success = ((Number)last).doubleValue() < ((Number)next).doubleValue(); 
                else if( type==Float.class ) success = ((Number)last).floatValue() < ((Number)next).floatValue();  
                else if( type==String.class ) success = last.toString().compareTo( next.toString() ) < 0;
                if( !success ) break;
            }
            
            last = next;
        }
        
        return success;
    }

}
