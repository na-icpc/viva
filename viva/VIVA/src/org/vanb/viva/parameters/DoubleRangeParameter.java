/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

/**
 * The Class DoubleRangeParameter.
 */
public abstract class DoubleRangeParameter extends Parameter
{
    
    /** The low value of the range. */
    protected double lo;
    
    /** The high value of the range. */
    protected double hi;
    
    /**
     * Instantiates a new double range parameter.
     *
     * @param name the name
     * @param lo the low value
     * @param hi the high value
     * @param defaultValue the default value
     */
    protected DoubleRangeParameter( String name, double lo, double hi, double defaultValue )
    {
        super( name, Double.class, defaultValue );
        this.lo = lo;
        this.hi = hi;
    }
    
    /**
     * Checks if parameter is valid.
     *
     * @param value the value
     * @return true, if is valid
     */
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Double.class && lo <= (Double)value && (Double)value <= hi;
    }
    
    /**
     * Usage.
     *
     * @return the string
     */
    @Override
    public String usage()
    {
        return "Must be a double between " + lo + " and " + hi;
    }

    /**
     * Convert a token to its double value.
     *
     * @param token the token
     * @return the object
     */
    @Override
    public Object convert( String token )
    {
        Double value;
        
        try
        {
            value = Double.parseDouble( token );
        }
        catch( NumberFormatException nfe )
        {
            value = null;
        }
        
        return value;
    }
}
