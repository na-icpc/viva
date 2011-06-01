package org.vanb.viva.functions;

public class NaturalLogFunction extends ArithmeticFunction
{
    public NaturalLogFunction()
    {
        name = "ln";
    }
    
    @Override
    protected double implementation( double parameter ) throws Exception
    {
        if( parameter<0.0 )
        {
            throw new Exception( "Parameter to ln() is <0" );
        }
        
        return Math.log( parameter );
    }

}
