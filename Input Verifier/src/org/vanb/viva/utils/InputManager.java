package org.vanb.viva.utils;

import java.io.*;
import java.util.*;

import org.vanb.viva.parameters.Parameter;

/**
 * Control the input: get lines, tokenize them, report simple formatting errors.
 * 
 * @author David Van Brackle
 */
public class InputManager
{
    private class State
    {
        long pos = 0L;
        boolean eof = false, eoln = false, lastfixed = false;
        int lineno = 1, tokenno = 0;
        
        public void set( State s )
        {
            pos = s.pos;
            eof = s.eof;
            eoln = s.eoln;
            lastfixed = s.lastfixed;
            lineno = s.lineno;
            tokenno = s.tokenno;
        }
        
        public String toString()
        {
            return "{lineno=" + lineno + " tokenno=" + tokenno 
                + " pos=" + pos + " eof=" + eof + " eoln=" + eoln + " lastfixed=" + lastfixed + "}";
        }
    }
    
    State state = new State();
    LinkedList<State> anchors = new LinkedList<State>();
    VIVAContext context;
    
    private RandomAccessFile reader;
   
    /**
     * Create an input controller for the specified file.
     * 
     * @param filename Name of the file
     */
    public InputManager( String filename, VIVAContext c ) throws Exception
    {
        reader = new RandomAccessFile( filename, "r" );    
        context = c;
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
    private int nextch() throws Exception
    {
        int c = reader.read();
        
        // Check for EOF
        if( c<0 )
        {
            state.eof = true;
        }
        else
        {
            String crlf = "";
            
            if( c>127 )
            {
                context.showError( "Non-ASCII character (" + c + ")" );
            }
            
            // Check for EOLN
            // Will recognize three EOLN markers: \r, \n, or \r\n.
            if( c=='\r' )
            {
                crlf += '\r';
                state.eoln = true;
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
                state.eoln = true;
                c = -2;   
            }
            
            // Check to make sure that the EOLN characters are what we expect on this platform.
            if( crlf.length()>0 && !crlf.equals( context.lineSeparator ) )
            {
                String prettycrlf = crlf.replace( "\r", "\\r" ).replace( "\n", "\\n" );
                String prettylinesep = context.lineSeparator.replace( "\r", "\\r" ).replace( "\n", "\\n" );
                context.showError( "Bad line separator. Expecting " + prettylinesep + ", got " + prettycrlf );
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
    public String getNextToken() throws Exception
    {
        int c=0; 
        if( !state.eoln && !state.eof )
        {
            c = nextch();
        }
        String token = "";
        
        boolean ignoreblanks = Parameter.isTrue( context.getParameter( "ignoreblanks" ) );
        boolean ignoreeoln = Parameter.isTrue( context.getParameter( "ignoreeoln" ) );
                 
        // The character pointer should be right at the beginning of the token.
        // If we see blanks, then they're extra (bad) blanks.
        if( c==' ' )
        {
            if( !ignoreblanks ) context.showError( "Extra blank(s)" );
            while( c==' ' ) c = nextch();
        }
                
        if( ignoreeoln )
        {
            if( state.eoln && !state.eof ) 
            {
                getNextLine();
                c = nextch();
                if( c==' ' )
                {
                    if( !ignoreblanks ) context.showError( "Extra blank(s)" );
                    while( c==' ' ) c = nextch();
                }
            }
            
            if( state.eoln )
            {
                context.showError( "Blank Line(s)" );
                while( state.eoln && !state.eof ) 
                {
                    getNextLine();
                    c = nextch();
                    if( c==' ' )
                    {
                        if( !ignoreblanks ) context.showError( "Extra blank(s)" );
                        while( c==' ' ) c = nextch();
                    }
                }                
            }
        }
        
        if( state.eof )
        {
            context.throwException( "Unexpected EOF encountered" );
            token = null;
        }
        else if( state.eoln )
        {
            context.throwException( "Unexpected EOLN encountered" );
            token = null;
        }
        else
        {
            state.tokenno++;
            while( c!=' ' && !state.eof && !state.eoln )
            {
                token += (char)c;
                c = nextch();
            }
        }
           
        state.lastfixed = false;
        return token;
    }
    
    public String getToEOLN() throws Exception
    {
        String result = "";
        while( !state.eoln && !state.eof )
        {
            int c = nextch();
            if( state.eoln || state.eof ) break;
            result += (char)c;
        }
        
        state.tokenno++;
        state.lastfixed = false;
        return result;
    }
    
    public String getFixedField( int width ) throws Exception
    {
        String result = "";
                
        for( int i=0; i<width; i++ )
        {
            int c = nextch();
            
            if( state.eoln || state.eof  )
            {
                context.showError( "Encountered " + (state.eoln ? "EOLN" : "EOF") 
                        + " after reading only " + i + " chars of a field of width " + width );
                break;
            }
            
            result += (char)c;
        }
           
        state.tokenno++;
        state.lastfixed = true;
        return result;
    }
          
    public void dropAnchor() throws VIVAException
    {
        State anchor = new State();
        anchor.set( state );
        try
        {
            anchor.pos = reader.getFilePointer();
        }
        catch( IOException ioe )
        {
            context.throwException( "Couldn't drop anchor " + anchor + ": " + ioe.getMessage() );
        }
        anchors.addFirst( anchor );
    }
    
    public void returnToAnchor() throws VIVAException
    {
        try
        {
            State anchor = anchors.getFirst();
            state.set( anchor );
            reader.seek( state.pos );
        }
        catch( IOException ioe )
        {
            context.throwException( "Couldn't return to anchor: " + ioe.getMessage() );            
        }
    }
    
    public void raiseAnchor() throws VIVAException
    {
        if( anchors.size()==0 )
        {
            context.throwException( "There are no anchors to raise." );
        }
        else
        {
            anchors.removeFirst();
        }
    }
        
    public boolean atEOF()
    {
        return state.eof;
    }
    
    public boolean atEOLN()
    {
        return state.eoln;
    }
    
    /**
     * Read the next line, and perform formatting checks.
     * @param context TODO
     */
    public void getNextLine() throws Exception
    {
        boolean blanks = false;
        boolean tokens = false;
        boolean ignoreblanks = Parameter.isTrue( context.getParameter( "ignoreblanks" ) );

        if( !state.eof && !state.eoln && state.lastfixed )
        {
            nextch();
        }
        
        if( !state.eof )
        {
            if( !state.eoln )
            {
                backch();
                for(;;)
                {
                    int c = nextch();
                    if( c==' ' ) blanks = true;
                    else if( c>=0 ) tokens = true;
                    else break;
                }
                
                if( blanks && !ignoreblanks ) context.showError( "Extra blank(s) at the end of line"  );
                if( tokens ) context.showError( "Extra token(s) at the end of line" );
            }        
            state.lineno++;
            state.tokenno = 0;
            state.eoln = false;
            state.lastfixed = false;
        }
    }
    
    /**
     * Check to see if there are any blank lines, extra spaces, or extra characters after all of the input has been parsed.
     * 
     * @param context
     * @throws Exception
     */
    public void eofChecks() throws Exception
    {
        if( !state.eof && state.lastfixed )
        {
            nextch();
        }
        
        if( !state.eof )
        {
            String message = "";
            
            if( state.eoln ) 
            {
                message = "Blank line at end of input. ";
            }
            else
            {
                backch();
            }
            
            int c = nextch();                    
            if( c==' ' )
            {
                if( c==' ' ) message = "Extra blank(s) at end of file";
                while( c==' ' )
                {
                    c = nextch();
                }
            }

            if( !state.eof ) message = "Extra characters after input.";
            
            context.err.println( message );
        }
    }
    
    public int getLine()
    {
        return state.lineno;
    }
    
    public int getToken()
    {
        return state.tokenno;
    }
}
