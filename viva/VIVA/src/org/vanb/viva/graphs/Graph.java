/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class Graph.
 *
 * @author vanb
 */
public class Graph extends Base
{
    
    /**  Is this graph directed?. */
    private boolean directed;
    
    /**  Is this graph weighted?. */
    private boolean weighted;
    
    /**  Does this graph allow multiple edges between the same nodes?. */
    private boolean allowMulti;
    
    /**  Does this graph allow edges from a node to itself?. */
    private boolean allowSelf;
    
    /** Do we automatically add nodes from addEdge that we haven't seen yet?. */
    private boolean autoAdd;
    
    /** The nodes. */
    private HashMap<Object,Node> nodes = new HashMap<Object,Node>();
    
    /** The edges. */
    private HashMap<Object,Edge> edges = new HashMap<Object,Edge>();
    
    /** This will help us with Kruskall-like algorithm we use for isConnected(). */
    private int nComponents=0;
    
    /** A set of all of the Nodes with no in-edges. */
    private HashSet<Node> zeroins = new HashSet<Node>();
     
    /**
     * Instantiates a new graph.
     *
     * @param id the id
     * @param directed the directed
     * @param weighted the weighted
     * @param allowMulti the allow multi
     * @param allowSelf the allow self
     * @param autoAdd the auto add
     */
    public Graph( String id, boolean directed, boolean weighted, boolean allowMulti, boolean allowSelf, boolean autoAdd )
    {
        super( id );
        //System.err.println( "Creating graph " + id );
        this.directed = directed;
        this.weighted = weighted;
        this.allowMulti = allowMulti;
        this.allowSelf = allowSelf;
        this.autoAdd = autoAdd;
    }
    
    /**
     * Creates and adds a node if it doesn't already exist.
     *
     * @param id the id
     * @return the node
     */
    public Node addNode( Object id )
    {
        //System.err.println( "addnode: " + id );

        String key = id.toString();
        Node node = nodes.get( key );
        if( node==null )
        {
            node = new Node( key );
            nodes.put( key, node );
            zeroins.add( node );
            ++nComponents;
        }
        //System.err.println( "Components is now " + nComponents );
        
        return node;
    }
        
    /**
     * Gets the root of a tree representing the set for a Kruskall-like algorithm.
     *
     * @param node the node
     * @return the root
     */
    private Node getRoot( Node node )
    {
        Node root = node;
        for( ; !root.getExtras(root).equals(root); root = (Node)root.getExtras() );
        for( ; !node.getExtras().equals(node); node=(Node)node.getExtras() ) node.setExtras(root);
        //System.err.println( "node is " + node.getID() + ", root is " + root.getID() );
        return root;
    }
    
    /**
     * Adds an edge.
     *
     * @param from the from node ID
     * @param to the to node ID
     * @param weight the weight
     * @throws Exception the exception
     */
    public void addEdge( Object from, Object to, Number weight ) throws Exception
    {
        //System.err.println( "addedge: " + from + " " + to + " " + weight );
        // Do a bunch of error checking, which should be pretty obvious.
        
        // Do we want weighted edges?
        if( !weighted && weight!=null )     
        {
            throw new Exception( "Cannot put a weighted edge on unweighted graph '" + getID() + "'" );
        }
        else if( weighted && weight==null )     
        {
            throw new Exception( "Cannot put an unweighted edge on weighted graph '" + getID() + "'" );
        }

        // We'll identify nodes by String. Only discrete types (int, long, String) can be Node IDs.
        String fromstr = from.toString();
        String tostr = to.toString();
        
        // Is this a self-edge?
        if( !allowSelf && fromstr.equals( tostr ) )
        {
            throw new Exception( "Cannot add a self-edge (" + fromstr + ") to graph '" + getID() + "'" );
        }
        
        // The key is a unique identifier for the Edge. 
        // For an undirected edge where A->B and B->A are the same, we'll sort the Node names.
        String key = null;
        if( directed || fromstr.compareTo(tostr)>0 ) key = fromstr + ":" + tostr;
        else key = tostr + ":" + fromstr;  
        
        // Is this a multi-edge?
        if( !allowMulti && edges.keySet().contains( key ) ) 
        {
            throw new Exception( "Cannot add duplicate edge (" + key + ") to graph '" + getID() + "'" );
        }
        
        // Does the from-node exist?
        Node fromnode = nodes.get( fromstr );
        if( fromnode==null )
        {
            if( autoAdd ) fromnode = this.addNode( from );
            else throw new Exception( "Cannot find from-node " + fromnode + " in graph '" + getID() + "'" );            
        }
        
        // Does the to-node exist?
        Node tonode = nodes.get( tostr );
        if( tonode==null )
        {
            if( autoAdd ) tonode = this.addNode( to );
            else throw new Exception( "Cannot find to-node " + tonode + " in graph '" + getID() + "'" );            
        }

        // Create the edge, put it everywhere it needs to go.
        Edge edge = new Edge( key, fromnode, tonode, weight );
        fromnode.addEdge( edge );
        if( !directed ) tonode.addEdge( edge );
        edges.put( key, edge );
        
        // See if this edge merges two components.
        Node fromroot = getRoot(fromnode);
        Node toroot = getRoot(tonode);
        if( !fromroot.equals( toroot ) )
        {
            // We've just connected two separate components. So now there's one less component.
            --nComponents;
            fromnode.setExtras( toroot );
        }
        
        // The to-node now has an in-edge. So does the from-node in an undirected graph.
        zeroins.remove( tonode );
        if( !directed ) zeroins.remove( fromnode );
        
        //System.err.println( "Components is now " + nComponents );

    }

    
    /**
     * Checks if this graph is connected.
     *
     * @return the boolean
     */
    public Boolean isConnected()
    {
        // The graph is Connected if it's all one big component.
        return nComponents == 1;
    }
        
