package org.vanb.viva.utils;

import java.util.*;

public class ValueManager
{
    private Vector<Object> values;
    private Object current;
    private int count;
    
    public ValueManager()
    {
        values = new Vector<Object>();
        current = null;
        count = 0;
    }
    
    public void addValue( Object v )
    {
        values.add( v );
        current = v;
        ++count;
    }
    
    public Object getCurrentValue()
    {
        return current;
    }
    
    public Object getNthValue( int n )
    {
        Object result = null;
        if( n>=0 && n<count )
        {
            result = values.get( n );   
        }
        
        return result;
    }
    
    public int getCount()
    {
        return count;
    }
    
    public String toString()
    {
        return "ValueManager:" + values + " " + current + " " + count;
    }
}
