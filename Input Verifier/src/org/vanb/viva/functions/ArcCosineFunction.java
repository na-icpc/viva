package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class ArcCosineFunction extends ArithmeticFunction
{
    public ArcCosineFunction()
    {
        name = "acos";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        if( parameter<-1.0 || parameter>1.0 )
        {
            throw new Exception( "Argument (" + parameter + ") to acos() is out of range.");
        }
        
        return Math.acos( parameter );
    }
}
