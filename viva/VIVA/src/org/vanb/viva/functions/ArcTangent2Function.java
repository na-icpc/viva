package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

public class ArcTangent2Function implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "atan2";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==2
            && Number.class.isAssignableFrom( params[0] )
            && Number.class.isAssignableFrom( params[1] )
                ? Double.class : null;
    }

    @Override
    public String getUsage()
    {
        return "atan2(number,number)";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        double dy = ((Number)parameters.get(0)).doubleValue();
        double dx = ((Number)parameters.get(1)).doubleValue();
        return Math.atan2( dy, dx );
    }

}
