/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * Class to control a pattern list by looking for a sentinel pattern.
 * 
 * @author David Van Brackle
 */
public class EOFController extends PatternListController
{
    /**
     * Create a Pattern to control a pattern list by looking for a sentinel pattern.
     */
    public EOFController()
    {
        super();
    }
            
    /**
     * Test to see if this pattern matches the input file
     * 
     * @return true if this Pattern matches, otherwise false
     */
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        
        context.values.addLevel();
        for(;;)
        {
            // Check to see if EOF has been reached
            context.input.dropAnchor();
            boolean ateof = context.input.atEOF();
            context.input.returnToAnchor();
            context.input.raiseAnchor();
           
            if(  ateof ) break; 
             
            // If not, reset the line and read it as data
            context.values.incrementLevel();
            success &= patternList.test( context );
            
            // If we're in testing mode, break on any failure.
            if( !success && context.testLevel>0 ) break;
        }
        
        success &= constraints.test( context );
        context.values.removeLevel();
        
        return success;
    }

}
