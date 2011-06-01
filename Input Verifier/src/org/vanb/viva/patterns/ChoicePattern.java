package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;
import java.util.*;

public class ChoicePattern implements Pattern
{
    private List<Pattern> choices;
    
    public ChoicePattern( List<Pattern> c )
    {
        choices = c;   
    }
    
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        Pattern choice = null;
        
        try
        {
            context.input.setAnchor();
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );
        }
        
        for( Pattern pattern : choices  )
        {
            // Create an artificial level. 
            // We don't want to actually add any variables
            // until we know which choice we're going to use.
            choice = pattern;
            context.values.addLevel();
            context.justTesting = true;
            boolean itworks = pattern.test( context );
            context.justTesting = false;
            context.values.removeLevel();
            context.input.returnToAnchor();
            
            if( itworks ) break;            
        }
        
        return choice==null ? false : choice.test( context );
    }

}
