package org.vanb.viva.parameters;

public class DoubleRangeParameter extends Parameter
{
    double lo, hi;
    
    public DoubleRangeParameter( double low, double high )
    {
        super( Double.class );
        lo = low;
        hi = high;
    }
    
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Double.class && lo <= (Double)value && (Double)value <= hi;
    }
    
    public String usage()
    {
        return "Must be a double between " + lo + " and " + hi;
    }

}
