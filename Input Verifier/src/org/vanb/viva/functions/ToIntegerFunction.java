package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class ToIntegerFunction implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "tointeger";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 ? Integer.class : null;
    }

    @Override
    public String getUsage()
    {
        return "tointeger(arg)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        Object param = parameters.get( 0 );
        Integer result;
        try
        {
            if( Number.class.isAssignableFrom( param.getClass() ))
            {
                Number value = (Number)param;
                result = value.intValue();
            }
            else if( param.getClass()==Boolean.class )
            {
                result = (Boolean)param ? 1 : 0;
            }
            else
            {
                result = new Integer( param.toString() ); 
            }
        }
        catch( Exception e )
        {
            throw new Exception( "Could not convert '" + param + "' to an Integer"  );
        }
        
        return result;
    }

}
