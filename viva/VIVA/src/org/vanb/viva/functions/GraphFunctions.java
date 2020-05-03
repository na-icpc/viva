package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.graphs.Graph;
import org.vanb.viva.utils.Utilities;
import org.vanb.viva.utils.VIVAContext;

/**
 * All of the Graph Functions.
 */
public class GraphFunctions
{

    /**
     * Cannot instantiate one of these, it's just a holder.
     */
    private GraphFunctions() {}

    /**
     * Add an Edge to a Graph.
     */
    public static class AddEdge implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "addedge";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return ( params.length == 3 || params.length == 4 ) 
                    && params[0] == String.class 
                    && Utilities.isDiscrete( params[1] ) 
                    && Utilities.isDiscrete( params[2] ) 
                    && ( params.length == 3 || Number.class.isAssignableFrom( params[3] ) ) 
                    ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "addedge( graph_id, from_id, to_id [, weight] ) graph_id must be a string, from_id and to_id must be discrete (integer, long or string)";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            Always TRUE
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            String graph_id = (String)parameters.get( 0 );
            Object from_id = parameters.get( 1 );
            Object to_id = parameters.get( 2 );
            Number weight = parameters.size() == 4 ? (Number)parameters.get( 3 ) : null;

            Graph graph = (Graph)context.getThing( Graph.class, graph_id );
            graph.addEdge( from_id, to_id, weight );
            return Boolean.TRUE;
        }

    }

    /**
     * Add a Node to a Graph.
     */
    public static class AddNode implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "addnode";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 2 
                    && params[0] == String.class 
                    && Utilities.isDiscrete( params[1] ) 
                    ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "addnode( graph_id, node_id ) graph_id must be a string, node_id must be discrete (integer, long or string)";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            Always TRUE
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            String graph_id = (String)parameters.get( 0 );
            Object node_id = parameters.get( 1 );

            Graph graph = (Graph)context.getThing( Graph.class, graph_id );
            graph.addNode( node_id );
            return Boolean.TRUE;
        }

    }

    /**
     * Add multiple sequential Nodes to a Graph.
     */
    public static class AddNodes implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "addnodes";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 3 
                    && params[0] == String.class 
                    && params[1] == Integer.class 
                    && params[2] == Integer.class 
                    ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "addnode( graph_id, start_id, end_id ) graph_id must be a string, start_id and end_id must be integers";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            Always TRUE
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            String graph_id = (String)parameters.get( 0 );
            int start_id = (Integer)parameters.get( 1 );
            int end_id = (Integer)parameters.get( 2 );

            if( end_id < start_id ) throw new Exception( "Ending node must be >= starting node" );
            if( end_id - start_id + 1 > 1000000 ) throw new Exception( "Cannot create a graph with more than a million nodes" );

            Graph graph = (Graph)context.getThing( Graph.class, graph_id );
            for( int node_id = start_id; node_id <= end_id; ++node_id ) graph.addNode( node_id );
            return Boolean.TRUE;
        }

    }

    /**
     * Create a Graph.
     */
    public static class CreateGraph implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "graph";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            Class<?> retval = Boolean.class;

            if( !Utilities.isDiscrete( params[0] ) ) retval = null;
            else for( int i = 1; i < params.length; i++ )
            {
                if( params[i] != String.class ) retval = null;
            }

            return retval;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "graph([Integer | Long | String] [,parameter]*) Possible parameters are \"[un]directed\", \"[un]weighted\", \"[no]multi\", \"[no]self\", \"[no]auto\"";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            boolean directed = false;
            boolean weighted = false;
            boolean multi = false;
            boolean self = false;
            boolean auto = false;

            HashSet<String> properties = new HashSet<String>();

            for( int i = 1; i < parameters.size(); i++ )
            {
                String parameter = parameters.get( i ).toString().toLowerCase();
                if( parameter.equals( "directed" ) )
                {
                    directed = true;
                    if( properties.contains( "undirected" ) ) throw new Exception( "Graph cannot be both directed and undirected." );
                }
                else if( parameter.equals( "undirected" ) )
                {
                    directed = false;
                    if( properties.contains( "directed" ) ) throw new Exception( "Graph cannot be both directed and undirected." );
                }
                else if( parameter.equals( "weighted" ) )
                {
                    weighted = true;
                    if( properties.contains( "unweighted" ) ) throw new Exception( "Graph cannot be both weighted and unweighted." );
                }
                else if( parameter.equals( "unweighted" ) )
                {
                    weighted = false;
                    if( properties.contains( "weighted" ) ) throw new Exception( "Graph cannot be both weighted and unweighted." );
                }
                else if( parameter.equals( "multi" ) )
                {
                    multi = true;
                    if( properties.contains( "nomulti" ) ) throw new Exception( "Graph cannot be both multi and nomulti." );
                }
                else if( parameter.equals( "nomulti" ) )
                {
                    multi = false;
                    if( properties.contains( "multi" ) ) throw new Exception( "Graph cannot be both multi and nomulti." );
                }
                else if( parameter.equals( "self" ) )
                {
                    self = true;
                    if( properties.contains( "noself" ) ) throw new Exception( "Graph cannot be both self and noself." );
                }
                else if( parameter.equals( "noself" ) )
                {
                    self = false;
                    if( properties.contains( "self" ) ) throw new Exception( "Graph cannot be both self and noself." );
                }
                else if( parameter.equals( "auto" ) )
                {
                    auto = true;
                    if( properties.contains( "noauto" ) ) throw new Exception( "Graph cannot be both auto and noauto." );
                }
                else if( parameter.equals( "noauto" ) )
                {
                    auto = false;
                    if( properties.contains( "auto" ) ) throw new Exception( "Graph cannot be both auto and noauto." );
                }
                else throw new Exception( "Unrecognized parameter to graph(): " + parameter );

                properties.add( parameter );
            }

            String name = parameters.get( 0 ).toString();
            context.setThing( name, new Graph( name, directed, weighted, multi, self, auto ) );

            return Boolean.TRUE;
        }

    }

    /**
     * A template for all Graph test functions.
     */
    private static abstract class GraphTestFunction implements ScalarFunction
    {

        /** The name. */
        private String name;

        /** The return type. */
        private Class<?> type;

        /**
         * Instantiates a new graph test function.
         *
         * @param name the name
         */
        protected GraphTestFunction(String name)
        {
            this( name, Boolean.class );
        }

        /**
         * Instantiates a new graph test function.
         *
         * @param name the name
         * @param type the return type
         */
        protected GraphTestFunction(String name, Class<?> type)
        {
            this.name = name;
            this.type = type;
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return name;
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 1 
                    && params[0] == String.class 
                    ? type : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return name + "( graph_id ) graph_id must be a string";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            Graph graph = (Graph)context.getThing( Graph.class, parameters.get( 0 ) );
            return test( graph );
        }

        /**
         * Test.
         *
         * @param  graph the graph
         * @return       the boolean
         */
        public abstract Object test( Graph graph );
    }

    /**
     * Is this Graph a Cactus?.
     */
    public static class IsCactus extends GraphTestFunction
    {

        /**
         * Instantiates a new checks if is cactus function.
         */
        public IsCactus()
        {
            super( "iscactus" );
        }

        /**
         * Test if the graph is a Cactus.
         *
         * @param  graph the graph
         * @return       the boolean
         */
        @Override
        public Object test( Graph graph )
        {
            return graph.isCactus();
        }

    }

    /**
     * Test if the graph is connected.
     */
    public static class IsConnected extends GraphTestFunction
    {

        /**
         * Instantiates a new checks if is connected function.
         */
        public IsConnected()
        {
            super( "isconnected" );
        }

        /**
         * Test if the graph is connected.
         *
         * @param  graph the graph
         * @return       the boolean
         */
        @Override
        public Object test( Graph graph )
        {
            return graph.isConnected();
        }

    }

    /**
     * Test if the graph is a Directed Acyclic Graph (DAG).
     */
    public static class IsDAG extends GraphTestFunction
    {

        /**
         * Instantiates a new checks if is DAG function.
         */
        public IsDAG()
        {
            super( "isdag" );
        }

        /**
         * Test if the graph is a Directed Acyclic Graph (DAG).
         *
         * @param  graph the graph
         * @return       the boolean
         */
        @Override
        public Object test( Graph graph )
        {
            return graph.isDAG();
        }

    }

    /**
     * Checks if this Graph is a Desert.
     */
    public static class IsDesert extends GraphTestFunction
    {

        /**
         * Instantiates a new checks if is desert function.
         */
        public IsDesert()
        {
            super( "isdesert" );
        }

        /**
         * Test if the graph is a Desert. A desert is a collection of disjoint cactus graphs.
         *
         * @param  graph the graph
         * @return       the boolean
         */
        @Override
        public Object test( Graph graph )
        {
            return graph.isDesert();
        }

    }

    /**
     * Test if this Graph is a Forest.
     */
    public static class IsForest extends GraphTestFunction
    {

        /**
         * Instantiates a new checks if is a forest function.
         */
        public IsForest()
        {
            super( "isforest" );
        }

        /**
         * Test if the graph is a forest.
         *
         * @param  graph the graph
         * @return       the boolean
         */
        @Override
        public Object test( Graph graph )
        {
            return graph.isForest();
        }

    }

    /**
     * Test if the graph is a Tree.
     */
    public static class IsTree extends GraphTestFunction
    {

        /**
         * Instantiates a new checks if is tree function.
         */
        public IsTree()
        {
            super( "istree" );
        }

        /**
         * Test if the graph is a Tree.
         *
         * @param  graph the graph
         * @return       the boolean
         */
        @Override
        public Object test( Graph graph )
        {
            return graph.isTree();
        }

    }

    /**
     * Count the number of components in this Graph.
     */
    public static class Components extends GraphTestFunction
    {

        /**
         * Count the number of components in this Graph.
         *
         * @param name the name
         */
        public Components()
        {
            super( "components", Integer.class );
        }

        /**
         * Count the number of components in this Graph.
         *
         * @param  graph the graph
         * @return       the number of components
         */
        @Override
        public Object test( Graph graph )
        {
            return graph.components();
        }
    }

}
