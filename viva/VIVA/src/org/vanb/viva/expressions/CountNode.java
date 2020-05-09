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
 * The Class CountNode.
 */
public class CountNode extends VariableNode
{
    
    /**
     * Instantiates a new count node.
     *
     * @param name the name
     */
    public CountNode( String name )
    {
        super( name, Integer.class, true );
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return name + "#";
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
        Integer value = null;
        
        if( vm!=null )
        {
            value = new Integer( vm.getCount() );      
        }
        else
        {
            context.throwException( "Cannot find count for " + name );
        }
        
        return value;
    }


}
