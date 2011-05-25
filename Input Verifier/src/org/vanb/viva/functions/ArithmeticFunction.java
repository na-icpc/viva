package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

public abstract class ArithmeticFunction implements ScalarFunction
{
    protected String name;
    
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && Number.class.isAssignableFrom( params[0] ) ? Double.class : null;
    }

    @Override
    public String getUsage()
    {
        return name + "(double)";
    }
    
    protected abstract double implementation( double parameter ) throws Exception;

    @Override
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        return new Double( implementation( ((Number)parameters.get( 0 )).doubleValue() ) );
    }

}
