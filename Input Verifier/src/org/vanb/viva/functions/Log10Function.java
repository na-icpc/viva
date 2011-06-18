package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class Log10Function extends ArithmeticFunction
{
    public Log10Function()
    {
        name = "log10";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        if( parameter<0.0 )
        {
            throw new Exception( "Parameter to log10() is <0" );
        }
        
        return Math.log10( parameter );
    }

}
