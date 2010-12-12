package org.vanb.viva.patterns;


public class IntegerPattern extends ValuePattern
{
    public Object getValue( String token ) throws Exception
    {
        return new Integer( token );
    }
    
    
    public Class<?> getType()
    {
        return Integer.class;
    }
}
