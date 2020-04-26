package org.vanb.viva.functions;

import org.vanb.viva.graphs.Graph;


/**
 * The Class IsCactusFunction.
 */
public class IsCactusFunction extends  GraphTestFunction
{
    
    /**
     * Instantiates a new checks if is cactus function.
     */
    public IsCactusFunction()
    {
        super( "istree" );
    }

    /**
     * Test if the graph is a Cactus.
     *
     * @param graph the graph
     * @return the boolean
     */
    @Override
    public Boolean test( Graph graph )
    {
        return graph.isTree();
    }

}
