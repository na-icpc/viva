package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class InSetFunction implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "inset";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==2 ? Boolean.class : null;
    }

    @Override
    public String getUsage()
    {
        return "inset(set_id,value)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        String key = "set:" + parameters.get( 0 ).toString();
        
        HashSet<Object> set = (HashSet<Object>)context.globals.get( key );

        if( set==null ) throw new Exception( "No set named <" + parameters.get( 0 ).toString() + ">" );
        
        return new Boolean( set.contains( parameters.get( 1 ) ) );
    }

}
