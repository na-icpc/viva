package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class JavaIntParameter extends StringListParameter
{
    public JavaIntParameter()
    {
        super( "javaint", Parameter.truefalse, 1 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.javaint = Parameter.isTrue( value );
    }
}
