package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class SingleLineController extends PatternListController
{
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = super.test( context );
        try
        {
            context.input.getNextLine();
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
            success = false;
        }
        return success;
    }
}
