package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class TestFunction implements ScalarFunction
{

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
        return "test(args1,arg2,...)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        for( Object parameter : parameters )
        {
            context.err.print( "<" + parameter + ">" );
        }
        context.err.println();
        
        return new Boolean(true);
    }

}
