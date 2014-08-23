package org.vanb.viva.functions;

import java.util.*;

import org.vanb.viva.*;
import org.vanb.viva.utils.*;

public class ToStringFunction implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "tostring";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 ? String.class : null;
    }

    @Override
    public String getUsage()
    {
        return "tostring(arg)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        return parameters.get( 0 ).toString();
    }

}
