/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

/**
 * The Class LongRangeParameter.
 */
public abstract class LongRangeParameter extends Parameter
{
    
    /** The low value of the range. */
    protected long lo;
    
    /** The high value of the range. */
    protected long hi;
    
    /**
     * Instantiates a new long range parameter.
     *
     * @param name the name
     * @param lo the low value
     * @param hi the high value
     * @param defaultvalue the defaultvalue
     */
    protected LongRangeParameter( String name, long lo, long hi, long defaultvalue )
    {
        super( name, Long.class, defaultvalue );
        this.lo = lo;
        this.hi = hi;
    }
    
    /**
     * Checks if the value is valid.
     *
     * @param value the value
     * @return true, if is valid
     */
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Long.class && lo <= (Long)value && (Long)value <= hi;
    }
    
    /**
     * Usage.
     *
     * @return the string
     */
    @Override
    public String usage()
    {
        return "Must be a long between " + lo + " and " + hi;
    }
    
    /**
     * Convert token to value.
     *
     * @param token the token
     * @return the object
     */
    @Override
    public Object convert( String token )
    {
        Long value;
        
        try
        {
            value = Long.parseLong( token );
        }
        catch( NumberFormatException nfe )
        {
            value = null;
        }
        
        return value;
    }

}
