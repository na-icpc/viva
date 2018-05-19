package org.vanb.viva.functions;

public class MaxAllFunction extends MinMaxAllFunction
{
    public MaxAllFunction()
    {
        comparator = new MaxFunction.MaxComparator();
    }
    
    @Override
    public String getName()
    {
        return "maxall";
    }

}
