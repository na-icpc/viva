/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class JavaDoubleParameter.
 */
public class JavaDoubleParameter extends StringListParameter
{
    
    /**
     * Instantiates a new Java double parameter.
     */
    public JavaDoubleParameter()
    {
        super( "javadouble", Parameter.truefalse, 1 );
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
        context.javadouble = Parameter.isTrue( value );
    }
}
