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
 * The Class DepsParameter.
 */
public class DepsParameter extends DoubleRangeParameter
{
    
    /**
     * Instantiates a new Double Epsilon parameter.
     */
    public DepsParameter()
    {
        super( "deps", 0.0, Double.MAX_VALUE, VIVA.DEPS );
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
        context.deps = (Double)value;
    }
}
