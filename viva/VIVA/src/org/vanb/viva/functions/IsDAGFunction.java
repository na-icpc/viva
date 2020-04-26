package org.vanb.viva.functions;

import org.vanb.viva.graphs.Graph;


/**
 * The Class IsDAGFunction.
 */
public class IsDAGFunction extends  GraphTestFunction
{
    
    /**
     * Instantiates a new checks if is DAG function.
     */
    public IsDAGFunction()
    {
        super( "istree" );
    }

    /**
     * Test if the graph is a Directed Acyclic Graph (DAG).
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
