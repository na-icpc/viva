package org.vanb.viva.patterns;

import org.vanb.viva.expressions.ExpressionNode;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

public abstract class ValuePattern implements Pattern
{
    private String name;
    private boolean fixedfield = false;
    private ExpressionNode width = null;
    
    public void setName( String n )
    {
        name = n;
    }
    
    public void setFixedField( boolean ff )
    {
        fixedfield = ff;   
    }
    
    public void setWidth( ExpressionNode w )
    {
        width = w;
    }
    
    protected String getToken( VIVAContext context ) throws Exception
    {
        String token = null;
                
        if( fixedfield )
        {
            if( width==null )
            {
                token = context.input.getToEOLN();
            }
            else
            {
                int w = (Integer)width.evaluate( context );
                if( w<0 )
                {
                    context.showError( "Trying to read a field of negative width. [" + width.toString() + "] = " + w  );
                    token = null;
                }
                else
                {
                    token = context.input.getFixedField( w );
                }
            }
        }
        else
        {
            token = context.input.getNextToken();
        }
                
        return token;
    }
    
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        String token = "";
                
        try
        {
            token = getToken( context );
        }
        catch( VIVAException ve )
        {
            String msg = ve.getMessage();
            int p = msg.indexOf( ':' );
            if( p>=0 ) msg = msg.substring( p+1 );
            context.throwException( msg );            
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );
        }
        
        Object value = null;
        ValueManager vm = context.values.lookup( name );
        if( vm==null || !context.values.atCurrentLevel( name ) )
        {
            vm = new ValueManager();
            context.values.add( name, vm );
        }
        
        try
        { 
            if( token!=null )
            {
                value = getValue( token );
            }
            else
            {
                context.showError( "Could not find value for variable " + name );
                success = false;
            }
        }
        catch( Exception e )
        {
            context.showError( "Unable to parse value (" + token + ") as " + getType().getSimpleName() );
            success = false;
        }
        
        if( !success )
        {
            value = getDefaultValue();
        }
        vm.addValue( value, token );
       
        return success;
    }
    
    public abstract Object getValue( String token ) throws Exception;
    
    public abstract Class<?> getType();
    
    public abstract Object getDefaultValue();

}
