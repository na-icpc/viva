package org.vanb.viva.expressions;

import java.util.*;

public class FunctionNode extends VariableNode
{
    protected LinkedList<ExpressionNode> parameters;
    
    public FunctionNode( String name, Class<?> type, LinkedList<ExpressionNode> parms )
    {
        super( name, type, true );
        parameters = parms;
    }
    
    public String toString()
    {
        String result = name + "(";
        
        boolean first = true;
        for( ExpressionNode parm : parameters )
        {
            if( first )
            {
                first = false;   
            }
            else
            {
                result += ",";
            }
            result += parm.toString();
        }
        
        result += ")";
        
        return result;
    }
}
