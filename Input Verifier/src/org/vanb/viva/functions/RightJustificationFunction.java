package org.vanb.viva.functions;

import java.util.*;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.*;

public class RightJustificationFunction implements ScalarFunction
{
    @Override
    public String getName()
    {
        return "rjust";
    }

    @Override
    public String getUsage()
    {
        return "rjust(string)";
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
        char lastch = value.charAt( value.length()-1 );
        return !Character.isWhitespace( lastch );
    }

}
