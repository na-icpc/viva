package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

public class VariableNode implements ExpressionNode
{
    protected String name;
    protected Class<?> type;
    protected boolean wantValue;
    
    public VariableNode( String n, Class<?> t, boolean v )
    {
        name = n;
        type = t;
        wantValue = v;
    }
    
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

    @Override
    public Class<?> getReturnType()
    {
        return type;
    }
    
    public String toString()
    {
        return name + (wantValue?"":"$");
    }

}
