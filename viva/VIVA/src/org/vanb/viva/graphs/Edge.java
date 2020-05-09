/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.graphs;

/**
 * The Class Edge.
 */
public class Edge extends Base
{
    
    /** The From Node. */
    private Node from;
    
    /** The To Node. */
    private Node to;
    
    /** The Weight. */
    private Number weight = null;
    
    /**
     * Instantiates a new edge.
     *
     * @param id the id
     * @param from the from Node
     * @param to the to Node
     * @param weight the weight
     */
    public Edge( String id, Node from, Node to, Number weight )
    {
        super( id );
        this.from  = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * Gets the from Node.
     *
     * @return the from Node
     */
    public Node getFrom()
    {
        return from;
    }

    /**
     * Gets the to Node.
     *
     * @return the to Node
     */
    public Node getTo()
    {
        return to;
    }
    
    /**
     * Gets the other Node.
     *
     * @param node the Node
     * @return the other Node
     */
    public Node getOther( Node node )
    {
        return from.equals( node ) ? to : from;
    } 
 
    /**
     * Sets the weight.
     *
     * @param weight the new weight
     */
    public void setWeight( Number weight )
    {
        this.weight = weight;
    }

    /**
     * Gets the weight.
     *
     * @return the weight
     */
    public Number getWeight()
    {
        return weight;
    }

}
