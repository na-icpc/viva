package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

public class TestAllFunction implements VectorFunction
{

    @Override
    public Object run( VIVAContext context, List<List<Object>> parameters )
            throws Exception
    {
        for( List<Object> row : parameters )
        {
            for( Object p : row )
            {
                context.err.print( "<" + p + ">" );
            }
            context.err.println();
        }
        context.err.println( "---------------" );
        
        return Boolean.TRUE;
    }

    @Override
    public String getName()
    {
        return "testall";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return Boolean.class;
    }

    @Override
    public String getUsage()
    {
        return "testall(can take any parameters)";
    }

}
