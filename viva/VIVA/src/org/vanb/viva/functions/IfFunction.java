package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * Implementation of the If Function.
 */
public class IfFunction implements ScalarFunction
{
    
    /**
     * @see org.vanb.viva.Function#getName()
     */
    @Override
    public String getName()
    {
        return "if";
    }

    /**
     * @see org.vanb.viva.Function#getReturnType(java.lang.Class[])
     */
    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return (params.length==3 && params[0]==Boolean.class && params[1]==params[2]) ? params[1] : null;
    }

    /**
     * @see org.vanb.viva.Function#getUsage()
     */
    @Override
    public String getUsage()
    {
        return "if(boolean,arg1,arg2) arg1 and arg2 can be of any type, but they must be of the SAME type.";
    }

    /**
     * @see org.vanb.viva.ScalarFunction#run(org.vanb.viva.utils.VIVAContext, java.util.List)
     */
    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        return (Boolean)parameters.get( 0 ) ? parameters.get( 1 ) : parameters.get( 2 );
    }

}
