package org.vanb.viva.functions;

import java.util.List;

public class LengthFunction implements Function
{
    private static Class<?> parameters[] = { String.class };
    
    @Override
    public String getName()
    {
        return "length";
    }

    @Override
    public Class<?>[] parameters()
    {
        return parameters;
    }

    @Override
    public Class<?> returnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==String.class ? String.class : null;
    }
    
    @Override
    public Object run( List<Object> parameters )
    {
        return ((String)parameters.get( 0 )).length();
    }

}
