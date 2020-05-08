package org.vanb.viva.parameters;

/**
 * The Class FloatRangeParameter.
 */
public abstract class FloatRangeParameter extends Parameter
{
    
    /** The low value of the range. */
    protected float lo;
    
    /** The high value of the range. */
    protected float hi;
    
    /**
     * Instantiates a new float range parameter.
     *
     * @param name the name
     * @param lo the low value
     * @param hi the high value
     * @param defaultValue the default value
     */
    protected FloatRangeParameter( String name, float lo, float hi, float defaultValue )
    {
        super( name, Float.class, defaultValue );
        this.lo = lo;
        this.hi = hi;
    }
    
    /**
     * Checks if value is valid.
     *
     * @param value the value
     * @return true, if is valid
     */
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Float.class && lo <= (Float)value && (Float)value <= hi;
    }
    
    /**
     * Usage.
     *
     * @return the string
     */
    public String usage()
    {
        return "Must be a float between " + lo + " and " + hi;
    }

    /**
     * Convert token to value.
     *
     * @param token the token
     * @return the object
     */
    public Object convert( String token )
    {
        Float value;
        
        try
        {
            value = Float.parseFloat( token );
        }
        catch( NumberFormatException nfe )
        {
            value = null;
        }
        
        return value;
    }
}
