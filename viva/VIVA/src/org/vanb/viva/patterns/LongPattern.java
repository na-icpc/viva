package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;

public class LongPattern extends ValuePattern
{
    @Override
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        token = token.trim();
        if( !context.javalong && (token.charAt(0)=='+' || 
                                 (token.charAt(0)=='0' && token.length()>1 ) ) ) throw new Exception();
        return new Long( token.trim() );
    }
    
    public Class<?> getType()
    {
        return Long.class;
    }
    
    public Object getDefaultValue()
    {
        return new Long( 0 );
    }

}
