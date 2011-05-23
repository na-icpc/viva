package org.vanb.viva.functions;

public class SquareRootFunction extends ArithmeticFunction
{
    public SquareRootFunction()
    {
        name = "sqrt";
    }
    
    @Override
    protected double implementation( double parameter )
    {
        return Math.sqrt( parameter );
    }

}
