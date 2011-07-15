package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class Log2Function extends ArithmeticFunction
{
    private static final double log10of2 = Math.log10( 2.0 );
    
    public Log2Function()
    {
        name = "log2";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        if( parameter<0.0 )
        {
            throw new Exception( "Parameter to log2() is <0" );
        }
        
        return Math.log10( parameter ) / log10of2;
    }

}
