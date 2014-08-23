package org.vanb.viva.patterns;


public class IntegerPattern extends ValuePattern
{
    public Object getValue( String token ) throws Exception
    {
        return new Integer( token.trim() );
    }
    
    
    public Class<?> getType()
    {
        return Integer.class;
    }
       
    public Object getDefaultValue()
    {
        return new Integer( 0 );
    }

}
