package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

public class ConcatAllFunction implements VectorFunction
{

    @Override
    public Object run( VIVAContext context, List<List<Object>> parameters )
            throws Exception
    {
        StringBuilder sb = new StringBuilder();
        for( List<Object> row : parameters )
        {
            for( Object p : row )
            {
                sb.append( p.toString() );
            }
        }
        
        return sb.toString();
    }

    @Override
    public String getName()
    {
        return "concatall";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return String.class;
    }

    @Override
    public String getUsage()
    {
        return "concatall(can take any parameters)";
    }

}
