package org.vanb.viva.functions;

import org.vanb.viva.ArithmeticFunction;

public class ArcTangentFunction extends ArithmeticFunction
{
    public ArcTangentFunction()
    {
        name = "atan";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        return Math.atan( parameter );
    }
}
