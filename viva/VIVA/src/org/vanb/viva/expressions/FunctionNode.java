package org.vanb.viva.expressions;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

/**
 * The Class FunctionNode.
 */
public abstract class FunctionNode extends VariableNode
{
    
    /** The parameters. */
    protected List<ExpressionNode> parameters;
    
    /**
     * Instantiates a new function node.
     *
     * @param name the name
     * @param type the type
     * @param parms the parms
     */
    public FunctionNode( String name, Class<?> type, List<ExpressionNode> parms )
    {
        super( name, type, true );
        parameters = parms;
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    public String toString()
    {
        String result = name + "(";
        
        boolean first = true;
        for( ExpressionNode parm : parameters )
        {
            if( first ) first = false;   
            else result += ",";
            result += parm.toString();
        }
        
        result += ")";
        
        return result;
    }
    
    /**
     * Evaluate.
     *
     * @param context the context
     * @return the object
     * @throws VIVAException the VIVA exception
     */
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
    
    /**
     * Gets the value.
     *
     * @param context the context
     * @return the value
     * @throws VIVAException the VIVA exception
     */
    public abstract Object getValue( VIVAContext context ) throws VIVAException;
}
