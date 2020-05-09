/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

/**
 * The Class SubscriptNode.
 */
public class SubscriptNode extends VariableNode
{
    
    /** The subscript. */
    private ExpressionNode subscript;
    
    /**
     * Instantiates a new subscript node.
     *
     * @param name the name
     * @param type the type
     * @param sub the sub
     */
    public SubscriptNode( String name, Class<?> type, ExpressionNode sub )
    {
        super( name, type, true );
        subscript = sub;
    }
    
    /**
     * Evaluate.
     *
     * @param context the context
     * @return the object
     * @throws VIVAException the VIVA exception
     */
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
                    value = vm.getNth( n, true );
                }
                else
                {
                    context.throwException( "Subscript " + n + " is out of range for " + name + "[] " + vm );
                }
            }
            else
            {
                context.throwException( "Subscript " + subvalue + "("+ subscript +") is not an Integer for " + name + "[]. It is a " + subvalue.getClass().getSimpleName() );
            }
        }
        else
        {
            context.throwException( "Cannot find value for " + name + "[]" );
        }
        
        return value==null ? new Integer(0) : value;
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return name + "[" + subscript + "]";
    }
}
