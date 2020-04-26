package org.vanb.viva.functions;

import org.vanb.viva.graphs.Graph;


/**
 * The Class IsTreeFunction.
 */
public class IsTreeFunction extends  GraphTestFunction
{
    
    /**
     * Instantiates a new checks if is tree function.
     */
    public IsTreeFunction()
    {
        super( "istree" );
    }

    /**
     * Test if the graph is a Tree.
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
