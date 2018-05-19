package org.vanb.viva.functions;

import java.util.Comparator;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public abstract class MinMaxFunction implements ScalarFunction
{
    protected Comparator<Object> comparator;
    
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
     

    @Override
    public String getUsage()
    {
        return getName() + "(args) Must be at least one argument. Arguments can be of any type except boolean, but they must all be of the same type.";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        Object best = null;
        for( Object parm : parameters )
        {
            if( comparator.compare( best, parm )<0 ) best = parm;
        }
        return best;
    }

}
