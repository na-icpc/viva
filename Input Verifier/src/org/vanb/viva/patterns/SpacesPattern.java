package org.vanb.viva.patterns;

import org.vanb.viva.expressions.*;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class SpacesPattern extends ValuePattern
{
    public SpacesPattern( ExpressionNode w )
    {
        super.setName( "@" );
        super.setFixedField( true );
        super.setWidth( w );
    }
    
    @Override
    public Class<?> getType()
    {
        return String.class;
    }

    @Override
    public Object getValue( String token ) throws Exception
    {
        return null;
    }
    
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
    
    
    public Object getDefaultValue()
    {
        return " ";
    }

}
