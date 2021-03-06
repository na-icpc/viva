/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Control the input: get lines, tokenize them, report simple formatting errors.
 * 
 * @author David Van Brackle
 */
public class InputManager
{
    /**
     * This Class records the state of the input.
     * There are cases where we need to go back to a previous state.
     * This Class is our mechanism.
     * 
     * @author vanb
     */
    private class State
    {
        
        /**  Position in the input file. */
        protected int pos = 0;
        
        /**  Position to backsh() to. */
        protected int lastPos;
        
        /** State of EOF, EOLN, and whether the last variable was a fixed-sized field. */
        protected boolean eof = false, eoln = false, lastfixed = false;
        
        /** Line number, token number. */
        protected int lineno = 1, tokenno = 0;
        
        /**
         * Set the values of this State to be the same as another State.
         * 
         * @param s Another State
         */
        public void set( State s )
        {
            pos = s.pos;
            lastPos = s.lastPos;
            eof = s.eof;
            eoln = s.eoln;
            lastfixed = s.lastfixed;
            lineno = s.lineno;
            tokenno = s.tokenno;
        }
        
        /**
         * Create a pretty String for debugging.
         * 
         * @return Pretty String
         */
        @Override
        public String toString()
        {
            return "{lineno=" + lineno + " tokenno=" + tokenno 
                + " pos=" + pos + " eof=" + eof + " eoln=" + eoln + " lastfixed=" + lastfixed + "}";
        }
    }
    
    /**  Current state. */
    private State state = new State();
    
    /** A list of 'anchors' - States we can go back to. */
    private Deque<State> anchors = new ArrayDeque<State>();
    
    /**  Context. */
    private VIVAContext context;
    
    /**  The current input file being tested. */
    private MappedByteBuffer map;
    
    /** Last position in the file. */
    private int lastPos;
   
    /**
     * Create an input controller for the specified file.
     *
     * @param filename Name of the file
     * @param c the c
     * @throws Exception the exception
     */
    public InputManager( String filename, VIVAContext c ) throws Exception
    {
        RandomAccessFile reader = new RandomAccessFile( filename, "r" ); 
        map = reader.getChannel().map( MapMode.READ_ONLY, 0L, reader.length() );
        map.load();
        reader.close();
        context = c;
        lastPos = 0;
    }
    
    /**
     * Put one character back on the input.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void backch() throws IOException
    {
        map.position( lastPos );
    }
    
    /**
     * Read a character, returning -1 on EOF and -2 on EOL. 
     * Also set the "eof" and "eoln" flags.
     *
     * @return The input character, or -1 for eof, or -2 for eoln.
     * @throws Exception the exception
     */
    private int nextch() throws Exception
    {
        int c = 0;
        String crlf = "";
        lastPos = map.position();
        try
        {
            c = map.get() & 0xff;           
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
                c = map.get() & 0xff;

                if( c=='\n' )
                {
                    crlf += '\n';
                }
                else
                {
                    // Go back before the \r
                    backch();
                    // re-read the \r
                    map.get();
                }
                c = -2;
            }
            else if( c=='\n' )
            {
                crlf += '\n';
                state.eoln = true;
                c = -2;   
            }
        }   
        catch( BufferUnderflowException bue )
        {
            state.eof = true;
            c = -1;
        }
        
        // Check to make sure that the EOLN characters are what we expect on this platform.
        if( crlf.length()>0 && !crlf.equals( context.lineSeparator ) )
        {
            String prettycrlf = crlf.replace( "\r", "\\r" ).replace( "\n", "\\n" );
            String prettylinesep = context.lineSeparator.replace( "\r", "\\r" ).replace( "\n", "\\n" );
            context.showError( "Bad line separator. Expecting " + prettylinesep + ", got " + prettycrlf );
        }

