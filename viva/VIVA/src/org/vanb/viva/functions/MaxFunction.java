package org.vanb.viva.functions;

import java.util.Comparator;

/**
 * The Class MaxFunction.
 */
public class MaxFunction extends MinMaxFunction
{
    
    /**
     * The Class MaxComparator.
     */
    public static class MaxComparator implements Comparator<Object>
    {

        /**
         * Compare.
         *
         * @param a a value
         * @param b another value
         * @return standard for compare
         */
        @Override
        public int compare( Object a, Object b )
        {
            int diff = 0;
            if( a==null ) diff = -1;
            else if( b==null ) diff = 1;
            else if( a instanceof Integer ) diff = Integer.compare( (Integer)a, (Integer)b );
            else if( a instanceof Long ) diff = Long.compare( (Long)a, (Long)b );
            else if( a instanceof Float ) diff = Float.compare( (Float)a, (Float)b );
            else if( a instanceof Double ) diff = Double.compare( (Double)a, (Double)b );
            else if( a instanceof String ) diff = ((String)a).compareTo( (String)b );
            return diff;
        }
        
    }
    
    /**
     * Instantiates a new max function.
     */
    public MaxFunction()
    {
        comparator = new MaxComparator();
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName()
    {
        return "max";
    }

}
