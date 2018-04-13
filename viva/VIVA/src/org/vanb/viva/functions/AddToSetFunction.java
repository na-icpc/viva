package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class AddToSetFunction implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "addtoset";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==2 ? Boolean.class : null;
    }

    @Override
    public String getUsage()
    {
        return "addtoset(set_id,value)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        String key = "set:" + parameters.get( 0 ).toString();
        
        HashSet<Object> set = (HashSet<Object>)context.globals.get( key );
        if( set==null )
        {
            set = new HashSet<Object>();
            context.globals.put( key, set );
        }
        set.add( parameters.get( 1 ) );
        
        return Boolean.TRUE;
    }

}
