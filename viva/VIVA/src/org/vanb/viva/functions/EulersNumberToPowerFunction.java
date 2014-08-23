package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class EulersNumberToPowerFunction extends ArithmeticFunction
{
    public EulersNumberToPowerFunction()
    {
        name = "exp";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.exp( parameter );
    }
}
