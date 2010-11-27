package org.vanb.viva.utils;

import java.io.*;
import java.util.*;

/**
 * Control the input: get lines, tokenize them, report simple formatting errors.
 * 
 * @author David Van Brackle
 */
public class InputManager
{
    FileReader reader;
    int lineno, tokenno;
    String tokens[];
    char ch;
    char eolchars[] = "\n".toCharArray();
    int eolcount;
   
    /**
     * Create an input controller for the specified file.
     * 
     * @param filename Name of the file
     */
    public InputManager( String filename ) throws Exception
    {
        reader = new FileReader( filename );    
        lineno = tokenno = 0;
        tokens = new String[0];
        ch = ' ';
    }
    
    /**
     * Get the next token on the current line.
     * 
     * @return The next token
     * @throws Exception
     */
    public String getNextToken() throws Exception
    {
        if( tokenno >=tokens.length )
        {
            throw new Exception( "Too few tokens on line " + lineno );   
        }
        
        return tokens[tokenno++];
    }
    
    /**
     * Reset the current line back to the beginning.
     */
    public void resetLine()
    {
        tokenno = 0;
    }
    
    /**
     * Read the next line, and perform formatting checks.
     */
    public void getNextLine() throws Exception
    {
        boolean previousblank = Character.isWhitespace( ch );
        StringBuilder sb = new StringBuilder();
        eolcount = 0;
        
        do
        {
            ++lineno;
            int eolcount = 0;
            for(;;)
            {
                int c = reader.read();
                
                // Check for EOF
                if( c<0 )
                {
                   throw new Exception( "EOF Encountered on line " + lineno );  
                }
                
                // Convert to char
                ch = (char) c;
                
                // Check for EOLN
                if( ch==eolchars[eolcount] ) 
                {
                    ++eolcount; 
                    if( eolcount==eolchars.length ) break;
                }
                else 
                {
                    eolcount=0;
                    boolean isblank = ch==' ';
                    
                    // Check for duplicate blanks
                    if( isblank && previousblank )
                    {
                        System.err.println( "Extra blanks encountered on line " + lineno );
                    }
                    
                    // Check for spurious chars
                    else if( Character.isWhitespace( ch ) || Character.isISOControl( ch ) )
                    {
                        System.err.println( "Spurious character encountered on line " + lineno);   
                    }
                    else
                    {
                        sb.append( ch );
                    }
                } 
            }  
            tokens = sb.toString().trim().split( "\\s+" );
            if( tokens.length==0 )
            {
                System.err.println( "Blank line encountered on line " + lineno );
            }
        } while( tokens.length==0 );
        tokenno = 0;
    }
}
