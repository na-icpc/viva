/**
 * 
 */
package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class DoublePattern.
 *
 * @author David Van Brackle
 */
public class DoublePattern extends ValuePattern
{

    /**
     * Gets the value.
     *
     * @param token the token
     * @param context the context
     * @return the value
     * @throws Exception the exception
     * @see org.vanb.viva.patterns.ValuePattern#getValue(java.lang.String)
     */
    @Override
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        token = token.trim();
        if( !context.javadouble && !ValuePattern.goodDouble.matcher( token ).matches() ) throw new Exception();
        return new Double( token );
    }
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class<?> getType()
    {
        return Double.class;
    }
    
    /**
     * Gets the default value.
     *
     * @return the default value
     */
    @Override
    public Object getDefaultValue()
    {
        return new Double( 0 );
    }
}
