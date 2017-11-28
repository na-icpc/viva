package org.vanb.viva.parameters;

public abstract class IntegerRangeParameter extends Parameter
{
    protected int lo, hi;
    
    protected IntegerRangeParameter( String name, int lo, int hi, int defaultValue )
    {
        super( name, Integer.class, defaultValue );
        this.lo = lo;
        this.hi = hi;
    }
    
    @Override
    public boolean isvalid( Object value )
    {
        return value.getClass()==Integer.class && lo <= (Integer)value && (Integer)value <= hi;
    }
    
    public String usage()
    {
        return "Must be an integer between " + lo + " and " + hi;
    }
   
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
