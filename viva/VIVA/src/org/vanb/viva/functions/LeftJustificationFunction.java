package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class LeftJustificationFunction implements ScalarFunction
{
    @Override
    public String getName()
    {
        return "ljust";
    }

    @Override
    public String getUsage()
    {
        return "ljust(string)";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==String.class ? Boolean.class : null;
    }
    
    @Override
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        String value = parameters.get( 0 ).toString();
        char firstch = value.charAt( 0 );
        return !Character.isWhitespace( firstch );
    }

}
