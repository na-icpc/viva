package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class HyperbolicSineFunction extends ArithmeticFunction
{
    public HyperbolicSineFunction()
    {
        name = "sinh";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.sinh( parameter );
    }
}
