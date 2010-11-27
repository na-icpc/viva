/**
 * A generic, hierarchical symbol table.
 */
package org.vanb.viva.utils;

import java.util.*;

/**
 * A generic, hierarchical symbol table.
 * The symbol table is implemented as a stack of Hashtables.
 * 
 * @author vanb
 */
public class SymbolTable<T>
{
    private LinkedList<Hashtable<String,T>> tables;
    
    /**
     * Create a generic SymbolTable.
     */
    public SymbolTable()
    {
        tables = new LinkedList<Hashtable<String,T>>();
        tables.add( new Hashtable<String,T>() );
    }
    
    /**
     * Add a hierarchical level.
     */
    public void addLevel()
    {
        tables.addFirst( new Hashtable<String,T>() );
    }
    
    /**
     * Pop a hierarchical level. Be sure to leave at least one Hashtable in the stack.
     */
    public void removeLevel()
    {
        if( tables.size()>1 )
        {
            tables.removeFirst();
        }
    }
    
    /**
     * Look up a symbol. Go through the Hashtables, starting with the most recent.
     * 
     * @param name  Symbol to look up
     * @return Value, null if not found
     */
    public T lookup( String name )
    {
        T result = null;
        
        for( Hashtable<String,T> table : tables )
        {
            result = table.get( name );
            if( result!=null ) break;
        }
        
        return result;
    }
    
    /**
     * Add a symbol & value to the Symbol Table. Only add to the most recent context.
     * Return true if this
     * 
     * @param key Name of symbol to add
     * @param value Value of symbol to add
     * @return true if this is a new symbol, false if the symbol is already there.
     */
    public boolean add( String key, T value )
    {
        Hashtable<String,T> top = tables.peekFirst();
        T result = top.get(  key  );
        top.put( key, value );
        return result==null;
    }
}
