package org.vanb.viva.functions;

import org.vanb.viva.graphs.Graph;


/**
 * The Class IsDesertFunction.
 */
public class IsDesertFunction extends  GraphTestFunction
{
    
    /**
     * Instantiates a new checks if is desert function.
     */
    public IsDesertFunction()
    {
        super( "istree" );
    }

    /**
     * Test if the graph is a Desert.
     * A desert is a collection of disjoint cactus graphs.
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
