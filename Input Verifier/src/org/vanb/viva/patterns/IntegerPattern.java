package org.vanb.viva.patterns;


public class IntegerPattern extends ValuePattern
{
    public Object getValue( String token ) throws Exception
    {
        return new Integer( token );
    }
    
    
    public String getType()
    {
        return "Integer";
    }
}
