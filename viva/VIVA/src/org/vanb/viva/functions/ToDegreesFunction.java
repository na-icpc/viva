package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class ToDegreesFunction extends ArithmeticFunction
{
    public ToDegreesFunction()
    {
        name = "todegrees";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.toDegrees( parameter );
    }
}
