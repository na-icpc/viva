package org.vanb.viva.functions;

import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class MiscFunctions.
 */
public class MiscFunctions
{

    /**
     * Can't create one, it's just a holder.
     */
    private MiscFunctions() {}

    /**
     * Testing Vector function.
     */
    public static class TestAll implements VectorFunction
    {

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
            for( List<Object> row : parameters )
            {
                for( Object p : row )
                {
                    context.err.print( "<" + p + ">" );
                }
                context.err.println();
            }
            context.err.println( "---------------" );

            return Boolean.TRUE;
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "testall";
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
            return "testall(can take any parameters)";
        }

    }

    /**
     * testing Scalar function.
     */
    public static class Test implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "test";
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
            return "test(args1,arg2,...)";
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
            for( Object parameter : parameters )
            {
                context.err.print( "<" + parameter + ">" );
            }
            context.err.println();

            return Boolean.TRUE;
        }

    }

    /**
     * Convert something to a Double.
     */
    public static class ToDouble implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "todouble";
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
            return params.length == 1 ? Double.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "todouble(arg)";
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
            Object param = parameters.get( 0 );
            Double result;
            try
            {
                if( Number.class.isAssignableFrom( param.getClass() ) )
                {
                    Number value = (Number)param;
                    result = value.doubleValue();
                }
                else if( param.getClass() == Boolean.class )
                {
                    result = (Boolean)param ? 1.0 : 0.0;
                }
                else
                {
                    result = new Double( param.toString() );
                }
            }
            catch( Exception e )
            {
                throw new Exception( "Could not convert '" + param + "' to a Double" );
            }

            return result;
        }

    }

    /**
     * Convert something to a Float.
     */
    public static class ToFloat implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "tofloat";
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
            return params.length == 1 ? Float.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "tofloat(arg)";
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
            Object param = parameters.get( 0 );
            Float result;
            try
            {
                if( Number.class.isAssignableFrom( param.getClass() ) )
                {
                    Number value = (Number)param;
                    result = value.floatValue();
                }
                else if( param.getClass() == Boolean.class )
                {
                    result = (Boolean)param ? 1.0F : 0.0F;
                }
                else
                {
                    result = new Float( param.toString() );
                }
            }
            catch( Exception e )
            {
                throw new Exception( "Could not convert '" + param + "' to a Float" );
            }

            return result;
        }
    }

    /**
     * Convert something to an Integer.
     */
    public static class ToInteger implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "tointeger";
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
            return params.length == 1 ? Integer.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "tointeger(arg)";
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
            Object param = parameters.get( 0 );
            Integer result;
            try
            {
                if( Number.class.isAssignableFrom( param.getClass() ) )
                {
                    Number value = (Number)param;
                    result = value.intValue();
                }
                else if( param.getClass() == Boolean.class )
                {
                    result = (Boolean)param ? 1 : 0;
                }
                else
                {
                    result = new Integer( param.toString() );
                }
            }
            catch( Exception e )
            {
                throw new Exception( "Could not convert '" + param + "' to an Integer" );
            }

            return result;
        }
    }

    /**
     * Convert something to a Long.
     */
    public static class ToLong implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "tolong";
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
            return params.length == 1 ? Long.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "tolong(arg)";
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
            Object param = parameters.get( 0 );
            Long result;
            try
            {
                if( Number.class.isAssignableFrom( param.getClass() ) )
                {
                    Number value = (Number)param;
                    result = value.longValue();
                }
                else if( param.getClass() == Boolean.class )
                {
                    result = (Boolean)param ? 1L : 0L;
                }
                else
                {
                    result = new Long( param.toString() );
                }
            }
            catch( Exception e )
            {
                throw new Exception( "Could not convert '" + param + "' to a Long" );
            }

            return result;
        }
    }

    /**
     * Convert something to a String.
     */
    public static class ToString implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "tostring";
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
            return params.length == 1 ? String.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "tostring(arg)";
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
            return parameters.get( 0 ).toString();
        }

    }

}
