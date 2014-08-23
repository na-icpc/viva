package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class ArcSineFunction extends ArithmeticFunction
{
    public ArcSineFunction()
    {
        name = "asin";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        if( parameter<-1.0 || parameter>1.0 )
        {
            throw new Exception( "Argument (" + parameter + ") to asin() is out of range.");
        }
        
        return Math.asin( parameter );
    }
}
