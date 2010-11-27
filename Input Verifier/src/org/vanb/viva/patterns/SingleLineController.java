package org.vanb.viva.patterns;

import org.vanb.viva.utils.InputManager;

public class SingleLineController extends PatternListController
{
    public boolean test( InputManager input )
    {
        boolean success = super.test( input );
        try
        {
            input.getNextLine();
        }
        catch( Exception e )
        {
            success = false;
        }
        return success;
    }
}
