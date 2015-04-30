package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class DepsParameter extends DoubleRangeParameter
{
    public DepsParameter()
    {
        super( "deps", 0.0, Double.MAX_VALUE, 0.000001 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.deps = (Double)value;
    }
}
