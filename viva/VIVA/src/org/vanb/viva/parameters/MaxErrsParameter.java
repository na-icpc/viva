package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class MaxErrsParameter.
 */
public class MaxErrsParameter extends IntegerRangeParameter
{
    
    /**
     * Instantiates a new maximum number of errors parameter.
     */
    public MaxErrsParameter()
    {
        super( "maxerrs", 0, Integer.MAX_VALUE, 25 );
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
        context.maxerrs = (Integer)value;
    }
}
