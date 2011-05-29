package org.vanb.viva.utils;

import java.util.*;

public class ValueManager
{
    private Vector<Object> values;
    private Vector<String> tokens;
    private Object currentValue;
    private String currentToken;
    private int count;
    
    public ValueManager()
    {
        values = new Vector<Object>();
        tokens = new Vector<String>();
        currentValue = null;
        currentToken = null;
        count = 0;
    }
    
    public void addValue( Object value, String token )
    {
        values.add( value );
        tokens.add( token );
        currentValue = value;
        currentToken = token;
        ++count;
    }
    
    public Object getCurrent( boolean wantValue )
    {
        return wantValue ? currentValue : currentToken;
    }
        
    public Object getNth( int n, boolean wantValue )
    {
        Object result = null;
        if( n>=0 && n<count )
        {
            result = wantValue ? values.get( n ) : tokens.get( n );   
        }
        
        return result;
    }
        
    public int getCount()
    {
        return count;
    }
    
    public String toString()
    {
        return "ValueManager:" + values + " " + currentValue + " " + count;
    }
}
