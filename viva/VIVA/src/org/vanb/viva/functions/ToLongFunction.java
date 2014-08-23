package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class ToLongFunction implements ScalarFunction
{
    @Override
    public String getName()
    {
        return "tolong";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 ? Long.class : null;
    }

    @Override
    public String getUsage()
    {
        return "tolong(arg)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        Object param = parameters.get( 0 );
        Long result;
        try
        {
            if( Number.class.isAssignableFrom( param.getClass() ))
            {
                Number value = (Number)param;
                result = value.longValue();
            }
            else if( param.getClass()==Boolean.class )
            {
                result = (Boolean)param ? 1L : 0L;
            }
            else
            {
                result = new Long( param.toString() ); 
            }
        }
        catch( Exception e )
        {
            throw new Exception( "Could not convert '" + param + "' to a Long"  );
        }
        
        return result;
    }
}
