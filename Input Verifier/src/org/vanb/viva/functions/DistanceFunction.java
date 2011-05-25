package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

public class DistanceFunction implements ScalarFunction
{

    @Override
    public String getName()
    {
        return "distance";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==4 
            && Number.class.isAssignableFrom( params[0] )
            && Number.class.isAssignableFrom( params[1] )
            && Number.class.isAssignableFrom( params[2] )
            && Number.class.isAssignableFrom( params[3] )
            ? Double.class : null;
    }

    @Override
    public String getUsage()
    {
        return "distance(x1,y1,x2,y2) [the four parameters can be any numeric type]";
    }

    @Override
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception
    {
        double x1 = ((Number)parameters.get( 0 )).doubleValue();
        double y1 = ((Number)parameters.get( 1 )).doubleValue();
        double x2 = ((Number)parameters.get( 2 )).doubleValue();
        double y2 = ((Number)parameters.get( 3 )).doubleValue();
        return Math.hypot( x2-x1, y2-y1 );
    }

}
