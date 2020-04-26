package org.vanb.viva.utils;

/**
 * Some generally useful utilities.
 * 
 * @author vanb
 */
public class Utilities
{

    /**
     * Can't instantiate one of these..
     */
    private Utilities() {}
    
    /**
     * Checks if a type is discrete (Integer, long or String).
     *
     * @param type the type
     * @return true, if the type is discrete
     */
    public static boolean isDiscrete( Class<?> type )
    {
        return type==Integer.class || type==Long.class || type==String.class;
    }

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
}
