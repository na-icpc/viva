package org.vanb.viva.functions;

import java.util.Comparator;

public class MinFunction extends MinMaxFunction
{
    public static class MinComparator implements Comparator<Object>
    {

        @Override
        public int compare( Object a, Object b )
        {
            int diff = 0;
            if( a==null ) diff = -1;
            else if( b==null ) diff = 1;
            else if( a instanceof Integer ) diff = Integer.compare( (Integer)b, (Integer)a );
            else if( a instanceof Long ) diff = Long.compare( (Long)b, (Long)a );
            else if( a instanceof Float ) diff = Float.compare( (Float)b, (Float)a );
            else if( a instanceof Double ) diff = Double.compare( (Double)b, (Double)a );
            else if( a instanceof String ) diff = ((String)b).compareTo( (String)a );
            return diff;
        }
        
    }
    
    public MinFunction()
    {
        comparator = new MinComparator();
    }
    
    @Override
    public String getName()
    {
        return "min";
    }

}
