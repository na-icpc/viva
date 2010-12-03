package org.vanb.viva.patterns;

import java.util.*;

import org.vanb.viva.*;
import org.vanb.viva.expressions.*;
import org.vanb.viva.utils.*;

/**
 * This class handles repeating patterns, both multiline and within a line.
 * 
 * @author David Van Brackle
 */
public class PatternList implements Pattern
{
    private LinkedList<Pattern> patterns;
    ConstraintList constraints;
    
    /**
     * Create a PatternList
     */
    public PatternList()
    {
        patterns = new LinkedList<Pattern>();
        constraints = new ConstraintList();
    }
    
    /**
     * Add a pattern to this patternList
     * 
     * @param pattern Pattern to add
     */
    public void addPattern( Pattern pattern )
    {
        patterns.add( pattern );   
    }
    
    public void addConstraint( ExpressionNode constraint )
    {
        constraints.addConstraint( constraint );
    }
    
    /**
     * Test to see if this pattern matches the input file
     * 
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( VIVAContext context )
    {
        boolean success = true;
        
        for( Pattern pattern : patterns )
        {
            success = pattern.test( context );
            if( !success ) break;
        }
        
        if( success ) success = constraints.test( context );
        
        return success;
    }

}
