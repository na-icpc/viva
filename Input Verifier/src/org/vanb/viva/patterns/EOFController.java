package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;

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
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        
        context.values.addLevel();
        for(;;)
        {
            if( context.input.atEOF() ) break;
            success &= patternList.test( context );
        }
        
        success &= constraints.test( context );
        context.values.removeLevel();
        
        return success;
    }

}
