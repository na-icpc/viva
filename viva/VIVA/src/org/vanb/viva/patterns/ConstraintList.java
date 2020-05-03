package org.vanb.viva.patterns;

import java.util.ArrayList;
import java.util.List;

import org.vanb.viva.expressions.ExpressionNode;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

public class ConstraintList implements Pattern
{
    private List<ExpressionNode> constraints;
    
    public ConstraintList()
    {
        constraints = new ArrayList<ExpressionNode>();
    }
    
    public void addConstraint( ExpressionNode exp )
    {
        constraints.add( exp );   
    }
    
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
