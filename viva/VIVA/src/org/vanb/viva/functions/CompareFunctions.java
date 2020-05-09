/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.functions;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * VIVA Functions that implement comparisons.
 */
public class CompareFunctions
{

    /**
     * Instantiates a new compare functions.
     */
    public CompareFunctions() {}

    /**
     * Check if a vector is Decreasing.
     */
    public static class Decreasing implements VectorFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "decreasing";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 1 ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "decreasing( int or long or double or float )";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
        {

            Object last = null;
            boolean success = true;
            Class<?> type = null;
            if( !parameters.isEmpty() ) type = parameters.get( 0 ).get( 0 ).getClass();
            for( List<Object> row : parameters )
            {
                Object next = row.get( 0 );
                if( last != null )
                {
                    if( type == Integer.class ) success = ( (Number)last ).intValue() > ( (Number)next ).intValue();
                    else if( type == Long.class ) success = ( (Number)last ).longValue() > ( (Number)next ).longValue();
                    else if( type == Double.class ) success = ( (Number)last ).doubleValue() > ( (Number)next ).doubleValue();
                    else if( type == Float.class ) success = ( (Number)last ).floatValue() > ( (Number)next ).floatValue();
                    else if( type == String.class ) success = last.toString().compareTo( next.toString() ) > 0;
                    if( !success ) break;
                }

                last = next;
            }

