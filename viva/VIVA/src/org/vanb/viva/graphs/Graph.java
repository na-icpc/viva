package org.vanb.viva.graphs;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The Class Graph.
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
        String key = id.toString();
        Node node = nodes.get( key );
        if( node==null )
        {
            node = new Node( key );
            nodes.put( key, node );
            zeroins.add( node );
            ++nComponents;
        }
        
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
        // Do a bunch of error checking, which should be pretty obvious.
        
        // Do we want weighted edges?
        if( !weighted && weight!=null )     
        {
            throw new Exception( "Cannot put a weighted edge on unweighted graph " + getID() );
        }
        else if( weighted && weight==null )     
        {
            throw new Exception( "Cannot put an unweighted edge on weighted graph " + getID() );
        }

        // We'll identify nodes by String. Only discrete types (int, long, String) can be Node IDs.
        String fromstr = from.toString();
        String tostr = to.toString();
        
        // Is this a self-edge?
        if( !allowSelf && fromstr.equals( tostr ) )
        {
            throw new Exception( "Cannot add a self-edge (" + fromstr + ") to graph " + getID() );
        }
        
        // The key is a unique identifier for the Edge. 
        // For an undirected edge where A->B and B->A are the same, we'll sort the Node names.
        String key = null;
        if( directed || fromstr.compareTo(tostr)>0 ) key = fromstr + ":" + tostr;
        else key = tostr + ":" + fromstr;  
        
        // Is this a multi-edge?
        if( !allowMulti && edges.keySet().contains( key ) ) 
        {
            throw new Exception( "Cannot add duplicate edge (" + key + ") to graph " + getID() );
        }
        
        // Does the from-node exist?
        Node fromnode = nodes.get( fromstr );
        if( fromnode==null )
        {
            if( autoAdd ) fromnode = this.addNode( from );
            else throw new Exception( "Cannot find from-node " + fromnode + " in graph " + getID() );            
        }
        
        // Does the to-node exist?
        Node tonode = nodes.get( fromstr );
        if( tonode==null )
        {
            if( autoAdd ) tonode = this.addNode( to );
            else throw new Exception( "Cannot find to-node " + tonode + " in graph " + getID() );            
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
    
    /** Path from the start of a DFS to a given node */
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
    
    /** A number used to differentiate between start points when detecting if a graph is a Desert */
    private int index;
    
    /**
     * Detect a Desert.
     *
     * @param node a node in the graph
     * @return true if we find a cycle, otherwise false
     */
    private Boolean detectDesert( Node node )
    {
        boolean isDesert = true;
        
        if( path.contains( node ) )
        {
            Node next = node;
            do
            {
                Edge edge = (Edge)next.getExtras();
                int edgeindex = (int)edge.getExtras();
                if( edgeindex==index ) 
                {
                    isDesert = false;
                    break;
                }
                
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
                node.setExtras( edge );
                if( !detectDesert( edge.getOther( node ) ) )
                {
                    isDesert = false;
                    break;
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
            if( !detectDesert( node ) )
            {
                isdesert = false;
                break;
            }
        }

        return isdesert;
    }
}
