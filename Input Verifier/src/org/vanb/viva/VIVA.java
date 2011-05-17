package org.vanb.viva;

import org.vanb.viva.parser.*;
import org.vanb.viva.patterns.*;
import org.vanb.viva.utils.*;
import org.vanb.viva.functions.*;

import java.io.*;

public class VIVA
{    
    
    private Pattern pattern = null;
    private VIVAContext context = new VIVAContext();
    
    public void setPattern( InputStream stream )
    {
        try
        {
            PatternParser parser = new PatternParser( stream );
            pattern = parser.multilinePattern();            
        }
        catch( Exception e )
        {
            pattern = null;
            context.err.println( "Pattern parsing failed: " + e.getMessage() );
        }
    }
    
    public void setOutputStream( PrintStream ps )
    {
        context.err = ps;
    }
    
    public boolean testInputFile( String filename )
    {
        boolean result = true;
        if( pattern==null )
        {
            context.err.println( "Can't test the input file: No Pattern successfully parsed." );
            result = false;
        }
        else
        {
            try
            {
                context.input = new InputManager( filename, context );
                result = pattern.test( context );
                if( !context.input.atEOF() )
                {
                    context.input.getNextLine( context, true );
                    if( !context.input.atEOF() ) context.err.println( "Extra characters after input." );
                }
            }
            catch( Exception e )
            {
                context.err.println( "Fatal error: " + e.getMessage() );
                e.printStackTrace();
                context.err.println( "Processing stopped." );
                result = false;
            }
        }
        
        return result;
    }
    
    public void addFunction( Function function )
    {
        context.addFunction( function );
    }

    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        VIVA viva = new VIVA();
        
        viva.addFunction( new LengthFunction() );
        
        viva.setPattern( new FileInputStream( "test.pattern" ) );
        
        viva.testInputFile( "test.judge" );
    }

}
