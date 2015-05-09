package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.VectorFunction;
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
    public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
    {
        HashSet<String> rows = new HashSet<String>();
                
        boolean unique = true;
        
        // Go through each row 
        for( List<Object> row : parameters )
        {
            // Build a "key" - a unique String representation of the row
            String key = "";
            for( Object object : row )
            {
                key += object.toString() + "\256";
            }
            
            // Look to see if we've seen this row before
            if( rows.contains( key ) )
            {
                unique = false;
                break;
            }
            
            // if we haven't seen it before, add this row to the list of seen rows
            rows.add( key );
        }
        return unique;
    }

}
