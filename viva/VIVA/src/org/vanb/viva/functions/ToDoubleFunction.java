package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class ToDoubleFunction implements ScalarFunction
{
    @Override
    public String getName()
    {
        return "todouble";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 ? Double.class : null;
    }

    @Override
    public String getUsage()
    {
        return "todouble(arg)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        Object param = parameters.get( 0 );
        Double result;
        try
        {
            if( Number.class.isAssignableFrom( param.getClass() ))
            {
                Number value = (Number)param;
                result = value.doubleValue();
            }
            else if( param.getClass()==Boolean.class )
            {
                result = (Boolean)param ? 1.0 : 0.0;
            }
            else
            {
                result = new Double( param.toString() ); 
            }
        }
        catch( Exception e )
        {
            throw new Exception( "Could not convert '" + param + "' to a Double"  );
        }
        
        return result;
    }

}
