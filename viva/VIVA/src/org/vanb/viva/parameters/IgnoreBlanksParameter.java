package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class IgnoreBlanksParameter extends StringListParameter
{
    public IgnoreBlanksParameter()
    {
        super( "ignoreblanks", Parameter.truefalse, 1 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.ignoreBlanks = Parameter.isTrue( value );
    }
}
