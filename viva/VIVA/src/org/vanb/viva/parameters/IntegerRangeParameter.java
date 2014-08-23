package org.vanb.viva.parameters;

public class IntegerRangeParameter extends Parameter
{
    int lo, hi;
    
    public IntegerRangeParameter( int low, int high, int dv )
    {
        super( Integer.class, dv );
        lo = low;
        hi = high;
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

}
