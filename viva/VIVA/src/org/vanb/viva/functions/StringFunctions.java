/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.functions;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.VectorFunction;
import org.vanb.viva.utils.VIVAContext;

import java.util.List;

/**
 * All of VIVA's String functions.
 */
public class StringFunctions
{

    /**
     * Can't create one of these, it's just a holder.
     */
    private StringFunctions() {}

    /**
     * Concatenate vector function.
     */
    public static class ConcatAll implements VectorFunction
    {

        /** The builder. */
        private StringBuilder builder = new StringBuilder();

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "concatall";
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
            return String.class;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "concatall(can take any parameters)";
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

            for( List<Object> row : parameters )
            {
                for( Object p : row )
                {
                    builder.append( p.toString() );
                }
            }

            return builder.toString();
        }

    }

    /**
     * Concatenate scalar function.
     */
    public static class Concatenate implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "concat";
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
            return String.class;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "concat(args1,arg2,...)";
        }

        /** The builder. */
        private StringBuilder builder = new StringBuilder();

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
            builder.setLength( 0 );
            for( Object parameter : parameters )
            {
                builder.append( parameter.toString() );
            }
            return builder.toString();
        }

    }

    /**
     * Checks if the string is left-justified.
     */
    public static class LeftJustification implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "ljust";
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "ljust(string)";
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
            return params.length == 1 && params[0] == String.class ? Boolean.class : null;
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
            String value = parameters.get( 0 ).toString();
            char firstch = value.charAt( 0 );
            return !Character.isWhitespace( firstch );
        }

    }

    /**
     * String length.
     */
    public static class Length implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "length";
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "length(string)";
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
            return params.length == 1 && params[0] == String.class ? Integer.class : null;
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
            return parameters.get( 0 ).toString().length();
        }

    }

    /**
     * Checks if the string is left-justified.
     */
    public static class RightJustification implements ScalarFunction
    {

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "rjust";
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "rjust(string)";
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
            return params.length == 1 && params[0] == String.class ? Boolean.class : null;
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
            String value = parameters.get( 0 ).toString();
            char lastch = value.charAt( value.length() - 1 );
            return !Character.isWhitespace( lastch );
        }

    }

}
