package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;


public class IntegerPattern extends ValuePattern
{
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        token = token.trim();
        if( !context.javaint && (token.charAt(0)=='+' || 
                                (token.charAt(0)=='0' && token.length()>1 ) ) ) throw new Exception();
        return new Integer( token );
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
