package org.vanb.viva.functions;

public class SquareRootFunction extends ArithmeticFunction
{
    public SquareRootFunction()
    {
        name = "sqrt";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        if( parameter<0.0 )
        {
            throw new Exception( "Parameter to sqrt() is <0" );
        }
        
        return Math.sqrt( parameter );
    }

}
