package org.vanb.viva.parameters;

public class LongRangeParameter extends Parameter
{
    long lo, hi;
    
    public LongRangeParameter( long low, long high )
    {
        super( Long.class );
        lo = low;
        hi = high;
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

}
