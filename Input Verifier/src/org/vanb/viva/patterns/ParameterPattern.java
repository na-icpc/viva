package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;

public class ParameterPattern implements Pattern
{
    String name;
    Object value;
    
    public ParameterPattern( String n, Object v )
    {
        name = n;
        value = v;
    }
    
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        System.err.println( "Trying to set " + name + " to " + value );
        return true;
    }

}
