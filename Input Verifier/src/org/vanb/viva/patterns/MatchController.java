package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;

/**
 * Class to control a pattern list by looking for a sentinel pattern.
 * 
 * @author David Van Brackle
 */
public class MatchController extends PatternListController
{
    Pattern terminatingPattern;
    
    /**
     * Create a Pattern to control a pattern list by looking for a sentinel pattern.
     */
    public MatchController()
    {
        super();
        terminatingPattern = null;
    }
    
    /**
     * Set the terminating Pattern.
     * 
     * @param term The terminating pattern
     */
    public void setTerminatingPattern( Pattern term )
    {
        terminatingPattern = term;
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
            // Check to see if the sentinel condition has been reached
            context.values.addLevel();
            context.justTesting = true;
            boolean terminate = terminatingPattern.test( context );
            context.justTesting = false;
            context.values.removeLevel();
            if( terminate ) break;
            
            // if not, reset the line and read it as data
            context.input.resetLine();
            context.values.incrementLevel();
            success &= patternList.test( context );
        }
        
        success &= constraints.test( context );
        context.values.removeLevel();
        
        return success;
    }

}
