package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class StringPattern.
 */
public class StringPattern extends ValuePattern
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
        return token;
    }
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    public Class<?> getType()
    {
        return String.class;
    }
      
    /**
     * Gets the default value.
     *
     * @return the default value
     */
    public Object getDefaultValue()
    {
        return "";
    }

}
