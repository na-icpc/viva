package org.vanb.viva.parameters;

public abstract class LongRangeParameter extends Parameter
{
    protected long lo, hi;
    
    protected LongRangeParameter( String name, long lo, long hi, long defaultvalue )
    {
        super( name, Long.class, defaultvalue );
        this.lo = lo;
        this.hi = hi;
    }
    
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Long.class && lo <= (Long)value && (Long)value <= hi;
    }
    
    public String usage()
    {
        return "Must be a long between " + lo + " and " + hi;
    }
    
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
