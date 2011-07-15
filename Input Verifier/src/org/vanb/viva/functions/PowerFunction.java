package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class PowerFunction implements ScalarFunction
{
    public String NotANumber( double x )
    {
        return x==Double.NaN ? "NaN" : 
               x==Double.NEGATIVE_INFINITY ? "-Infinity" :
               x==Double.POSITIVE_INFINITY ? "Infinity" : null;
    }
    
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
        String nan = NotANumber( argument );
        if( nan!=null )
        {
            throw new Exception( "First parameter to pow() is " + nan + "." );
        }
        
        double exponent = ((Number)parameters.get(1)).doubleValue();
        nan = NotANumber( argument );
        if( nan!=null )
        {
            throw new Exception( "Second parameter to pow() is " + nan + "." );
        }
        
        double result = Math.pow( argument, exponent );
        nan = NotANumber( result );
        if( nan!=null )
        {
            throw new Exception( "Result of pow(" + argument + "," + exponent + ") is " + nan + "." );
        }
        
        return result;
    }

}
