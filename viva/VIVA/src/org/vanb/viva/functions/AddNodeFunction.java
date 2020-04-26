package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.graphs.Graph;
import org.vanb.viva.utils.Utilities;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class AddNodeFunction.
 */
public class AddNodeFunction implements ScalarFunction
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
     * @param params the params
     * @return the return type
     */
    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==2 && params[0]==String.class && Utilities.isDiscrete( params[1] ) ? Boolean.class : null ;
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
     * @param context the context
     * @param parameters the parameters
     * @return Always TRUE
     * @throws Exception the exception
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