        return c;
    }
        
    /**  We'll build tokens in here. */
    private StringBuilder builder = new StringBuilder();
    
    /**
     * Get the next token on the current line.
     *
     * @return The next token
     * @throws Exception the exception
     */
    public String getNextToken() throws Exception
    {
        // Start afresh
        builder.setLength( 0 );
        String token = null;
        
        // Move past a single blank
        int c=0; 
        if( !state.eoln && !state.eof )
        {
            c = nextch();
        }
                         
        // The character pointer should be right at the beginning of the token.
        // If we see blanks, then they're extra (bad) blanks.
        if( c==' ' )
        {
            if( !context.ignoreBlanks ) context.showError( "Extra blank(s)" );
            while( c==' ' ) c = nextch();
        }
                
        if( context.ignoreEOLN )
        {
            if( state.eoln && !state.eof ) 
            {
                getNextLine();
                c = nextch();
                if( c==' ' )
                {
                    if( !context.ignoreBlanks ) context.showError( "Extra blank(s)" );
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
                        if( !context.ignoreBlanks ) context.showError( "Extra blank(s)" );
                        while( c==' ' ) c = nextch();
                    }
                }                
            }
        }
        
        if( state.eof )
        {
            context.showError( "Unexpected EOF encountered" );
            token = null;
        }
        else if( state.eoln )
        {
            context.showError( "Unexpected EOLN encountered" );
            token = null;
        }
        else
        {
            state.tokenno++;
            while( c!=' ' && !state.eof && !state.eoln )
            {
                builder.append( (char)c );
                c = nextch();
            }
            token = builder.toString();
        }
           
        state.lastfixed = false;
        return token;
    }
    
    /**
     * Get all text to EOLN.
     *
     * @return Text on the current line up to EOLN
     * @throws Exception the exception
     */
    public String getToEOLN() throws Exception
    {
        builder.setLength( 0 );
        while( !state.eoln && !state.eof )
        {
            int c = nextch();
            if( state.eoln || state.eof ) break;
            builder.append( (char)c );
        }
        
        state.tokenno++;
        state.lastfixed = false;
        return builder.toString();
    }
    
    /**
     * Get the text in a fixed-width field.
     *
     * @param width Width of the field
     * @return Text in the current line of the given width
     * @throws Exception the exception
     */
    public String getFixedField( int width ) throws Exception
    {
        builder.setLength( 0 );
        for( int i=0; i<width; i++ )
        {
            int c = nextch();
            
            if( state.eoln || state.eof  )
            {
                context.showError( "Encountered " + (state.eoln ? "EOLN" : "EOF") 
                        + " after reading only " + i + " chars of a field of width " + width );
                break;
            }
            
            builder.append( (char)c );
        }
           
        state.tokenno++;
        state.lastfixed = true;
        return builder.toString();
    }
          
    /**
     * Drop an anchor, which means remembering the State at this place
     * so we can easily go back to it.
     *
     * @throws VIVAException the VIVA exception
     */
    public void dropAnchor() throws VIVAException
    {
        State anchor = new State();
        anchor.set( state );
        anchor.pos = map.position();
        anchor.lastPos = lastPos;
        anchors.addLast( anchor );
    }
    
    /**
     * Return to the last recored 'anchor' State.
     *
     * @throws VIVAException the VIVA exception
     */
    public void returnToAnchor() throws VIVAException
    {
        State anchor = anchors.getLast();
        state.set( anchor );
        map.position( state.pos );
        lastPos = state.lastPos;
    }
    
    /**
     * Get rid of the current 'anchor' State.
     *
     * @throws VIVAException the VIVA exception
     */
    public void raiseAnchor() throws VIVAException
    {
        if( anchors.size()==0 )
        {
            context.throwException( "There are no anchors to raise." );
        }
        else
        {
            anchors.removeLast();
        }
    }
        
    /**
     * Are we at EOF?
     * This is a little tricky. If we're not really at EOF, but there's nothing but blank 
     * lines from here to EOF, then we've got to say we're at EOF.
     * 
     * @return true if at EOF, otherwise false
     */
    public boolean atEOF()
    {
        boolean ateof = state.eof;
        
        try
        {
            // Keep reading until we encounter either EOF or a non-blank line
            nextch();
            while( state.eoln && !state.eof ) nextch();
            ateof = state.eof;
        }
        catch( Exception e )
        {
            // If anything went wrong, the there's no more file to read.
            ateof = true;
        }   
        
        return ateof;
    }
    
    /**
     * Are we at EOLN?.
     *
     * @return true if at EOLN, otherwise false
     */
    public boolean atEOLN()
    {
        boolean ateoln = state.eoln;
        try
        {
            nextch();
            ateoln = state.eoln;
            backch();
        }
        catch( Exception e )
        {
            ateoln = true;
        }
        return ateoln;
    }
    
    /**
     * Read the next line, and perform formatting checks.
     *
     * @return the next line
     * @throws Exception the exception
     */
    public void getNextLine() throws Exception
    {
        boolean blanks = false;
        boolean tokens = false;

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
                
                if( blanks && !context.ignoreBlanks ) context.showError( "Extra blank(s) at the end of line"  );
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
     * @throws Exception the exception
     */
    public void eofChecks() throws Exception
    {
        if( !state.eof && state.lastfixed )
        {
            nextch();
        }
        
        // Linux prefers a blank line at EOF.
        if( state.eof && !context.allowWindowsEOF )
        {
            context.showError( "No EOLN after last line, which Linux prefers." );
            state.eof = true;            
        }
        
        // Allow one blank line if Linux
        if( !state.eof && context.allowLinuxEOF )
        {
            int c = nextch();
            if( c>=0 ) 
            {
                context.showError( "Extra characters after input." );
                state.eof = true;
            }
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
            
            context.showError( message );
        }
    }
    
    /**
     * Get the current line number in the input file.
     * 
     * @return Current line number
     */
    public int getLine()
    {
        return state.lineno;
    }
    
    /**
     * Get the current token number in the current line.
     * 
     * @return Current token number
     */
    public int getToken()
    {
        return state.tokenno;
    }
    
    /**
     * Close.
     */
    public void close()
    {
        map = null;
        System.gc();
    }
}
