package org.vanb.viva.utils;

import java.io.*;

public class VIVAContext
{
    public PrintStream err;
    public InputManager input;
    public SymbolTable<ValueManager> values;
    
    public VIVAContext( PrintStream e, String filename )
    {
        err = e;
        values = new SymbolTable<ValueManager>();
        try
        {
            input = new InputManager( filename, this );
        }
        catch( Exception x )
        {
            
        }
    }
}
