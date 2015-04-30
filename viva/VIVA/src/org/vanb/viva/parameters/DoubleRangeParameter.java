package org.vanb.viva.parameters;

public abstract class DoubleRangeParameter extends Parameter
{
    protected double lo, hi;
    
    public DoubleRangeParameter( String name, double lo, double hi, double defaultValue )
    {
        super( name, Double.class, defaultValue );
        this.lo = lo;
        this.hi = hi;
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
