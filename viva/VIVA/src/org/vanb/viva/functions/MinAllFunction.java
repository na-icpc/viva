package org.vanb.viva.functions;

public class MinAllFunction extends MinMaxAllFunction
{
    public MinAllFunction()
    {
        comparator = new MinFunction.MinComparator();
    }
    
    @Override
    public String getName()
    {
        return "minall";
    }

}
