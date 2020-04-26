package org.vanb.viva.functions;

/**
 * The Class MinAllFunction.
 */
public class MinAllFunction extends MinMaxAllFunction
{
    
    /**
     * Instantiates a new minall function.
     */
    public MinAllFunction()
    {
        comparator = new MinFunction.MinComparator();
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName()
    {
        return "minall";
    }

}
