/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.patterns;

import org.vanb.viva.expressions.ExpressionNode;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * Class to control a pattern list by looking for a sentinel pattern.
 * 
 * @author David Van Brackle
 */
public class PatternListController implements Pattern
{
    
    /** The pattern list. */
    protected PatternList patternList;
    
    /** The constraints. */
    protected ConstraintList constraints;
    
    /** Is this pattern multiline?. */
    protected boolean multiline = false;
    
    /**
     * Create a Pattern to control a pattern list by looking for a sentinel pattern.
     */
    public PatternListController()
    {
        patternList = null;
        constraints = new ConstraintList();
    }
        
    /**
     * Set the PatternList.
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
     * Test to see if this pattern matches the input file.
     *
     * @param context the context
     * @return true if this Pattern matches, otherwise false
     * @throws VIVAException the VIVA exception
     */
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = patternList.test( context );
        
        success &= constraints.test( context );
        
        return success;
    }

    /**
     * Tell this MatchController that it's controlling a MultiLine sequence.
     */
    public void setMultiLine()
    {
        multiline = true;
    }
}
