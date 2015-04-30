package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;

/**
 * Class to control a pattern list by looking for a sentinel pattern.
 * 
 * @author David Van Brackle
 */
public class EOLNController extends PatternListController
{
    /**
     * Create a Pattern to control a pattern list by looking for a sentinel pattern.
     */
    public EOLNController()
    {
        super();
    }
            
    /**
     * Test to see if this pattern matches the input file
     * 
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        
        context.values.addLevel();
        for(;;)
        {
            // Check to see if EOLN has been reached
            context.input.dropAnchor();
            boolean ateoln = context.input.atEOLN() || context.input.atEOF();
            context.input.returnToAnchor();
            context.input.raiseAnchor();
           
            if( ateoln ) break; 
             
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
