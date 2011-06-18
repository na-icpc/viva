package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class CosineFunction extends ArithmeticFunction
{
    public CosineFunction()
    {
        name = "cos";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.cos( parameter );
    }
}
