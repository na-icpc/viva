package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

public class TesterFunction implements VectorFunction
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
        return new Boolean(true);
    }

    @Override
    public String getName()
    {
        return "test";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return Boolean.class;
    }

    @Override
    public String getUsage()
    {
        return "test(can take any parameters)";
    }

}
