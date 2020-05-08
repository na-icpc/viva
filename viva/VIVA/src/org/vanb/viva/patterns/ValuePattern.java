package org.vanb.viva.patterns;

import org.vanb.viva.expressions.ExpressionNode;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

/**
 * The Class ValuePattern.
 */
public abstract class ValuePattern implements Pattern
{
    
    /** The name. */
    private String name;
    
    /** Is this Fixed field?. */
    private boolean fixedfield = false;
    
    /** The width. */
    private ExpressionNode width = null;
    
    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName( String name )
    {
        this.name = name;
    }
    
    /**
     * Sets the fixed field.
     *
     * @param ff the new fixed field
     */
    public void setFixedField( boolean ff )
    {
        fixedfield = ff;   
    }
    
    /**
     * Sets the width.
     *
     * @param w the new width
     */
    public void setWidth( ExpressionNode w )
    {
        width = w;
    }
    
    /**
     * Gets the token.
     *
     * @param context the context
     * @return the token
     * @throws Exception the exception
     */
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
    
    /**
     * Test.
     *
     * @param context the context
     * @return true, if successful
     * @throws VIVAException the VIVA exception
     */
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
                value = getValue( token, context );
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
    
    /** A RegEx pattern for a good double. */
    public static java.util.regex.Pattern goodDouble = java.util.regex.Pattern.compile( "\\-?(0|[1-9][0-9]*)(\\.[0-9]*)?" );
    
    /**
     * Gets the value.
     *
     * @param token the token
     * @param context the context
     * @return the value
     * @throws Exception the exception
     */
    public abstract Object getValue( String token, VIVAContext context ) throws Exception;
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    public abstract Class<?> getType();
    
    /**
     * Gets the default value.
     *
     * @return the default value
     */
    public abstract Object getDefaultValue();

}
