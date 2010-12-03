package org.vanb.viva.patterns;

import org.vanb.viva.*;
import org.vanb.viva.expressions.*;
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
    public boolean test( VIVAContext context )
    {
        boolean success = true;
        
        context.values.addLevel();
        for(;;)
        {
            context.values.addLevel();
            boolean terminate = terminatingPattern.test( context );
            context.values.removeLevel();
            if( terminate ) break;
            
            context.input.resetLine();
            success = patternList.test( context );

            if( !success ) break;
        }
        context.values.removeLevel();
        
        if( success ) success = constraints.test( context );
        
        return success;
    }

}
