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
            value = vm.getCurrentValue();      
        }
        else
        {
            context.showError( "Cannot find value for " + name + ", using 0" );
            new Exception().printStackTrace();
            vm = new ValueManager();
            value = new Integer( 0 );
            vm.addValue( value );
            context.values.add( name, vm );
        }
        return value;
    }

    @Override
    public Class<?> returnType()
    {
        return type;
    }
    
    public String toString()
    {
        return name;
    }

}
