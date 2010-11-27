package org.vanb.viva.patterns;

import org.vanb.viva.utils.InputManager;

public class IntegerPattern implements Pattern
{
    @Override
    public boolean test( InputManager input )
    {    
        boolean success = true;
        try
        {
            String token = input.getNextToken();
            int value = Integer.parseInt( token );
        }
        catch( Exception e )
        {
            success = false;
        }
        return success;
    }

}
