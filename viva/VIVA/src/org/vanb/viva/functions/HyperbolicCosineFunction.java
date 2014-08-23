package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class HyperbolicCosineFunction extends ArithmeticFunction
{
    public HyperbolicCosineFunction()
    {
        name = "cosh";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.cosh( parameter );
    }
}
