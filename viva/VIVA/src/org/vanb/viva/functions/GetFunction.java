package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * Function to set a global value.
 * This should never be run on its own, it should be extended by setint, setlong, etc.
 * 
 * @author vanb
 */
public class GetFunction implements ScalarFunction
{
    
    /** The name. */
    private String name;
    
    /** The type. */
    private Class<?> type;
       
    /**
     * Instantiates a new Set function.
     *
     * @param name the name
     * @param type the type
     */
    public GetFunction( String name, Class<?> type )
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
     * @param params the params
     * @return the return type
     */
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 ? type : null;
    }

    /**
     * Gets the usage.
     *
     * @return the usage
     */
    public String getUsage()
    {
        return name + "(varname)";
    }

    /**
     * Run.
     *
     * @param context the context
     * @param parameters the parameters
     * @return the object
     * @throws Exception the exception
     */
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {        
        return context.getThing( type, parameters.get( 0 ) );
    }

}
