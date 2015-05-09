package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

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
        context.setParameter( name, value );
        return true;
    }

}
