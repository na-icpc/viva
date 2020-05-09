/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.patterns;

import java.util.ArrayList;
import java.util.List;

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
    
    /** The patterns. */
    private List<Pattern> patterns;
    
    /** The constraints. */
    ConstraintList constraints;
    
    /**
     * Create a PatternList.
     */
    public PatternList()
    {
        patterns = new ArrayList<Pattern>();
        constraints = new ConstraintList();
    }
    
    /**
     * Add a pattern to this PatternList.
     *
     * @param pattern Pattern to add
     */
    public void addPattern( Pattern pattern )
    {
        patterns.add( pattern );   
    }
    
    /**
     * Add a constraint to this PatternList.
     *
     * @param constraint the constraint
     */
    public void addConstraint( ExpressionNode constraint )
    {
        constraints.addConstraint( constraint );
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
        
        for( Pattern pattern : patterns ) success &= pattern.test( context );
        
        success &= constraints.test( context );
        
        return success;
    }

}
