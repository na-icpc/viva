package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

public abstract class ValuePattern implements Pattern
{
    private String name;
    
    public void setName( String n )
    {
        name = n;
    }
    
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        String token = "";
        
        try
        {
            token = context.input.getNextToken( context );
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );
        }
        
        try
        {
            Object value = getValue( token );
            ValueManager vm = context.values.lookup( name );
            if( vm==null || !context.values.atCurrentLevel( name ) )
            {
                vm = new ValueManager();
                context.values.add( name, vm );
            }
            vm.addValue( value, token );
        }
        catch( Exception e )
        {
            context.showError( "Unable to parse value as " + getType().getSimpleName() );
            success = false;
        }
        
        return success;
    }
    
    public abstract Object getValue( String token ) throws Exception;
    
    public abstract Class<?> getType();

}
