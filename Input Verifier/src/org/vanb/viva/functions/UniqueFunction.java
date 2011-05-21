package org.vanb.viva.functions;

import java.util.*;

import org.vanb.viva.utils.VIVAContext;

public class UniqueFunction implements VectorFunction
{

    @Override
    public String getName()
    {
        return "unique";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return Boolean.class;
    }

    @Override
    public String getUsage()
    {
        return "unique( <can take any parameters> )";
    }

    @Override
    public Object run( VIVAContext context, List<List<Object>> parameters )
    {
        HashSet<String> rows = new HashSet<String>();
                
        boolean unique = true;
        
        for( List<Object> row : parameters )
        {
            String key = "";
            for( Object object : row )
            {
                key += object.toString() + " ";
            }
            
            if( rows.contains( key ) )
            {
                unique = false;
                break;
            }
            rows.add( key );
        }
        return unique;
    }

}
