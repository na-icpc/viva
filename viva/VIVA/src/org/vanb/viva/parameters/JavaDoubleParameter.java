package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class JavaDoubleParameter extends StringListParameter
{
    public JavaDoubleParameter()
    {
        super( "javadouble", Parameter.truefalse, 1 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.javadouble = Parameter.isTrue( value );
    }
}
