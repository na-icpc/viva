/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * A Node of a Graph.
 */
public class Node extends Base
{
    
    /** The edges. */
    private List<Edge> edges = new ArrayList<Edge>(1000000);

    /**
     * Instantiates a new node.
     *
     * @param id the id
     */
    public Node( String id )
    {
        super( id );
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public String getID()
    {
        return id;
    }

    /**
     * Gets the edges.
     *
     * @return the edges
     */
    public List<Edge> getEdges()
    {
        return edges;
    }
    
    /**
     * Gets the neighbors of thes node.
     *
     * @return the neighbors
     */
    public List<Node> getNeighbors()
    {
        List<Node> neighbors = new ArrayList<Node>(edges.size());
        for( Edge edge : edges ) neighbors.add( edge.getOther( this ) );
        return neighbors;
    }
    
    /**
     * Adds an edge.
     *
     * @param edge the edge
     */
    public void addEdge( Edge edge )
    {
        edges.add( edge );   
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return id + "(" + extras + ")";
    }
}
