package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class InListFunction.
 */
public class InListFunction implements ScalarFunction
{

    /**
     * Gets the name of the function.
     *
     * @return the name
     */
    @Override
    public String getName()
    {
        return "inlist";
    }

    /**
     * Gets the return type.
     *
     * @param params the params
     * @return the return type, or null if the params are wrong
     */
    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==2 ? Boolean.class : null;
    }

    /**
     * Gets the usage.
     *
     * @return the usage
     */
    @Override
    public String getUsage()
    {
        return "inlist(list_id,value)";
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
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        Object name = parameters.get( 0 );
        
        @SuppressWarnings( "unchecked" )
        HashSet<Object> set = (HashSet<Object>)context.getThing( HashSet.class, name );
        
        return new Boolean( set.contains( parameters.get( 1 ) ) );
    }

}
