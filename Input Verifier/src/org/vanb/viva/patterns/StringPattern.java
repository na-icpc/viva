package org.vanb.viva.patterns;


public class StringPattern extends ValuePattern
{
    @Override
    public Object getValue( String token ) throws Exception
    {
        return token;
    }
    
    public String getType()
    {
        return "String";
    }
}
