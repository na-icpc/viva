package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.graphs.Graph;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class GraphTestFunction.
 */
public abstract class GraphTestFunction implements ScalarFunction
{
    
    /** The name. */
    private String name;
        
    /**
     * Instantiates a new graph test function.
     *
     * @param name the name
     */
    protected GraphTestFunction( String name )
    {
        this.name = name;
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
     * @param params the params
     * @return the return type
     */
    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && params[0]==String.class ? Boolean.class : null;
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
     * @param context the context
     * @param parameters the parameters
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object run( VIVAContext context, List<Object> parameters )throws Exception
    {
        Graph graph = (Graph)context.getThing( Graph.class, parameters.get( 0 ) );
        return test( graph );
    }
    
    /**
     * Test.
     *
     * @param graph the graph
     * @return the boolean
     */
    public abstract Boolean test( Graph graph );
}