            return success;
        }

    }

    /**
     * Check if a vector is Increasing.
     */
    public static class Increasing implements VectorFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "increasing";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 1 ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "increasing( int or long or double or float )";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
        {
            Object last = null;
            boolean success = true;
            Class<?> type = null;
            if( !parameters.isEmpty() ) type = parameters.get( 0 ).get( 0 ).getClass();
            for( List<Object> row : parameters )
            {
                Object next = row.get( 0 );
                if( last != null )
                {
                    if( type == Integer.class ) success = ( (Number)last ).intValue() < ( (Number)next ).intValue();
                    else if( type == Long.class ) success = ( (Number)last ).longValue() < ( (Number)next ).longValue();
                    else if( type == Double.class ) success = ( (Number)last ).doubleValue() < ( (Number)next ).doubleValue();
                    else if( type == Float.class ) success = ( (Number)last ).floatValue() < ( (Number)next ).floatValue();
                    else if( type == String.class ) success = last.toString().compareTo( next.toString() ) < 0;
                    if( !success ) break;
                }

                last = next;
            }

            return success;
        }

    }

    /**
     * Conditional: if( Boolean, if_true, if_false ).
     */
    public static class If implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         * @see    org.vanb.viva.Function#getName()
         */
        @Override
        public String getName()
        {
            return "if";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         * @see           org.vanb.viva.Function#getReturnType(java.lang.Class[])
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return ( params.length == 3 && params[0] == Boolean.class && params[1] == params[2] ) ? params[1] : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         * @see    org.vanb.viva.Function#getUsage()
         */
        @Override
        public String getUsage()
        {
            return "if(boolean,arg1,arg2) arg1 and arg2 can be of any type, but they must be of the SAME type.";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         * @see               org.vanb.viva.ScalarFunction#run(org.vanb.viva.utils.VIVAContext, java.util.List)
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            return (Boolean)parameters.get( 0 ) ? parameters.get( 1 ) : parameters.get( 2 );
        }

    }

    /**
     * A base class for minimum and maximum scalar functions.
     */
    private static abstract class MinMaxFunction implements ScalarFunction
    {

        /** The comparator. */
        protected Comparator<Object> comparator;

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            Class<?> result = null;
            if( params.length > 0 )
            {
                result = params[0];
                for( int i = 1; i < params.length; i++ )
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
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            Object best = null;
            for( Object parm : parameters )
            {
                if( comparator.compare( best, parm ) < 0 ) best = parm;
            }
            return best;
        }

    }

    /**
     * A base class for minimum and maximum vector functions.
     */
    private static abstract class MinMaxAllFunction implements VectorFunction
    {
        /** The comparator. */
        protected Comparator<Object> comparator;

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            Class<?> result = null;
            if( params.length > 0 )
            {
                result = params[0];
                for( int i = 1; i < params.length; i++ )
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
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
        {
            Object best = null;
            for( List<Object> list : parameters ) for( Object parm : list )
            {
                if( comparator.compare( best, parm ) < 0 ) best = parm;
            }
            return best;
        }

    }

    /**
     * A comparator for Maximum functions.
     */
    private static class MaxComparator implements Comparator<Object>
    {

        /**
         * Compare.
         *
         * @param  a a value
         * @param  b another value
         * @return   standard for compare
         */
        @Override
        public int compare( Object a, Object b )
        {
            int diff = 0;
            if( a == null ) diff = -1;
            else if( b == null ) diff = 1;
            else if( a instanceof Integer ) diff = Integer.compare( (Integer)a, (Integer)b );
            else if( a instanceof Long ) diff = Long.compare( (Long)a, (Long)b );
            else if( a instanceof Float ) diff = Float.compare( (Float)a, (Float)b );
            else if( a instanceof Double ) diff = Double.compare( (Double)a, (Double)b );
            else if( a instanceof String ) diff = ( (String)a ).compareTo( (String)b );
            return diff;
        }

    }

    /**
     * Maximum of a list.
     */
    public static class Max extends MinMaxFunction
    {

        /**
         * The Class MaxComparator.
         */

        /**
         * Instantiates a new max function.
         */
        public Max()
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

    /**
     * Maximum of a vector of lists.
     */
    public static class MaxAll extends MinMaxAllFunction
    {

        /**
         * Instantiates a new maxall function.
         */
        public MaxAll()
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
            return "maxall";
        }

    }

    /**
     * A comparator for Minimum functions.
     */
    private static class MinComparator implements Comparator<Object>
    {
        /**
         * Compare two values.
         *
         * @param  a A value
         * @param  b Another value
         * @return   Standard for compare, but backwards
         */
        @Override
        public int compare( Object a, Object b )
        {
            int diff = 0;
            if( a == null ) diff = -1;
            else if( b == null ) diff = 1;
            else if( a instanceof Integer ) diff = Integer.compare( (Integer)b, (Integer)a );
            else if( a instanceof Long ) diff = Long.compare( (Long)b, (Long)a );
            else if( a instanceof Float ) diff = Float.compare( (Float)b, (Float)a );
            else if( a instanceof Double ) diff = Double.compare( (Double)b, (Double)a );
            else if( a instanceof String ) diff = ( (String)b ).compareTo( (String)a );
            return diff;
        }

    }

    /**
     * Minimum of a list.
     */
    public static class Min extends MinMaxFunction
    {
        /**
         * The Class MinComparator.
         */

        /**
         * Instantiates a new min function.
         */
        public Min()
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

    /**
     * Minimum of a vector of lists.
     */
    public static class MinAll extends MinMaxAllFunction
    {

        /**
         * Instantiates a new minall function.
         */
        public MinAll()
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
            return "minall";
        }

    }

    /**
     * Check if a vector is NonDecreasing.
     */
    public static class NonDecreasing implements VectorFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "nondecreasing";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 1 ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "nondecreasing( int or long or double or float )";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
        {
            double deps = context.deps;
            float feps = context.feps;

            Object last = null;
            boolean success = true;
            Class<?> type = null;
            if( !parameters.isEmpty() ) type = parameters.get( 0 ).get( 0 ).getClass();
            for( List<Object> row : parameters )
            {
                Object next = row.get( 0 );
                if( last != null )
                {
                    if( type == Integer.class ) success = ( (Number)last ).intValue() <= ( (Number)next ).intValue();
                    else if( type == Long.class ) success = ( (Number)last ).longValue() <= ( (Number)next ).longValue();
                    else if( type == Double.class ) success = ( (Number)last ).doubleValue() <= ( (Number)next ).doubleValue() + deps;
                    else if( type == Float.class ) success = ( (Number)last ).floatValue() <= ( (Number)next ).floatValue() + feps;
                    else if( type == String.class ) success = last.toString().compareTo( next.toString() ) <= 0;
                    if( !success ) break;
                }

                last = next;
            }

            return success;
        }

    }

    /**
     * Check if a vector is NonIncreasing.
     */
    public static class NonIncreasing implements VectorFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "nonincreasing";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 1 ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "nonincreasing( int or long or double or float )";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
        {
            double deps = (Double)context.getParameter( "deps" );
            float feps = (Float)context.getParameter( "feps" );

            Object last = null;
            boolean success = true;
            Class<?> type = null;
            if( !parameters.isEmpty() ) type = parameters.get( 0 ).get( 0 ).getClass();
            for( List<Object> row : parameters )
            {
                Object next = row.get( 0 );
                if( last != null )
                {
                    if( type == Integer.class ) success = ( (Number)last ).intValue() >= ( (Number)next ).intValue();
                    else if( type == Long.class ) success = ( (Number)last ).longValue() >= ( (Number)next ).longValue();
                    else if( type == Double.class ) success = ( (Number)last ).doubleValue() >= ( (Number)next ).doubleValue() - deps;
                    else if( type == Float.class ) success = ( (Number)last ).floatValue() >= ( (Number)next ).floatValue() - feps;
                    else if( type == String.class ) success = last.toString().compareTo( next.toString() ) >= 0;
                    if( !success ) break;
                }

                last = next;
            }

            return success;
        }

    }

    /**
     * Check if every element of a vector is Unique.
     */
    public static class Unique implements VectorFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "unique";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return Boolean.class;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "unique( <can take any parameters> )";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
        {
            HashSet<String> rows = new HashSet<String>();

            boolean unique = true;

            // Go through each row
            for( List<Object> row : parameters )
            {
                // Build a "key" - a unique String representation of the row
                String key = "";
                for( Object object : row )
                {
                    key += object.toString() + "\256";
                }

                // Look to see if we've seen this row before
                if( rows.contains( key ) )
                {
                    unique = false;
                    break;
                }

                // if we haven't seen it before, add this row to the list of seen rows
                rows.add( key );
            }
            return unique;
        }

    }

    /**
     * Count the number of things in a vector that satisfy a condition.
     */
    public static class Count implements VectorFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "count";
        }

        /**
         * Gets the return type.
         *
         * @param  params the params
         * @return        the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length == 1 && params[0] == Boolean.class ? Integer.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "count( boolean )";
        }

        /**
         * Run.
         *
         * @param  context    the context
         * @param  parameters the parameters
         * @return            the object
         * @throws Exception  the exception
         */
        @Override
        public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception
        {
            int count = 0;

            for( List<Object> row : parameters )
            {
                Boolean result = (Boolean)row.get( 0 );
                if( result ) ++count;
            }

            return new Integer( count );
        }

    }

}
