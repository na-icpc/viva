package org.vanb.viva.expressions;

import java.util.*;

import org.vanb.viva.functions.*;

public class FunctionNode extends VariableNode
{
    protected LinkedList<ExpressionNode> parameters;
    protected Function function;
    
    public FunctionNode( String name, Class<?> type, Function f, LinkedList<ExpressionNode> parms )
    {
        super( name, type );
        parameters = parms;
        function = f;
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
