package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

public class DepsFunction implements Function
{

    @Override
    public String getName()
    {
        return "deps";
    }

    @Override
    public Class<?> returnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==Double.class ? Boolean.class : null;
    }

    @Override
    public String usage()
    {
        return "deps(double)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
    {
        context.deps = (Double)parameters.get( 0 );
        return true;
    }

}
