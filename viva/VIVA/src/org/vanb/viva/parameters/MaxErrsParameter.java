package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class MaxErrsParameter extends IntegerRangeParameter
{
    public MaxErrsParameter()
    {
        super( "maxerrs", 0, Integer.MAX_VALUE, 25 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.maxerrs = (Integer)value;
    }
}
