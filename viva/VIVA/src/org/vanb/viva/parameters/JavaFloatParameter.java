package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class JavaFloatParameter extends StringListParameter
{
    public JavaFloatParameter()
    {
        super( "javafloat", Parameter.truefalse, 1 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.javafloat = Parameter.isTrue( value );
    }
}
