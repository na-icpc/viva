package org.vanb.viva.expressions;

import java.util.*;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

public abstract class FunctionNode extends VariableNode
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
    
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Object value; 
        
        String name = toString();
        
        ValueManager vm = context.values.lookup( name );
        if( vm==null || !context.values.atCurrentLevel( name ) )
        {
            vm = new ValueManager();
            context.values.add( name, vm );
            
            value = getValue( context );
            vm.addValue( value, name );
        }
        else
        {
            if( context.index>=0 )
            {
                if( vm.getCount() <= context.index )
                {
                    value = getValue( context );
                    vm.addValue( value, name );                
                    
                }
                else
                {
                    value = vm.getNth( context.index, true ); 
                }
            }
            else if( vm.getCount() < context.values.getCount() )
            {
                value = getValue( context );
                vm.addValue( value, name );                
            }
            else
            {
                value = vm.getCurrent( true );                      
            }
        }
        
        return value;
    }
    
    public abstract Object getValue( VIVAContext context ) throws VIVAException;
}
