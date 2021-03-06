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
public class MatchController extends PatternListController
{
    
    /** The terminating pattern. */
    private Pattern terminatingPattern;

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
     * Test to see if this pattern matches the input file.
     *
     * @param context the context
     * @return true if this Pattern matches, otherwise false
     * @throws VIVAException the VIVA exception
     */
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        
        context.values.addLevel();
        for(;;)
        {
            // Check to see if the sentinel condition has been reached
            context.input.dropAnchor();
            context.values.addLevel();
            context.testLevel++;
            boolean terminate = terminatingPattern.test( context );
            context.testLevel--;
            context.values.removeLevel();
            if( terminate ) 
            {
                context.input.raiseAnchor();
                if( multiline )
                {
                    try { context.input.getNextLine(); } catch( Exception e ) {};
                }
                break;
            }
            else
            {
                context.input.returnToAnchor();
                context.input.raiseAnchor();
            }
            
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
