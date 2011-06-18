package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class ToFloatFunction implements ScalarFunction
{
    @Override
    public String getName()
    {
        return "tofloat";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 ? Float.class : null;
    }

    @Override
    public String getUsage()
    {
        return "tofloat(arg)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        Object param = parameters.get( 0 );
        Float result;
        try
        {
            if( Number.class.isAssignableFrom( param.getClass() ))
            {
                Number value = (Number)param;
                result = value.floatValue();
            }
            else if( param.getClass()==Boolean.class )
            {
                result = (Boolean)param ? 1.0F : 0.0F;
            }
            else
            {
                result = new Float( param.toString() ); 
            }
        }
        catch( Exception e )
        {
            throw new Exception( "Could not convert '" + param + "' to a Float"  );
        }
        
        return result;
    }
}
