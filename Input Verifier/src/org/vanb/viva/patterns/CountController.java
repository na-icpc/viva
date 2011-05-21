package org.vanb.viva.patterns;

import org.vanb.viva.expressions.*;
import org.vanb.viva.utils.*;

/**
 * Class to control a pattern list by looking for a sentinel pattern.
 * 
 * @author David Van Brackle
 */
public class CountController extends PatternListController
{
    ExpressionNode count;
    
    /**
     * Create a Pattern to control a pattern list by looking for a sentinel pattern.
     */
    public CountController()
    {
        super();
        count = null;
    }
    
    /**
     * Set the Count.
     * 
     * @param exp The number of times to repeat the pattern.
     */
    public void setCountExpression( ExpressionNode exp )
    {
        count = exp;
    }
        
    /**
     * Test to see if this pattern matches the input file
     * 
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        int c=0;
        
        try
        {
            c = (Integer)count.evaluate( context );
        }
        catch( Exception e )
        {
            context.throwException( e.getMessage() );
        }
        
        context.values.addLevel();
        for( int i=0; i<c; i++ )
        {
            context.values.incrementLevel();
            success &= patternList.test( context );
        }
        
        success &= constraints.test( context );
        context.values.removeLevel();
        
        return success;
    }

}
