package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class IgnoreEOLNParameter extends StringListParameter
{
    public IgnoreEOLNParameter()
    {
        super( "ignoreeoln", Parameter.truefalse, 1 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.ignoreEOLN = Parameter.isTrue( value );
    }
}
