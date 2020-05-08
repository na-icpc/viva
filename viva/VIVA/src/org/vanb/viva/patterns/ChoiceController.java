package org.vanb.viva.patterns;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class ChoiceController implements Pattern
{
    private List<Pattern> choices;
    
    public ChoiceController( List<Pattern> c )
    {
        choices = c;   
    }
    
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        Pattern choice = null;
        
        context.input.dropAnchor();
        boolean itworks = false;
        
        for( Pattern pattern : choices  )
        {
            // Create an artificial level. 
            // We don't want to actually add any variables
            // until we know which choice we're going to use.
            choice = pattern;
            context.values.addLevel();
            context.testLevel++;
            itworks = pattern.test( context );
            context.testLevel--;
            context.values.removeLevel();
            context.input.returnToAnchor();
            
            if( itworks ) break;     
        }
        
        return itworks ? choice.test( context ) : false;
    }

}
