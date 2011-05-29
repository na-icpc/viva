package org.vanb.viva.utils;

import java.io.*;

/**
 * Control the input: get lines, tokenize them, report simple formatting errors.
 * 
 * @author David Van Brackle
 */
public class InputManager
{
    private RandomAccessFile reader;
    private boolean eof = false, eoln = false;
    private long lineStart;
    private boolean firstparse = true;
    int lineno, tokenno;
   
    /**
     * Create an input controller for the specified file.
     * 
     * @param filename Name of the file
     */
    public InputManager( String filename, VIVAContext context ) throws Exception
    {
        reader = new RandomAccessFile( filename, "r" );    
        lineStart = 0L;
        lineno = 1;
        tokenno = 0;
    }
    
    /**
     * Put one character back on the input.
     * 
     * @throws IOException
     */
    private void backch() throws IOException
    {
        reader.seek( reader.getFilePointer()-1 );
    }
    
    /**
     * Read a character, returning -1 on EOF and -2 on EOL. 
     * Also set the "eof" and "eoln" flags.
     * 
     * @param context
     * @return The input character, or -1 for eof, or -2 for eoln.
     * @throws Exception
     */
    private int nextch( VIVAContext context ) throws Exception
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
            // Will recognize three EOLN markers: \r, \n, or \r\n.
            if( c=='\r' )
            {
                crlf += '\r';
                eoln = true;
                c = reader.read();
                if( c=='\n' )
                {
                    crlf += '\n';
                }
                else
                {
                    backch();
                }
                c = -2;
            }
            else if( c=='\n' )
            {
                crlf += '\n';
                eoln = true;
                c = -2;   
            }
            
            // Check to make sure that the EOLN characters are what we expect on this platform.
            if( crlf.length()>0 && !crlf.equals( context.lineSeparator ) && firstparse )
            {
                String prettycrlf = crlf.replace( "\r", "\\r" ).replace( "\n", "\\n" );
                String prettylinesep = context.lineSeparator.replace( "\r", "\\r" ).replace( "\n", "\\n" );
                context.err.println( "Bad line separator on line " + lineno + ". Expecting " + prettylinesep + ", got " + prettycrlf );
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
        int c = nextch( context );
        String token = "";
                 
        // The character pointer should be right at the beginning of the token.
        // If we see blanks, then they're extra (bad) blanks.
        if( c==' ' )
        {
            if( firstparse ) context.err.println( "Extra blank(s) on line " + lineno );
            while( c==' ' ) c = nextch( context );
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
                c = nextch( context );
            }
        }
                
        return token;
    }
    
    /**
     * Reset the current line back to the beginning.
     */
    public void resetLine()
    {       
        try
        {
            reader.seek( lineStart );
            tokenno = 0;
            firstparse = false;
            eoln = false;
            eof = false;
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
                backch();
                for(;;)
                {
                    int c = nextch( context );
                    if( c==' ' ) blanks = true;
                    else if( c>=0 ) tokens = true;
                    else break;
                }
                
                if( blanks  && firstparse ) context.err.println( "Extra blank(s) at the end of line " + lineno );
                if( tokens  && firstparse ) context.err.println( "Extra token(s) at the end of line " + lineno );
            }        
            lineno++;
            tokenno = 0;
            lineStart = reader.getFilePointer();
            eoln = false;
            firstparse = true;
        }
    }
    
    /**
     * Check to see if there are any blank lines, extra spaces, or extra characters after all of the input has been parsed.
     * 
     * @param context
     * @throws Exception
     */
    public void eofChecks( VIVAContext context ) throws Exception
    {
        if( !eof )
        {
            String message = "";
            
            if( eoln ) 
            {
                message = "Blank line at end of input. ";
            }
            else
            {
                backch();
            }
            
            int c = nextch( context );                    
            if( c==' ' )
            {
                if( c==' ' ) message = "Extra blank(s) at end of file";
                while( c==' ' )
                {
                    c = nextch( context );
                }
            }

            if( !eof ) message = "Extra characters after input.";
            
            context.err.println( message );
        }
    }
}
