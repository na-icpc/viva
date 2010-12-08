package org.vanb.viva.utils;

import java.io.*;

public class VIVAContext
{
    public PrintStream err = System.out;
    public InputManager input;
    public SymbolTable<ValueManager> values;
    public int errcount, maxerrs;
    public boolean justTesting = false;
    
    public VIVAContext()
    {
        values = new SymbolTable<ValueManager>();
        errcount = 0;
        maxerrs = 25;
    }
    
    public void throwException( String message ) throws VIVAException
    {
        throw new VIVAException( "At line " + input.lineno + " token " + input.tokenno + ": " + message, 
                input.lineno, input.tokenno );
    }
    
    public void showError( String message ) throws VIVAException
    {
        if( !justTesting )
        {
            err.println( "At line " + input.lineno + " token " + input.tokenno + ": " + message );
            ++errcount;
            if( errcount>=maxerrs )
            {
                throwException( "Too many errors. Exiting." );
            }
        }
    }
}