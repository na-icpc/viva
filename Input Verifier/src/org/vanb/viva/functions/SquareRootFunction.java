package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class SquareRootFunction extends ArithmeticFunction
{
    public SquareRootFunction()
    {
        name = "sqrt";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        if( parameter<0.0 )
        {
            throw new Exception( "Parameter (" + parameter + ") to sqrt() is <0" );
        }
        
        return Math.sqrt( parameter );
    }

}
