package org.vanb.viva;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

/**
 * A class for Arithmetic Functions which implements a lot of the things that are the same.
 * This is a base class for all functions which take a single number and return a double, such as
 * sqrt(), exp(), log(), sin(), cos(), etc.
 * 
 * @author vanb
 */
public abstract class ArithmeticFunction implements ScalarFunction
{
    protected String name;

    /**
     * Check to see if a result is not a number.
     * 
     * @param x Result to check
     * @param how How the result was derived
     * @throws Exception Throws a generic Exception if x is NaN
     */
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
    
    /**
     * Check to see if a result is not a number.
     * 
     * @param x Result to check
     * @param how How the result was derived
     * @throws Exception Throws a generic Exception if x is NaN
     */
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
    /**
     * Get this function's name.
     * 
     * @return This function's name
     */
    public String getName()
    {
        return name;
    }

    @Override
    /**
     * Check this function's parameters. It should take exactly one number.
     * 
     * @param params List of parameters to check
     * @return Double.class if parameter is OK, null if not.
     */
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && Number.class.isAssignableFrom( params[0] ) ? Double.class : null;
    }

    @Override
    /**
     * Get a String describing this function's usage, which should be the same for all Arithmetic Functions.
     * 
     * @return A String describing this function's usage
     */
    public String getUsage()
    {
        return name + "(number)";
    }
    
    /**
     * This is what individual functions implement.
     * 
     * @param parameter Parameter passed to the function
     * @return Return value from the function
     * @throws Exception If anything goes wrong, like passing a negative to sqrt().
     */
    protected abstract double implementation( double parameter ) throws Exception;

    @Override
    /**
     * Execute this function.
     * 
     * @param context VIVA Context
     * @param parameters List of parameters passed to the function
     * @return The result of the function
     * @throws A generic exeption if anything goes wrong
     */
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        Number argument = (Number)parameters.get( 0 );
        double result = implementation( argument.doubleValue() );
        nanCheck( result, name + "(" + argument + ")" );

        return new Double( result );
    }

}