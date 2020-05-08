package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class JavaIntParameter.
 */
public class JavaIntParameter extends StringListParameter
{
    
    /**
     * Instantiates a new Java int parameter.
     */
    public JavaIntParameter()
    {
        super( "javaint", Parameter.truefalse, 1 );
    }

    /**
     * Action.
     *
     * @param context the context
     * @param value the value
     */
    @Override
    public void action( VIVAContext context, Object value )
    {
        context.javaint = Parameter.isTrue( value );
    }
}
