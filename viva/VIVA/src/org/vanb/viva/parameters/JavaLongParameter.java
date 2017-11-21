package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class JavaLongParameter extends StringListParameter
{
    public JavaLongParameter()
    {
        super( "javalong", Parameter.truefalse, 1 );
    }

    @Override
    public void action( VIVAContext context, Object value )
    {
        context.javalong = Parameter.isTrue( value );
    }
}
