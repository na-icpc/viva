package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class IgnoreBlanksParameter.
 */
public class IgnoreBlanksParameter extends StringListParameter
{
    
    /**
     * Instantiates a new ignore blanks parameter.
     */
    public IgnoreBlanksParameter()
    {
        super( "ignoreblanks", Parameter.truefalse, 1 );
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
        context.ignoreBlanks = Parameter.isTrue( value );
    }
}
