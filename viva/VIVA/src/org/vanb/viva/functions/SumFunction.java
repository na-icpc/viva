package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ArithmeticFunction;
import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

public class SumFunction implements VectorFunction
{

    @Override
    public String getName()
    {
        return "sum";
    }

    @Override
    public Class<?> getReturnType( Class<?>[] params )
    {
        return params.length==1 && Number.class.isAssignableFrom( params[0] ) ? params[0] : null;
    }

    @Override
    public String getUsage()
    {
        return "sum( int or long or double or float )";
    }

    @Override
    public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
    {
        int intsum = 0;
        long longsum = 0L;
        double doublesum = 0D;
        float floatsum = 0F;
        
        Class<?> type = null;
        if( !parameters.isEmpty() ) type = parameters.get(0).get(0).getClass();
        for( List<Object> row : parameters )
        {
            Number addend = (Number)row.get( 0 );
            
            if( type==Integer.class ) intsum += addend.intValue(); 
            else if( type==Long.class ) longsum += addend.longValue(); 
            else if( type==Double.class ) doublesum += addend.doubleValue(); 
            else if( type==Float.class ) floatsum += addend.floatValue();                        
        }
        
        ArithmeticFunction.nanCheck( doublesum, "sum()" );
        ArithmeticFunction.nanCheck( floatsum, "sum()" );
                
        Object value = null;
        if( type==Integer.class ) value = new Integer(intsum); 
        else if( type==Long.class ) value = new Long(longsum); 
        else if( type==Double.class ) value = new Double(doublesum); 
        else if( type==Float.class ) value = new Float(floatsum);
        else value = new Integer(0);

        return value;
    }

}
