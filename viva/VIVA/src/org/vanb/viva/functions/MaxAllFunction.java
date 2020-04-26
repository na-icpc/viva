package org.vanb.viva.functions;

/**
 * The Class MaxAllFunction.
 */
public class MaxAllFunction extends MinMaxAllFunction
{
    
    /**
     * Instantiates a new maxall function.
     */
    public MaxAllFunction()
    {
        comparator = new MaxFunction.MaxComparator();
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName()
    {
        return "maxall";
    }

}
