/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

import org.vanb.viva.VIVA;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class FepsParameter.
 */
public class FepsParameter extends FloatRangeParameter
{
    
    /**
     * Instantiates a new Float Epsilon parameter.
     */
    public FepsParameter()
    {
        super( "feps", 0.0F, Float.MAX_VALUE, VIVA.FEPS );
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
        context.feps = (Float)value;
    }

}
