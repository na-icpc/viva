package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.graphs.Graph;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class AddNodeFunction.
 */
public class AddNodesFunction implements ScalarFunction
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
     * @param params the params
     * @return the return type
     */
    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==3 && params[0]==String.class 
                && params[1]==Integer.class 
                && params[2]==Integer.class  ? Boolean.class : null ;
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
     * @param context the context
     * @param parameters the parameters
     * @return Always TRUE
     * @throws Exception the exception
     */
    @Override
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        String graph_id = (String)parameters.get( 0 );
        int start_id = (Integer)parameters.get( 1 );
        int end_id = (Integer)parameters.get( 2 );
        if( end_id<start_id ) throw new Exception( "Ending node must be >= starting node");
        if( end_id-start_id+1>1000000 ) throw new Exception( "Cannot create a graph with more than a million nodes");
        Graph graph = (Graph)context.getThing( Graph.class, graph_id );
        for( int node_id=start_id; node_id<=end_id; ++node_id ) graph.addNode( node_id );
        return Boolean.TRUE;
    }

}
