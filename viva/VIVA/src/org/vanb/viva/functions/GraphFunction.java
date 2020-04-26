package org.vanb.viva.functions;

import java.util.HashSet;
import java.util.List;

import org.vanb.viva.ScalarFunction;
import org.vanb.viva.graphs.Graph;
import org.vanb.viva.utils.VIVAContext;

/**
 * The Class GraphFunction.
 */
public class GraphFunction implements ScalarFunction
{
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName()
    {
        return "graph";
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
        Class<?> retval = Boolean.class;
        
        if( params[0]!=Integer.class && params[0]!=Long.class && params[0]!=String.class ) retval = null;
        else for( int i=1; i<params.length; i++  )
        {
            if( params[i]!=String.class ) retval = null;
        }
        
        return retval;
    }

    /**
     * Gets the usage.
     *
     * @return the usage
     */
    @Override
    public String getUsage()
    {
        return "graph([Integer | Long | String] [,parameter]*) Possible parameters are \"[un]directed\", \"[un]weighted\", \"[no]multi\", \"[no]self\", \"[no]auto\"";
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
    public Object run( VIVAContext context, List<Object> parameters )
            throws Exception
    {
        boolean directed = false;
        boolean weighted = false;
        boolean multi = false;
        boolean self = false;
        boolean auto = false;
                
        HashSet<String> properties = new HashSet<String>();
        
        for( int i=1; i<parameters.size(); i++ )
        {
            String parameter = parameters.get( i ).toString().toLowerCase();
            if( parameter.equals( "directed" ) )
            {
                directed = true;
                if( properties.contains( "undirected" ) ) throw new Exception( "Graph cannot be both directed and undirected." );
            }
            else if( parameter.equals( "undirected" ) )
            {
                if( properties.contains( "directed" ) ) throw new Exception( "Graph cannot be both directed and undirected." );                
            }
            else if( parameter.equals( "weighted" ) )             
            {
                weighted = true;
                if( properties.contains( "unweighted" ) ) throw new Exception( "Graph cannot be both weighted and unweighted." );
            }
            else if( parameter.equals( "unweighted" ) )
            {
                if( properties.contains( "weighted" ) ) throw new Exception( "Graph cannot be both weighted and unweighted." );                
            }
            else if( parameter.equals( "multi" ) )
            {
                multi = true;
                if( properties.contains( "nomulti" ) ) throw new Exception( "Graph cannot be both multi and nomulti." );
            }
            else if( parameter.equals( "nomulti" ) )
            {
                if( properties.contains( "multi" ) ) throw new Exception( "Graph cannot be both multi and nomulti." );                
            }
            else if( parameter.equals( "self" ) ) 
            {
                self = true;
                if( properties.contains( "noself" ) ) throw new Exception( "Graph cannot be both self and noself." );
            }
            else if( parameter.equals( "noself" ) )
            {
                if( properties.contains( "self" ) ) throw new Exception( "Graph cannot be both self and noself." );                
            }
            else if( parameter.equals( "auto" ) )
            {
                auto = true;
                if( properties.contains( "noauto" ) ) throw new Exception( "Graph cannot be both auto and noauto." );
            }
            else if( parameter.equals( "noauto" ) )
            {
                if( properties.contains( "auto" ) ) throw new Exception( "Graph cannot be both auto and noauto." );                
            }

            else throw new Exception( "Unrecognized parameter to graph(): " + parameter );
            
            properties.add( parameter );
        }
        
        String name = parameters.get( 0 ).toString();
        context.setThing( name , new Graph( name, directed, weighted, multi, self, auto ) );
        
        return Boolean.TRUE;
    }

}
