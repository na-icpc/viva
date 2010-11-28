package org.vanb.viva.patterns;

import org.vanb.viva.utils.InputManager;
import org.vanb.viva.utils.SymbolTable;
import org.vanb.viva.utils.ValueManager;

public abstract class ValuePattern implements Pattern
{
    private String name;
    
    public void setName( String n )
    {
        name = n;
    }
    
    @Override
    public boolean test( InputManager input, SymbolTable<ValueManager> values )
    {
        boolean success = true;
        String token = "";
        
        try
        {
            token = input.getNextToken();
            Object value = getValue( token );
            ValueManager vm = values.lookup( token );
            if( vm==null )
            {
                vm = new ValueManager();
                values.add( name, vm );
            }
            vm.addValue( value );
        }
        catch( Exception e )
        {
            success = false;
        }
        
        return success;
    }
    
    public abstract Object getValue( String token ) throws Exception;

}
