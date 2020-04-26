package org.vanb.viva.functions;

import org.vanb.viva.graphs.Graph;

/**
 * The Class IsForestFunction.
 */
public class IsForestFunction extends  GraphTestFunction
{
    
    /**
     * Instantiates a new checks if is a forest function.
     */
    public IsForestFunction()
    {
        super( "isforest" );
    }

    /**
     * Test if the graph is a forest.
     *
     * @param graph the graph
     * @return the boolean
     */
    @Override
    public Boolean test( Graph graph )
    {
        return graph.isForest();
    }

}
