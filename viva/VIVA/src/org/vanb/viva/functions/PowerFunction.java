package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ArithmeticFunction;
import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class PowerFunction implements ScalarFunction
{    
    @Override
    public String getName()
    {
        return "pow";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==2
            && Number.class.isAssignableFrom( params[0] )
            && Number.class.isAssignableFrom( params[1] )
                ? Double.class : null;
    }

    @Override
    public String getUsage()
    {
        return "pow(number,number)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        double argument = ((Number)parameters.get(0)).doubleValue();        
        double exponent = ((Number)parameters.get(1)).doubleValue();
        
        double result = Math.pow( argument, exponent );
        ArithmeticFunction.nanCheck( result, "pow(" + argument + "," + exponent + ")" );
        
        return result;
    }

}
