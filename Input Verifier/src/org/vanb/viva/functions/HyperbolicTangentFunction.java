package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class HyperbolicTangentFunction extends ArithmeticFunction
{
    public HyperbolicTangentFunction()
    {
        name = "tanh";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.tanh( parameter );
    }
}
