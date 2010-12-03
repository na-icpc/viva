package org.vanb.viva.patterns;

import org.vanb.viva.*;
import org.vanb.viva.expressions.*;
import org.vanb.viva.utils.*;

import java.util.*;

/**
 * Class to control a pattern list by looking for a sentinel pattern.
 * 
 * @author David Van Brackle
 */
public class PatternListController implements Pattern
{
    PatternList patternList;
    ConstraintList constraints;
    
    /**
     * Create a Pattern to control a pattern list by looking for a sentinel pattern.
     */
    public PatternListController()
    {
        patternList = null;
        constraints = new ConstraintList();
    }
        
    /**
     * Set the PatternList
     * 
     * @param list the list of Patterns to control
     */
    public void setPatternList( PatternList list )
    {
        patternList = list;    
    }
    
    /**
     * Set the Constraint.
     * 
     * @param expression the Constraint
     */
    public void addConstraint( ExpressionNode expression )
    {
        constraints.addConstraint( expression );   
    }
    
    /**
     * Test to see if this pattern matches the input file
     * 
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( VIVAContext context )
    {
        boolean success = patternList.test( context );
        
        if( success ) success = constraints.test( context );
        
        return success;
    }

}
