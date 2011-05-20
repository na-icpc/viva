package org.vanb.viva.functions;

import java.util.*;
import org.vanb.viva.utils.*;

public class LengthFunction implements Function
{
    private static Class<?> parameters[] = { String.class };
    
    @Override
    public String getName()
    {
        return "length";
    }

    @Override
    public String usage()
    {
        return "length(string)";
    }

    @Override
    public Class<?> returnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==String.class ? Integer.class : null;
    }
    
    @Override
    public Object run( VIVAContext context, List<Object> parameters )
    {
        return ((String)parameters.get( 0 )).length();
    }

}
