package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

public class FepsFunction implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "feps";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==Float.class ? Boolean.class : null;
    }

    @Override
    public String getUsage()
    {
        return "feps(float)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
    {
        context.feps = (Float)parameters.get( 0 );
        return true;
    }

}