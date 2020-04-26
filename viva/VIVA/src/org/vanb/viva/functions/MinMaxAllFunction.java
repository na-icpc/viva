package org.vanb.viva.functions;

import java.util.Comparator;
import java.util.List;

import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * The superclass to handle minall and maxall functions.
 */
public abstract class MinMaxAllFunction implements VectorFunction
{
    /** The comparator. */
    protected Comparator<Object> comparator;
    
    /**
     * Gets the return type.
     *
     * @param params the params
     * @return the return type
     */
    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        Class<?> result = null;
        if( params.length>0 )
        {
            result = params[0];
            for( int i=1; i<params.length; i++ )
            {
                if( !params[i].equals( result ) )
                {
                    result = null;
                    break;
                }
            }
        }
        
        if( result.equals( Boolean.class ) ) result = null;
        return result;
    }

    /**
     * Gets the usage.
     *
     * @return the usage
     */
    @Override
    public String getUsage()
    {
        return getName() + "(args) Must be at least one argument. Arguments can be of any type except boolean, but they must all be of the same type.";
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
    public Object run( VIVAContext context, List<List<Object>> parameters )
            throws Exception
    {
        Object best = null;
        for( List<Object> list : parameters ) for( Object parm : list )
        {
            if( comparator.compare( best, parm )<0 ) best = parm;
        }
        return best;
    }

}
