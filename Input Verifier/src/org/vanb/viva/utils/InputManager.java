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
    char eolchars[] = "\r\n".toCharArray();
    int eolcount;
    boolean eof = false;
   
    /**
     * Create an input controller for the specified file.
     * 
     * @param filename Name of the file
     */
    public InputManager( String filename, VIVAContext context ) throws Exception
    {
        reader = new FileReader( filename );    
        lineno = tokenno = 0;
        tokens = new String[0];
        ch = ' ';
        getNextLine( context, false );
    }
    
    /**
     * Get the next token on the current line.
     * @param context TODO
     * 
     * @return The next token
     * @throws Exception
     */
    public String getNextToken( VIVAContext context ) throws Exception
    {
        if( eof )
        {
            throw new Exception( "EOF encountered on line " + lineno );
        }
        if( tokenno >= tokens.length )
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
    
    public boolean atEOF()
    {
        return eof;
    }
    
    /**
     * Read the next line, and perform formatting checks.
     * @param context TODO
     */
    public void getNextLine( VIVAContext context, boolean expectingEOF ) throws Exception
    {
        boolean previousblank = Character.isWhitespace( ch );
        StringBuilder sb = new StringBuilder();
        eolcount = 0;
        boolean blankerror = false;
        
        if( tokenno != tokens.length )
        {
            context.err.println( "Unused tokens on line " + lineno );
        }
        
        boolean isblankline = false;
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
                    eof = true;
                    break;
                }
                
                // Convert to char
                ch = (char) c;
                
                // Check for EOLN
                if( ch==eolchars[eolcount] ) 
                {
                    ++eolcount; 
                    if( eolcount==eolchars.length )
                    {
                        if( previousblank && sb.length()>0 )
                        {
                            context.err.println( "Blank(s) at the end of line " + lineno );
                        }
                        break;
                    }
                }
                else 
                {
                    eolcount=0;
                    boolean isblank = ch==' ';
                    
                    // Check for duplicate blanks
                    if( isblank && previousblank && !blankerror )
                    {
                        blankerror = true;
                        context.err.println( "Extra blank(s) on line " + lineno );
                    }
                    
                    // Check for spurious chars
                    else if( !isblank && (Character.isWhitespace( ch ) || Character.isISOControl( ch )) )
                    {
                        context.err.println( "Spurious character (" + ch + ") on line " + lineno );   
                    }
                    else
                    {
                        sb.append( ch );
                    }
                    
                    previousblank = isblank;
                } 
            }  
            
            tokens = sb.toString().trim().split( "\\s+" );
            isblankline = tokens.length==0 || (tokens.length==1 && tokens[0].length()==0);
            if( !expectingEOF && isblankline )
            {
                context.err.println( "Blank line encountered on line " + lineno );
            }
        } while( !eof && isblankline && !expectingEOF );
        tokenno = 0;
    }
}
