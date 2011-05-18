package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

public class SubscriptNode extends VariableNode
{
    private ExpressionNode subscript;
    
    public SubscriptNode( String name, Class<?> type, ExpressionNode sub )
    {
        super( name, type );
        subscript = sub;
    }
    
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        ValueManager vm = context.values.lookup( name );
        Object value = null;
        
        if( vm!=null )
        {
            Object subvalue = subscript.evaluate( context );
            if( subvalue instanceof Integer )
            {
                int n = (Integer)subvalue;
                if( n>=0 && n<vm.getCount() )
                {
                    value = vm.getNthValue( n );
                }
                else
                {
                    context.showError( "Subscript " + n + " is out of range for " + name + "[] " + vm );
                }
            }
            else
            {
                context.showError( "Subscript " + subvalue + "("+ subscript +") is not an Integer for " + name + "[]. It is a " + subvalue.getClass().getCanonicalName() );
            }
        }
        else
        {
            context.showError( "Cannot find value for " + name + "[]" );
        }
        
        return value==null ? new Integer(0) : value;
    }
    
    public String toString()
    {
        return name + "[" + subscript + "]";
    }
}
