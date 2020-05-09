/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.patterns;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class ChoiceController.
 */
public class ChoiceController implements Pattern
{
    
    /** The choices. */
    private List<Pattern> choices;
    
    /**
     * Instantiates a new choice controller.
     *
     * @param choices the choices
     */
    public ChoiceController( List<Pattern> choices )
    {
        this.choices = choices;   
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
