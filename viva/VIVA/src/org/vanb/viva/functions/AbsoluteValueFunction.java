package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class AbsoluteValueFunction implements ScalarFunction
{
    @Override
    public String getName()
    {
        return "abs";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && Number.class.isAssignableFrom( params[0] ) ? params[0] : null;
    }

    @Override
    public String getUsage()
    {
        return "abs(number)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        Object result = null;
        Object parm = parameters.get( 0 );
        if( parm instanceof Integer )
        {
            Integer i = (Integer)parm;
            result = i<0 ? -i : i;
        }
        else if( parm instanceof Long )
        {
            Long l = (Long)parm;
            result = l<0 ? -l : l;
        }
        else if( parm instanceof Double )
        {
            Double d = (Double)parm;
            result = d<0 ? -d : d;
        }
        else if( parm instanceof Float )
        {
            Float f = (Float)parm;
            result = f<0 ? -f : f;
        }

        return result;
    }

}
