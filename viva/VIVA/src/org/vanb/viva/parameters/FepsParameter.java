package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class FepsParameter extends FloatRangeParameter
{
    public FepsParameter()
    {
        super( "feps", 0.0F, Float.MAX_VALUE, 0.000001F );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.feps = (Float)value;
    }

}
