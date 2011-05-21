package org.vanb.viva.expressions;

import org.vanb.viva.utils.*;

public class VariableNode implements ExpressionNode
{
    protected String name;
    protected Class<?> type;
    
    public VariableNode( String n, Class<?> t )
    {
        name = n;
        type = t;
    }
    
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        ValueManager vm = context.values.lookup( name );
        Object value;
        
        if( vm!=null )
        {
            if( context.values.atCurrentLevel( name ) && context.index>=0 )
            {
                value = vm.getNthValue( context.index ); 
            }
            else
            {
                value = vm.getCurrentValue();                      
            }
        }
        else
        {
            context.showError( "Cannot find value for " + name + ", using 0" );
            value = new Integer( 0 );
        }
        return value;
    }

    @Override
    public Class<?> getReturnType()
    {
        return type;
    }
    
    public String toString()
    {
        return name;
    }

}
