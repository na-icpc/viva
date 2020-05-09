/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class LongPattern.
 */
public class LongPattern extends ValuePattern
{
    
    /**
     * Gets the value.
     *
     * @param token the token
     * @param context the context
     * @return the value
     * @throws Exception the exception
     */
    @Override
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        token = token.trim();
        if( !context.javalong && (token.charAt(0)=='+' || 
                                 (token.charAt(0)=='0' && token.length()>1 ) ) ) throw new Exception();
        return new Long( token.trim() );
    }
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class<?> getType()
    {
        return Long.class;
    }
    
    /**
     * Gets the default value.
     *
     * @return the default value
     */
    @Override
    public Object getDefaultValue()
    {
        return new Long( 0 );
    }

}
