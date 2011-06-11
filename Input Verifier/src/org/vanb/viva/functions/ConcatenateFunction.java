package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class ConcatenateFunction implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "concat";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return String.class;
    }

    @Override
    public String getUsage()
    {
        return "concat(args1,arg2,...)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        String result = "";
        for( Object parameter : parameters )
        {
            result += parameter.toString();
        }
        return result;
    }

}
