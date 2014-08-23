package org.vanb.viva.functions;

import java.util.*;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.*;

public class LengthFunction implements ScalarFunction
{
    @Override
    public String getName()
    {
        return "length";
    }

    @Override
    public String getUsage()
    {
        return "length(string)";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==String.class ? Integer.class : null;
    }
    
    @Override
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        return parameters.get( 0 ).toString().length();
    }

}
