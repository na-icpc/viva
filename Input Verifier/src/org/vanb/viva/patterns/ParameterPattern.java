package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;
import org.vanb.viva.expressions.*;

public class ParameterPattern implements Pattern
{
    String name;
    ExpressionNode value;
    
    public ParameterPattern( String n, ExpressionNode v)
    {
        name = n;
        value = v;
    }
    
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        Object result = value.evaluate( context );
        System.err.println( "Trying to set " + name + " to " + result + " (" + value + ")" );
        return true;
    }

}
