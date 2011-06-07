package org.vanb.viva.parameters;

public class FloatRangeParameter extends Parameter
{
    float lo, hi;
    
    public FloatRangeParameter( float low, float high )
    {
        super( Float.class );
        lo = low;
        hi = high;
    }
    
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Float.class && lo <= (Float)value && (Float)value <= hi;
    }
    
    public String usage()
    {
        return "Must be a float between " + lo + " and " + hi;
    }

}
