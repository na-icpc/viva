/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of the values and tokens for 
 * something being read from an input file. The "token" is the
 * String that's actually in the input - so, many different tokens
 * (e.g. "1.0", "1.00", "1.", "1e0") might all have the same value.
 * 
 * @author vanb
 */
public class ValueManager
{
    /** List of input values */
    private List<Object> values;
    
    /** List of input tokens (which got parsed to the values) */
    private List<String> tokens;
    
    /** The current value */
    private Object currentValue;
    
    /** The current token */
    private String currentToken;
    
    /** The number of values/tokens */
    private int count;
    
    /**
     * Create a ValueManager
     */
    public ValueManager()
    {
        values = new ArrayList<Object>(1000000);
        tokens = new ArrayList<String>(1000000);
        currentValue = null;
        currentToken = null;
        count = 0;
    }
    
    /**
     * Add a value.
     * 
     * @param value The Value
     * @param token The actual Token on the input
     */
    public void addValue( Object value, String token )
    {
        values.add( value );
        tokens.add( token );
        currentValue = value;
        currentToken = token;
        ++count;
    }
    
    /**
     * Get the current Value or Token.
     * 
     * @param wantValue true if the value is desired, false if the Token is wanted.
     * @return The desired Value or Token.
     */
    public Object getCurrent( boolean wantValue )
    {
        return wantValue ? currentValue : currentToken;
    }
        
    /**
     * Get the nth Value or Token from the input.
     * 
     * @param n Desired index
     * @param wantValue true if the value is desired, false if the Token is wanted.
     * @return The desired value or Token, or null if n is out of range.
     */
    public Object getNth( int n, boolean wantValue )
    {
        Object result = null;
        if( n>=0 && n<count )
        {
            result = wantValue ? values.get( n ) : tokens.get( n );   
        }
        
        return result;
    }
        
    /**
     * The total number of values read in for this variable so far.
     * 
     * @return That number.
     */
    public int getCount()
    {
        return count;
    }
    
    /**
     * Return a description of this ValueManager as a debugging tool.
     * 
     * @return A description of this ValueManager.
     */
    @Override
    public String toString()
    {
        return "ValueManager:" + values + " " + currentValue + " " + count;
    }
}
