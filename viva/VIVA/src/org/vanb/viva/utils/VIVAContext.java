package org.vanb.viva.utils;

import java.io.*;
import java.util.*;

import org.vanb.viva.*;

public class VIVAContext
{
    public PrintStream err = System.out;
    public InputManager input;
    public SymbolTable<ValueManager> values;
    public int errcount;
    public int testLevel = 0;
    public HashMap<String,Function> functions = new HashMap<String,Function>();
    public int index = -1;
    public String lineSeparator = System.getProperty( "line.separator" ); 
    public HashMap<String,Object> parameters = new HashMap<String,Object>();
    
    public VIVAContext()
    {
        values = new SymbolTable<ValueManager>();
        errcount = 0;
    }
    
    public void setParameter( String param, Object value )
    {
        parameters.put( param, value );
    }
    
    public Object getParameter( String param )
    {
        return parameters.get( param );
    }
 
    public void throwException( String message ) throws VIVAException
    {
        if( testLevel==0 )
        {
            throw new VIVAException( "At line " + input.getLine() + " token " + input.getToken() + ": " + message, 
                    input.getLine(), input.getToken() );
        }
    }
    
    public void showError( String message ) throws VIVAException
    {
        if( testLevel==0 )
        {
            err.println( "At line " + input.getLine() + " token " + input.getToken() + ": " + message );
            ++errcount;
            if( errcount>=(Integer)getParameter( "maxerrs" ) )
            {
                throwException( "Too many errors. Exiting." );
            }
        }
    }
    
    public void addFunction( Function function )
    {
        functions.put( function.getName(), function );
    }
}
