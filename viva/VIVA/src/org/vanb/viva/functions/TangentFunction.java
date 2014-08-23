package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class TangentFunction extends ArithmeticFunction
{
    public TangentFunction()
    {
        name = "tan";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.tan( parameter );
    }
}
