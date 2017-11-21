/**
 * 
 */
package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;

/**
 * @author David Van Brackle
 *
 */
public class DoublePattern extends ValuePattern
{

    /**
     * @see org.vanb.viva.patterns.ValuePattern#getValue(java.lang.String)
     */
    @Override
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        token = token.trim();
        if( !context.javadouble && !ValuePattern.goodDouble.matcher( token ).matches() ) throw new Exception();
        return new Double( token );
    }
    
    public Class<?> getType()
    {
        return Double.class;
    }
    
    public Object getDefaultValue()
    {
        return new Double( 0 );
    }
}
