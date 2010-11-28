package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;

public class SingleLineController extends PatternListController
{
    public boolean test( InputManager input, SymbolTable<ValueManager> values )
    {
        boolean success = super.test( input, values );
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
