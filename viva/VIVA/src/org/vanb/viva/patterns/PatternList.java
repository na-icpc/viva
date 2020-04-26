package org.vanb.viva.patterns;

import java.util.LinkedList;

import org.vanb.viva.expressions.ExpressionNode;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

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
     * Add a pattern to this PatternList
     * 
     * @param pattern Pattern to add
     */
    public void addPattern( Pattern pattern )
    {
        patterns.add( pattern );   
    }
    
    /**
     * Add a constraint to this PatternList
     * 
     * @param pattern Pattern to add
     */
    public void addConstraint( ExpressionNode constraint )
    {
        constraints.addConstraint( constraint );
    }
    
    /**
     * Test to see if this pattern matches the input file
     * 
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        
        for( Pattern pattern : patterns ) success &= pattern.test( context );
        
        success &= constraints.test( context );
        
        return success;
    }

}
