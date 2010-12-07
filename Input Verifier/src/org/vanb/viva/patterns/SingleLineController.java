package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;

public class SingleLineController extends PatternListController
{
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = super.test( context );
        try
        {
            context.input.getNextLine( context, false );
        }
        catch( Exception e )
        {
            success = false;
        }
        return success;
    }
}