    /**
     * Checks if this graph is a tree.
     *
     * @return the boolean
     */
    public Boolean isTree()
    {
        return edges.size()==nodes.size()-1 && isConnected()  && (!directed || zeroins.size()==1);
    }
    
    /**
     * Checks if the graph is a Forest.
     * A Forest is a set of disjoint graphs, each of which is a Tree.
     *
     * @return the boolean
     */
    public Boolean isForest() 
    {
        return edges.size()==nodes.size()-nComponents && (!directed || zeroins.size()==nComponents);
    }
    
    /**  Path from the start of a DFS to a given node. */
    private HashSet<Node> path = new HashSet<Node>();

    /**
     * Detect a cycle.
     *
     * @param node a node in the graph
     * @return true if we find a cycle, otherwise false
     */
    private Boolean detectCycle( Node node )
    {
        boolean isCycle = path.contains( node );
        
        if( !isCycle )
        {
            node.setExtras( "visited" );
            path.add( node );
            for( Node neighbor : node.getNeighbors() ) if( node.getExtras()==null )
            {
                if( detectCycle( neighbor ) )
                {
                    isCycle = true;
                    break;
                }
            }
            path.remove( node );
        }
        
        return isCycle;
    }
    
    /**
     * Checks if graph is a DAG (Directed Acyclic Graph).
     * A DAG is directed, connected, and has no cycles.
     *
     * @return true if the graph is a DAG, otherwise false
     */
    public Boolean isDAG()
    {
        Boolean isdag = false;
        if( directed && isConnected() && zeroins.size()>0 )
        {
            isdag = true;
            for( Node node : nodes.values() ) node.setExtras( null );
            path.clear();
            
            for( Node node : zeroins ) if( detectCycle( node ) )
            {
                isdag = false;
                break;
            }
        }
        
        return isdag;
    }
    
    /**
     * Checks if the graph is a Cactus.
     * A Cactus graph has no edges that are shared between two (or more) cycles.
     *
     * @return the boolean
     */
    public Boolean isCactus() 
    {
        return isConnected() && isDesert();
    }
    
    /**  A number used to differentiate between start points when detecting if a graph is a Desert. */
    private int index;
       
    /**
     * Detect a Desert.
     *
     * @param node a node in the graph
     * @param from the node we came from
     * @return true if we find a cycle, otherwise false
     */
    private Boolean detectDesert( Node node, Node from )
    {
        boolean isDesert = true;
        System.err.println( "Node " + node.getID() );
        
        if( path.contains( node ) )
        {
            Node next = node;
            do
            {
                Edge edge = (Edge)next.getExtras();
                int edgeindex = (int)edge.getExtras();
                System.err.println( "testing " + edge.getID() + " index is " + edgeindex);

                if( edgeindex==index ) 
                {
                    isDesert = false;
                    break;
                }
                else if( edgeindex==0 ) edge.setExtras( index );
                
                next = edge.getOther( next );
            }
            while( !next.equals( node ) );
        }
        else if( node.getExtras()==null )     
        {
            node.setExtras( "visited" );
            path.add( node );
            for( Edge edge : node.getEdges() ) 
            {
                Node next = edge.getOther( node );
                if( !next.equals( from ))
                {
                    node.setExtras( edge );
                    if( !detectDesert( next, node ) )
                    {
                        isDesert = false;
                        break;
                    }
                }
            }
            path.remove( node );
        }
        
        return isDesert;
    }
    
    /**
     * Checks if the graph is a Desert.
     * A Desert is a set of disjoint graphs, each of which is a Cactus.
     *
     * @return the boolean
     */
    public Boolean isDesert()
    {
        Boolean isdesert = true;

        for( Node node : nodes.values() ) node.setExtras( null );
        for( Edge edge : edges.values() ) edge.setExtras( 0 );
        index = 0;
        path.clear();
        
        for( Node node : nodes.values() ) if( node.getExtras()==null )
        {
            ++index;
            if( !detectDesert( node, null ) )
            {
                isdesert = false;
                break;
            }
        }

        return isdesert;
    }
    
