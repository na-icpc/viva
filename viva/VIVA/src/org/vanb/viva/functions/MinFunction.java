package org.vanb.viva.functions;

import java.util.Comparator;

/**
 * The Class MinFunction.
 */
public class MinFunction extends MinMaxFunction
{
    /**
     * The Class MinComparator.
     */
    public static class MinComparator implements Comparator<Object>
    {
        /**
         * Compare two values.
         *
         * @param a A value
         * @param b Another value
         * @return Standard for compare, but backwards
         */
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
    
    /**
     * Instantiates a new min function.
     */
    public MinFunction()
    {
        comparator = new MinComparator();
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName()
    {
        return "min";
    }

}
