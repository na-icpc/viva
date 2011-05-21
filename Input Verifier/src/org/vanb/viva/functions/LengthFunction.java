package org.vanb.viva.functions;

import java.util.*;
import org.vanb.viva.utils.*;

public class LengthFunction implements ScalarFunction
{
    private static Class<?> parameters[] = { String.class };
    
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
    public Object run( VIVAContext context, List<Object> parameters )
    {
        return ((String)parameters.get( 0 )).length();
    }

}
