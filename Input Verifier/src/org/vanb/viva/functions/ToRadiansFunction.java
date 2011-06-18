package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class ToRadiansFunction extends ArithmeticFunction
{
    public ToRadiansFunction()
    {
        name = "toradians";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.toRadians( parameter );
    }
}
