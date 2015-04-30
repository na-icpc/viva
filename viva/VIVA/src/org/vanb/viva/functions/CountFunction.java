package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

public class CountFunction implements VectorFunction
{

    @Override
    public String getName()
    {
        return "count";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==Boolean.class ? Integer.class : null;
    }

    @Override
    public String getUsage()
    {
        return "count( boolean )";
    }

    @Override
    public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
    {
        int count = 0;
        
        for( List<Object> row : parameters )
        {
            Boolean result = (Boolean)row.get( 0 );
            if( result ) ++count;
        }                

        return new Integer( count );
    }

}
