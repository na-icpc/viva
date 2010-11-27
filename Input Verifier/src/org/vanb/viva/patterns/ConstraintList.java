package org.vanb.viva.patterns;

import org.vanb.viva.expressions.*;
import java.util.*;

public class ConstraintList
{
    private LinkedList<ExpressionNode> constraints;
    
    public ConstraintList()
    {
        constraints = new LinkedList<ExpressionNode>();
    }
    
    public void addConstraint( ExpressionNode exp )
    {
        constraints.add( exp );   
    }
    
    public boolean test()
    {
        boolean success = true;
        for( ExpressionNode constraint : constraints )
        {
            if( !constraint.returnType().equals( Boolean.class ) )
            {
                success = false;
            }
            else
            {
                success = (Boolean)constraint.evaluate();
            }
            
            if( !success ) break;
        }
        
        return success;
    }
}
