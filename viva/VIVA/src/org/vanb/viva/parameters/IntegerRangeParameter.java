package org.vanb.viva.parameters;

/**
 * The Class IntegerRangeParameter.
 */
public abstract class IntegerRangeParameter extends Parameter
{
    
    /** The low value of the range. */
    protected int lo;
    
    /** The high value of the range. */
    protected int hi;
    
    /**
     * Instantiates a new integer range parameter.
     *
     * @param name the name
     * @param lo the low value
     * @param hi the high value
     * @param defaultValue the default value
     */
    protected IntegerRangeParameter( String name, int lo, int hi, int defaultValue )
    {
        super( name, Integer.class, defaultValue );
        this.lo = lo;
        this.hi = hi;
    }
    
    /**
     * Checks if is valid.
     *
     * @param value the value
     * @return true, if is valid
     */
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Integer.class && lo <= (Integer)value && (Integer)value <= hi;
    }
    
    /**
     * Usage.
     *
     * @return the string
     */
    public String usage()
    {
        return "Must be an integer between " + lo + " and " + hi;
    }
   
    /**
     * Convert token to value.
     *
     * @param token the token
     * @return the object
     */
    public Object convert( String token )
    {
        Integer value;
        
        try
        {
            value = Integer.parseInt( token );
        }
        catch( NumberFormatException nfe )
        {
            value = null;
        }
        
        return value;
    }

}
