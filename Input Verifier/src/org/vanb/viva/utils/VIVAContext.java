package org.vanb.viva.utils;

import java.io.*;
import java.util.*;

import org.vanb.viva.*;
import org.vanb.viva.expressions.*;

public class VIVAContext
{
    public PrintStream err = System.out;
    public InputManager input;
    public SymbolTable<ValueManager> values;
    public int errcount, maxerrs;
    public boolean justTesting = false;
    public HashMap<String,Function> functions = new HashMap<String,Function>();
    public double deps = 0.000001;
    public float feps = 0.000001F;
    public int index = -1;
    public String lineSeparator = System.getProperty( "line.separator" );  
    
    public VIVAContext()
    {
        values = new SymbolTable<ValueManager>();
        errcount = 0;
        maxerrs = 25;
    }
 
    public void throwException( String message ) throws VIVAException
    {
        if( !justTesting )
        {
            throw new VIVAException( "At line " + input.getLine() + " token " + input.getToken() + ": " + message, 
                    input.getLine(), input.getToken() );
        }
    }
    
    public void showError( String message ) throws VIVAException
    {
        if( !justTesting )
        {
            err.println( "At line " + input.getLine() + " token " + input.getToken() + ": " + message );
            ++errcount;
            if( errcount>=maxerrs )
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
