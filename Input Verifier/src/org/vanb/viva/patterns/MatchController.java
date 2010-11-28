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
     * @param input A controller for the input source
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( InputManager input, SymbolTable<ValueManager> values )
    {
        boolean success = true;
        
        values.addLevel();
        for(;;)
        {
            values.addLevel();
            boolean terminate = terminatingPattern.test( input, values );
            values.removeLevel();
            if( terminate ) break;
            
            input.resetLine();
            success = patternList.test( input, values );

            if( !success ) break;
        }
        values.removeLevel();
        
        if( success ) success = constraints.test( values );
        
        return success;
    }

}
