package org.vanb.viva.parameters;

public abstract class FloatRangeParameter extends Parameter
{
    protected float lo, hi;
    
    protected FloatRangeParameter( String name, float lo, float hi, float defaultValue )
    {
        super( name, Float.class, defaultValue );
        this.lo = lo;
        this.hi = hi;
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
