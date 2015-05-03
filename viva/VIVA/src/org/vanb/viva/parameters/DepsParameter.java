package org.vanb.viva.parameters;

import org.vanb.viva.VIVA;
import org.vanb.viva.utils.VIVAContext;

public class DepsParameter extends DoubleRangeParameter
{
    public DepsParameter()
    {
        super( "deps", 0.0, Double.MAX_VALUE, VIVA.DEPS );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.deps = (Double)value;
    }
}
