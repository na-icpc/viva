package org.vanb.viva.functions;

import org.vanb.viva.graphs.Graph;

/**
 * The Class IsConnectedfunction.
 */
public class IsConnectedFunction extends  GraphTestFunction
{
    /**
     * Instantiates a new checks if is connected function.
     *
     * @param name the name
     */
    public IsConnectedFunction()
    {
        super( "isconnected" );
    }

    /**
     * Test if the graph is connected.
     *
     * @param graph the graph
     * @return the boolean
     */
    @Override
    public Boolean test( Graph graph )
    {
        return graph.isConnected();
    }

}