    /**
     * Get the number of Components.
     *
     * @return the number of Component
     */
    public Integer components()
    {
        return nComponents;
    }
    
    /**
     * Find a connected component.
     *
     * @param node A starting Node
     * @param componentNodes the component's nodes, built as we go
     * @param componentEdges the component's edges, built as we go
     */
    private void findComponent( Node node, Set<Node> componentNodes, Set<Edge> componentEdges )
    {
        componentNodes.add( node );
        node.setExtras( "visited" );
        for( Edge edge : node.getEdges() )
        {
            componentEdges.add( edge );
            if( edge.getWeight().doubleValue()<0.0 ) edge.setExtras( node );
            Node neighbor = edge.getOther( node );
            if( neighbor.getExtras()==null ) findComponent( neighbor, componentNodes, componentEdges );
        }
    }
    
    /**
     * Detect a negative cycle in a connected component.
     *
     * @param start a starting node
     * @param componentNodes the component's nodes
     * @param componentEdges the component's edges
     * @return true, if there is a negative cycle in this component
     */
    private boolean detectNegativeCycle( Node start, Set<Node> componentNodes, Set<Edge> componentEdges )
    {
        // Use Bellman/Ford to find all shortest paths from the start node to every other node
        for( Node node : componentNodes ) node.setExtras( Double.POSITIVE_INFINITY );    
        start.setExtras( 0.0 );
               
        for( int i=0; i<componentNodes.size()-1; i++ )
        {
            for( Edge edge : componentEdges )
            {
                Node fromNode = edge.getFrom();
                Node toNode = edge.getTo();
                double u = ((Number)fromNode.getExtras()).doubleValue();
                double v = ((Number)toNode.getExtras()).doubleValue();
                double w = edge.getWeight().doubleValue();
                if( u+w<v ) toNode.setExtras( u+w );
             }
        }
        
        // Take B/F one step further.
        // If we can find a shorter path, then there has to be a negative cycle.
        boolean isneg = false;
        for( Edge edge : componentEdges )
        {
            double u = ((Number)edge.getFrom().getExtras()).doubleValue();
            double v = ((Number)edge.getTo().getExtras()).doubleValue();
            double w = edge.getWeight().doubleValue();
            if( u+w<v ) 
            {
                isneg = true;
                break;
            }
        }
        
        return isneg;
    }
    
    /**
     * Check if there are no negative cycles.
     *
     * @return true, if there are no negative cycles in the graph
     * @throws Exception the exception
     */
    public Boolean noNegativeCycles() throws Exception
    {
        Boolean noneg = Boolean.TRUE;
        
        if( weighted )
        {
            if( nodes.size()*edges.size()>1000000 )
            {
                throw new Exception( "Graph is too big to test for negative cycles. |V|*|E|>1,000,000 (" 
                                   + nodes.size() + " * " + edges.size() + " = " + (nodes.size()*edges.size()) + ")" );    
            }
            
            if( directed )
            {
                // For a Directed graph, we'll use Bellman/Fors, which is O(|V|*|E|)                
                List<Node> componentNodes = new ArrayList<Node>(nodes.size()+1);
                List<Edge> componentEdges = new ArrayList<Edge>(edges.size()+nodes.size());

                // Grab all of the ndodes and edges
                componentNodes.addAll( nodes.values() );
                componentEdges.addAll( edges.values() );
                
                // We'll create an artificial start node, 
                // with a directed edge of weight 0 to every actual node.
                Node start = new Node( "start" );
                componentNodes.add( start );
                for( Node node : nodes.values() )
                {
                    Edge edge = new Edge( "start->" + node.getID(), start, node, 0.0 );
                    componentEdges.add( edge );
                }
                
                // This will be the cost from the start node to each other nodes
                for( Node node : componentNodes ) node.setExtras( Double.POSITIVE_INFINITY );    
                start.setExtras( 0.0 );
                       
                // B/F!
                for( int i=0; i<componentNodes.size()-1; i++ )
                {
                    for( Edge edge : componentEdges )
                    {
                        Node fromNode = edge.getFrom();
                        Node toNode = edge.getTo();
                        double u = ((Number)fromNode.getExtras()).doubleValue();
                        double v = ((Number)toNode.getExtras()).doubleValue();
                        double w = edge.getWeight().doubleValue();
                        if( u+w<v ) toNode.setExtras( u+w );
                     }
                }
                
                // Take B/F one step further.
                // If we can find a shorter path, then there has to be a negative cycle.
                for( Edge edge : componentEdges )
                {
                    double u = ((Number)edge.getFrom().getExtras()).doubleValue();
                    double v = ((Number)edge.getTo().getExtras()).doubleValue();
                    double w = edge.getWeight().doubleValue();
                    if( u+w<v ) 
                    {
                        noneg = Boolean.FALSE;
                        break;
                    }
                }

            }
            else
            {
                throw new Exception( "Cannot detect negative cycles in an undirected graph; the problem is NP-Complete." );    
            }
        }
        
        return noneg;
    }
}
