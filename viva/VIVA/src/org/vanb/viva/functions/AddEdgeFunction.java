package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.graphs.Graph;
import org.vanb.viva.utils.Utilities;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class AddNodeFunction.
 */
public class AddEdgeFunction implements ScalarFunction
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
     * @param params the params
     * @return the return type
     */
    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return (params.length==3 || params.length==4)
                && params[0]==String.class 
                && Utilities.isDiscrete( params[1] ) 
                && Utilities.isDiscrete( params[2] )
                && (params.length==3 || Number.class.isAssignableFrom( params[3] )) ? Boolean.class : null ;
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
     * @param context the context
     * @param parameters the parameters
     * @return Always TRUE
     * @throws Exception the exception
     */
    @Override
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        String graph_id = (String)parameters.get( 0 );
        Object from_id = parameters.get( 1 );
        Object to_id = parameters.get( 2 );
        Number weight = parameters.size()==4 ? (Number)parameters.get( 3 ) : null;
        Graph graph = (Graph)context.getThing( Graph.class, graph_id );
        graph.addEdge( from_id, to_id, weight );
        return Boolean.TRUE;
    }

}
