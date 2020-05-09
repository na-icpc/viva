/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class JavaLongParameter.
 */
public class JavaLongParameter extends StringListParameter
{
    
    /**
     * Instantiates a new Java long parameter.
     */
    public JavaLongParameter()
    {
        super( "javalong", Parameter.truefalse, 1 );
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
        context.javalong = Parameter.isTrue( value );
    }
}
