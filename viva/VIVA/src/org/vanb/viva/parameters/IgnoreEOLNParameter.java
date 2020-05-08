package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class IgnoreEOLNParameter.
 */
public class IgnoreEOLNParameter extends StringListParameter
{
    
    /**
     * Instantiates a new ignore EOLN parameter.
     */
    public IgnoreEOLNParameter()
    {
        super( "ignoreeoln", Parameter.truefalse, 1 );
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
        context.ignoreEOLN = Parameter.isTrue( value );
    }
}
