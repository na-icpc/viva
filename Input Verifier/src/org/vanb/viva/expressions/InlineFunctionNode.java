package org.vanb.viva.expressions;

import java.util.*;

public class InlineFunctionNode extends VariableNode
{
    private LinkedList<ExpressionNode> parameters;
    
    public InlineFunctionNode( String name, Class<?> type, LinkedList<ExpressionNode> parms )
    {
        super( name, type );
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
