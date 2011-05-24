package org.vanb.viva.utils;

import java.io.*;

/**
 * Control the input: get lines, tokenize them, report simple formatting errors.
 * 
 * @author David Van Brackle
 */
public class InputManager
{
    RandomAccessFile reader;
    boolean eof = false, eoln = false;
    long pos;
    int lineno, tokenno;
   
    /**
     * Create an input controller for the specified file.
     * 
     * @param filename Name of the file
     */
    public InputManager( String filename, VIVAContext context ) throws Exception
    {
        reader = new RandomAccessFile( filename, "r" );    
        pos = 0L;
        lineno = 1;
        tokenno = 0;
    }
    
    private int nextch() throws IOException
    {
        int c = reader.read();
        
        // Check for EOF
        if( c<0 )
        {
            eof = true;
        }
        else
        {
            String crlf = "";
            // Check for EOLN
            if( c=='\r' )
            {
                crlf += '\r';
                eoln = true;
                c = reader.read();
                if( c!='\n' )
                {
                    reader.seek( reader.getFilePointer()-1 );
                }
                else
                {
                    crlf += '\n';
                }
                c = -2;
            }
            else if( c=='\n' )
            {
                crlf += '\n';
                eoln = true;
                c = -2;   
            }
            
            if( crlf.length()>0 && !crlf.equals( System.getProperty("line.separator") ) )
            {
                System.err.println( "Bad Separator" );
            }
        }
        return c;
    }
    
    /**
     * Get the next token on the current line.
     * 
     * @param context Context
     * @return The next token
     * @throws Exception
     */
    public String getNextToken( VIVAContext context ) throws Exception
    {
        int c = nextch();
        String token = "";
                
        if( c==' ' )
        {
            context.err.println( "Extra blank(s) on line " + lineno );
            while( c==' ' )
            {
                c = nextch();
            }
        }
        
        if( eof )
        {
            throw new Exception( "Unexpected EOF encountered on line " + lineno );
        }
        else if( eoln )
        {
            throw new Exception( "Unexpected EOLN encountered on line " + lineno );
        }
        else
        {
            ++tokenno;
            while( c!=' ' && !eof && !eoln )
            {
                token += (char)c;
                c = nextch();
            }
        }
                
        return token;
    }
    
    /**
     * Reset the current line back to the beginning.
     */
    public void resetLine()
    {
        tokenno = 0;
        try
        {
            reader.seek( pos );
        }
        catch( IOException ioe )
        {
            
        }
    }
    
    public boolean atEOF()
    {
        return eof;
    }
    
    public boolean atEOLN()
    {
        return eoln;
    }
    
    /**
     * Read the next line, and perform formatting checks.
     * @param context TODO
     */
    public void getNextLine( VIVAContext context ) throws Exception
    {
        boolean blanks = false;
        boolean tokens = false;
        
        if( !eof )
        {
            if( !eoln )
            {
                reader.seek( reader.getFilePointer()-1 );
                for(;;)
                {
                    int c = nextch();
                    if( c==' ' ) blanks = true;
                    else if( c>=0 ) tokens = true;
                    else break;
                }
                
                if( blanks ) context.err.println( "Extra blanks at the end of line " + lineno );
                if( tokens ) context.err.println( "Extra tokens at the end of line " + lineno );
            }        
            lineno++;
            tokenno = 0;
            pos = reader.getFilePointer();
            eoln = false;
        }
    }
}
