package org.vanb.viva.patterns;

import org.vanb.viva.expressions.ExpressionNode;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class SpacesPattern.
 */
public class SpacesPattern extends ValuePattern
{
    
    /**
     * Instantiates a new spaces pattern.
     *
     * @param w the w
     */
    public SpacesPattern( ExpressionNode w )
    {
        super.setName( "@" );
        super.setFixedField( true );
        super.setWidth( w );
    }
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class<?> getType()
    {
        return String.class;
    }

    /**
     * Gets the value.
     *
     * @param token the token
     * @param context the context
     * @return the value
     * @throws Exception the exception
     */
    @Override
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        return null;
    }
    
    /**
     * Test.
     *
     * @param context the context
     * @return true, if successful
     * @throws VIVAException the VIVA exception
     */
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
        
        if( token!=null )
        {
            for( int i=0; i<token.length(); i++ )
            {
                if( token.charAt( i ) != ' ' )
                {
                    context.showError( "Expecting blank, got <" + token.charAt(i) + ">" );
                    success = false;
                    break;
                }
            }
        }
        
        return success;
    }
    
    
    /**
     * Gets the default value.
     *
     * @return the default value
     */
    public Object getDefaultValue()
    {
        return " ";
    }

}
