package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class AddToListFunction.
 */
public class AddToListFunction implements ScalarFunction
{

    /**
     * Gets the name of the function.
     *
     * @return the name
     */
    @Override
    public String getName()
    {
        return "addtolist";
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
        return "addtolist(list_id,value)";
    }

    /**
     * Run.
     *
     * @param context the context
     * @param parameters the parameters
     * @return the object
     * @throws Exception the exception
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {        
        HashSet<Object> set;
        Object id = parameters.get( 0 );
        try
        {
            set = (HashSet<Object>)context.getThing( HashSet.class, id );
        }
        catch( Exception e )
        {
            set = new HashSet<Object>();
            context.setThing( id, set );
        }
        set.add( parameters.get( 1 ) );
        
        return Boolean.TRUE;
    }

}
