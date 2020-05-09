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
 * The Class VariableNode.
 */
public class VariableNode implements ExpressionNode
{
    
    /** The name. */
    protected String name;
    
    /** The type. */
    protected Class<?> type;
    
    /** The want value. */
    protected boolean wantValue;
    

    
    /**
     * Instantiates a new variable node.
     *
     * @param name the name
     * @param type the type
     * @param wantValue do we want the value or the image?
     */
    public VariableNode(String name, Class<?> type, boolean wantValue)
    {
        this.name = name;
        this.type = type;
        this.wantValue = wantValue;
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
            if( context.values.atCurrentLevel( name ) && context.index>=0 )
            {
                value = vm.getNth( context.index, wantValue ); 
            }
            else
            {
                value = vm.getCurrent( wantValue );                      
            }
        }
        else
        {
            context.throwException( "Cannot find value for " + name );
        }
        return value;
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public Class<?> getReturnType()
    {
        return type;
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return name + (wantValue?"":"$");
    }

}
