/**
 * A generic, hierarchical symbol table.
 */
package org.vanb.viva.utils;

import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A generic, hierarchical symbol table.
 * The symbol table is implemented as a stack of HashMaps.
 * 
 * @author vanb
 */
public class SymbolTable<T>
{
    /** A stack of symbol tables */
    private Deque<HashMap<String,T>> tables;
    
    /** A stack of iteration counts */
    private Deque<Integer> counts;
    
    /**
     * Create a generic SymbolTable.
     */
    public SymbolTable()
    {
        // Create the tables stack and add the top level
        tables = new ArrayDeque<HashMap<String,T>>();
        tables.add( new HashMap<String,T>() );
        
        // Ditto with the counts
        counts = new ArrayDeque<Integer>();
        counts.add( 0 );
    }
    
    /**
     * Add a hierarchical level.
     */
    public void addLevel()
    {
        tables.addFirst( new HashMap<String,T>() );
        counts.addFirst( 0 );
    }
    
    /**
     * Pop a hierarchical level. Be sure to leave at least one HashMap in the stack.
     */
    public void removeLevel()
    {
        if( tables.size()>1 )
        {
            tables.removeFirst();
        }
        if( counts.size()>1 )
        {
            counts.removeFirst();
        }
    }
    
    /**
     * Increment the number of iterations at this level.
     */
    public void incrementLevel()
    {
        counts.addFirst( counts.removeFirst() + 1 );
    }
    
    /**
     * Get the count of the number of iterations at this level.
     * @return
     */
    public int getCount()
    {
        return counts.peekFirst();
    }
    
    /**
     * Look up a symbol. Go through the HashMaps, starting with the most recent.
     * 
     * @param name  Symbol to look up
     * @return Value, null if not found
     */
    public T lookup( String name )
    {
        T result = null;
        
        for( HashMap<String,T> table : tables )
        {
            result = table.get( name );
            if( result!=null ) break;
        }
        
        return result;
    }
    
    /**
     * Add a symbol & value to the Symbol Table. Only add to the most recent context.
     * Return true if this is a new symbol, false if the symbol is already there.
     * 
     * @param key Name of symbol to add
     * @param value Value of symbol to add
     * @return true if this is a new symbol, false if the symbol is already there.
     */
    public boolean add( String key, T value )
    {
        HashMap<String,T> top = tables.peekFirst();
        T result = top.get( key );
        top.put( key, value );
        return result==null;
    }
    
    /**
     * Determine if the named variable is defined at the current (innermost) level
     * 
     * @param name variable name
     * @return true if defined at the current level, otherwise false
     */
    public boolean atCurrentLevel( String name )
    {
        return tables.peekFirst().get( name ) != null;
    }
    
    /**
     * Clear the symbol table(s).
     */
    public void clear()
    {
        // Create the tables stack and add the top level
        tables.clear();
        tables.add( new HashMap<String,T>() );
        
        // Ditto with the counts
        counts.clear();
        counts.add( 0 );        
    }
}
