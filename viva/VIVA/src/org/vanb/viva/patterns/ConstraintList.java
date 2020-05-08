package org.vanb.viva.patterns;

import java.util.ArrayList;
import java.util.List;

import org.vanb.viva.expressions.ExpressionNode;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class ConstraintList.
 */
public class ConstraintList implements Pattern
{
    
    /** The constraints. */
    private List<ExpressionNode> constraints;
    
    /**
     * Instantiates a new constraint list.
     */
    public ConstraintList()
    {
        constraints = new ArrayList<ExpressionNode>();
    }
    
    /**
     * Adds the constraint.
     *
     * @param exp the exp
     */
    public void addConstraint( ExpressionNode exp )
    {
        constraints.add( exp );   
    }
    
    /**
     * Test.
     *
     * @param context the context
     * @return true, if successful
     * @throws VIVAException the VIVA exception
     */
    public boolean test( VIVAContext context ) throws VIVAException
    {
        boolean success = true;
        for( ExpressionNode constraint : constraints )
        {
            if( !constraint.getReturnType().equals( Boolean.class ) )
            {
                success = false;
                context.showError( "Constraint evaluates to non-Boolean" );
            }
            else
            {
                success = (Boolean)constraint.evaluate( context );
                if( !success )
                {
                    context.showError( "Failed constraint: " + constraint );
                }
            }
            
            if( !success ) break;
        }
        
        return success;
    }
}
