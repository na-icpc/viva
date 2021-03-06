/**
 * 
 */
package org.vanb.viva.patterns;


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
    public Object getValue( String token ) throws Exception
    {
        return new Double( token.trim() );
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
