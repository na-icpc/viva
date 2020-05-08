package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class JavaFloatParameter.
 */
public class JavaFloatParameter extends StringListParameter
{
    
    /**
     * Instantiates a new java float parameter.
     */
    public JavaFloatParameter()
    {
        super( "javafloat", Parameter.truefalse, 1 );
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
        context.javafloat = Parameter.isTrue( value );
    }
}
