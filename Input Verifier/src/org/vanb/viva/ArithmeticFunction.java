package org.vanb.viva;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

public abstract class ArithmeticFunction implements ScalarFunction
{
    protected String name;

    public static void nanCheck( double x, String how ) throws Exception
    {
        String nan = x==Double.NaN ? "NaN" : 
                     x==Double.NEGATIVE_INFINITY ? "-Infinity" :
                     x==Double.POSITIVE_INFINITY ? "Infinity" : null;
        if( nan!=null )
        {
            throw new Exception( "Result of " + how + " is " + nan + "." );
        }
    }
    
    public static void nanCheck( float x, String how ) throws Exception
    {
        String nan = x==Float.NaN ? "NaN" : 
                     x==Float.NEGATIVE_INFINITY ? "-Infinity" :
                     x==Float.POSITIVE_INFINITY ? "Infinity" : null;
        if( nan!=null )
        {
            throw new Exception( "Result of " + how + " is " + nan + "." );
        }
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && Number.class.isAssignableFrom( params[0] ) ? Double.class : null;
    }

    @Override
    public String getUsage()
    {
        return name + "(number)";
    }
    
    protected abstract double implementation( double parameter ) throws Exception;

    @Override
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        Number argument = (Number)parameters.get( 0 );
        double result = implementation( argument.doubleValue() );
        nanCheck( result, name + "(" + argument + ")" );

        return new Double( result );
    }

}
