package org.vanb.viva.patterns;

import org.vanb.viva.expressions.*;
import org.vanb.viva.utils.*;

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
    
    public boolean test( SymbolTable<ValueManager> values )
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
                success = (Boolean)constraint.evaluate( values );
            }
            
            if( !success ) break;
        }
        
        return success;
    }
}
