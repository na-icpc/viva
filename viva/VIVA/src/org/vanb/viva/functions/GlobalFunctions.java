/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class GlobalFunctions.
 */
public class GlobalFunctions
{

    /**
     * Can't create one of these, it's just a holder.
     */
    private GlobalFunctions() {}
    
    /**
     * Add an element to a global list.
     */
    public static class AddToList implements ScalarFunction
    {

        /**
         * Gets the name of the function.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "addtolist";
        }

        /**
         * Gets the return type.
         *
         * @param params the params
         * @return the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length==2 ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "addtolist(list_id,value)";
        }

        /**
         * Run.
         *
         * @param context the context
         * @param parameters the parameters
         * @return the object
         * @throws Exception the exception
         */
        @SuppressWarnings( "unchecked" )
        @Override
        public Object run( VIVAContext context, List<Object> parameters )
                throws Exception
        {        
            HashSet<Object> set;
            Object id = parameters.get( 0 );
            try
            {
                set = (HashSet<Object>)context.getThing( HashSet.class, id );
            }
            catch( Exception e )
            {
                set = new HashSet<Object>();
                context.setThing( id, set );
            }
            set.add( parameters.get( 1 ) );
            
            return Boolean.TRUE;
        }

    }

    /**
     * Check to see if an element is in a global list.
     */
    public static class InList implements ScalarFunction
    {

        /**
         * Gets the name of the function.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return "inlist";
        }

        /**
         * Gets the return type.
         *
         * @param params the params
         * @return the return type, or null if the params are wrong
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length==2 ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return "inlist(list_id,value)";
        }

        /**
         * Run.
         *
         * @param context the context
         * @param parameters the parameters
         * @return the object
         * @throws Exception the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            Object name = parameters.get( 0 );
            
            @SuppressWarnings( "unchecked" )
            HashSet<Object> set = (HashSet<Object>)context.getThing( HashSet.class, name );
            
            return new Boolean( set.contains( parameters.get( 1 ) ) );
        }

    }
    
    /**
     * Function to get a global value.
     * This should never be run on its own, it should be extended by getint, getlong, etc.
     * 
     * @author vanb
     */
    private static class GetFunction implements ScalarFunction
    {
        
        /** The name. */
        private String name;
        
        /** The type. */
        private Class<?> type;
           
        /**
         * Instantiates a new Set function.
         *
         * @param name the name
         * @param type the type
         */
        public GetFunction( String name, Class<?> type )
        {
            this.name = name;
            this.type = type;
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return name;
        }

        /**
         * Gets the return type.
         *
         * @param params the params
         * @return the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length==1 ? type : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return name + "(varname)";
        }

        /**
         * Run.
         *
         * @param context the context
         * @param parameters the parameters
         * @return the object
         * @throws Exception the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {        
            return context.getThing( type, parameters.get( 0 ) );
        }

    }

    /**
     * Get a global Double.
     */
    public static class GetDouble extends GetFunction
    {
        /**
         * Instantiates the getdouble function.
         */
        public GetDouble()
        {
            super( "getdouble", Double.class );
        }

    }

    /**
     * Get a global Float.
     */
    public static class GetFloat extends GetFunction
    {
        /**
         * Instantiates the getfloat function.
         */
        public GetFloat()
        {
            super( "getfloat", Float.class );
        }

    }

    /**
     * Get a global Int.
     */
    public static class GetInt extends GetFunction
    {
        /**
         * Instantiates the getint function.
         */
        public GetInt()
        {
            super( "getint", Integer.class );
        }

    }

    /**
     * Get a global Long.
     */
    public static class GetLong extends GetFunction
    {
        /**
         * Instantiates the getlong function.
         */
        public GetLong()
        {
            super( "getlong", Long.class );
        }

    }

    /**
     * Get a global String.
     */
    public static class GetString extends GetFunction
    {
        /**
         * Instantiates the getstring function.
         */
        public GetString()
        {
            super( "getstring", String.class );
        }

    }

    /**
     * Function to set a global value.
     * This should never be run on its own, it should be extended by setint, setlong, etc.
     * 
     * @author vanb
     */
    private static class SetFunction implements ScalarFunction
    {
        
        /** The name. */
        private String name;
        
        /** The type. */
        private Class<?> type;
           
        /**
         * Instantiates a new Set function.
         *
         * @param name the name
         * @param type the type
         */
        public SetFunction( String name, Class<?> type )
        {
            this.name = name;
            this.type = type;
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName()
        {
            return name;
        }

        /**
         * Gets the return type.
         *
         * @param params the params
         * @return the return type
         */
        @Override
        public Class<?> getReturnType( Class<?>[] params )
        {
            return params.length==2 && params[1]==type ? Boolean.class : null;
        }

        /**
         * Gets the usage.
         *
         * @return the usage
         */
        @Override
        public String getUsage()
        {
            return name + "(varname," + type.getSimpleName().toLowerCase() + ")";
        }

        /**
         * Run.
         *
         * @param context the context
         * @param parameters the parameters
         * @return the object
         * @throws Exception the exception
         */
        @Override
        public Object run( VIVAContext context, List<Object> parameters ) throws Exception
        {
            context.setThing( parameters.get(0), parameters.get(1) );
            return Boolean.TRUE;
        }

    }

    /**
     * Set a global Double.
     */
    public static class SetDouble extends SetFunction
    {  
        /**
         * Instantiates the setdouble function.
         */
        public SetDouble()
        {
            super( "setdouble", Double.class );
        }

    }

    /**
     * Set a global Float.
     */
    public static class SetFloat extends SetFunction
    { 
        /**
         * Instantiates the setfloat function.
         */
        public SetFloat()
        {
            super( "setfloat", Float.class );
        }

    }

    /**
     * Set a global Int.
     */
    public static class SetInt extends SetFunction
    {
        /**
         * Instantiates the setint function.
         */
        public SetInt()
        {
            super( "setint", Integer.class );
        }

    }

    /**
     * Set a global Long.
     */
    public static class SetLong extends SetFunction
    {
        /**
         * Instantiates the setlong function.
         */
        public SetLong()
        {
            super( "setlong", Long.class );
        }

    }

    /**
     * Set a global String.
     * 
     * @author vanb
     */
    public static class SetString extends SetFunction
    {
        /**
         * Instantiates the setstring function.
         */
        public SetString()
        {
            super( "setstring", String.class );
        }

    }

}
