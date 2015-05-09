package org.vanb.viva.expressions;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;
import org.vanb.viva.utils.ValueManager;

public class CountNode extends VariableNode
{
    public CountNode( String n )
    {
        super( n, Integer.class, true );
    }
    
    public String toString()
    {
        return name + "#";
    }
        
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
