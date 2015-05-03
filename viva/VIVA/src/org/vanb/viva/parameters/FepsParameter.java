package org.vanb.viva.parameters;

import org.vanb.viva.VIVA;
import org.vanb.viva.utils.VIVAContext;

public class FepsParameter extends FloatRangeParameter
{
    public FepsParameter()
    {
        super( "feps", 0.0F, Float.MAX_VALUE, VIVA.FEPS );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.feps = (Float)value;
    }

}
