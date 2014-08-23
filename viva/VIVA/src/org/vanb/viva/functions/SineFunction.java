package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class SineFunction extends ArithmeticFunction
{
    public SineFunction()
    {
        name = "sin";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.sin( parameter );
    }
}
