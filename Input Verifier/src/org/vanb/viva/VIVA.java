package org.vanb.viva;

import org.vanb.viva.parser.*;
import org.vanb.viva.parameters.*;
import org.vanb.viva.patterns.*;
import org.vanb.viva.utils.*;
import org.vanb.viva.functions.*;

import java.io.*;
import java.util.*;

/**
 * VIVA: vanb's Input Verification Assistant
 * 
 * @author vanb
 */
public class VIVA
{    
    
    private Pattern pattern = null;
    private VIVAContext context = new VIVAContext();
    public static HashMap<String,Parameter> parameters;
    
    /**
     * Static constructor to build parameters
     */
    static
    {
        parameters = new HashMap<String,Parameter>();
        parameters.put( "deps", new DoubleRangeParameter( 0.0, Double.MAX_VALUE, 0.000001 ) );
        parameters.put( "feps", new FloatRangeParameter( 0.0F, Float.MAX_VALUE, 0.000001F ) );
        parameters.put( "ignoreeoln", new StringListParameter( Parameter.truefalse, 1 ) );
        parameters.put( "ignoreblanks", new StringListParameter( Parameter.truefalse, 1 ) );
        parameters.put( "maxerrs", new IntegerRangeParameter( 0, Integer.MAX_VALUE, 25 ) );
    }
    
    /**
     * Tell VIVA the pattern to use.
     * 
     * @param stream Input stream with the pattern
     */
    public void setPattern( InputStream stream )
    {
        try
        {
            PatternParser parser = new PatternParser( stream );
            parser.setFunctions( context.functions );
            pattern = parser.start();            
        }
        catch( Exception e )
        {
            pattern = null;
            context.err.println( "Pattern parsing failed: " + e.getMessage() );
        }
    }
    
    /**
     * Tell VIVA where to write its results.
     * 
     * @param ps A PrintStream for VIVA to write results to
     */
    public void setOutputStream( PrintStream ps )
    {
        context.err = ps;
    }
    
    /**
     * Test a judge input file against a pattern.
     * 
     * @param filename Name of the file to test.
     * @return true if the file passes the test, otherwise false
     */
    public boolean testInputFile( String filename )
    {
        context.err.println( "<<<<< Testing file: " + filename + " >>>>>" );
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
                context.input.eofChecks( context );
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
    
    /**
     * Add a Scalar Function to be use in VIVA's constraints.
     * 
     * @param function The function to add
     */
    public void addFunction( ScalarFunction function )
    {
        context.addFunction( function );
    }
    
    /**
     * Add a Vector Function to be use in VIVA's constraints.
     * 
     * @param function The function to add
     */
    public void addFunction( VectorFunction function )
    {
        context.addFunction( function );
    }
    
    /**
     * VIVA constructor adds all of the standard VIVA Functions.
     */
    public VIVA()
    {
        addFunction( new LengthFunction() );
        addFunction( new DepsFunction() );
        addFunction( new FepsFunction() );
        addFunction( new UniqueFunction() );
        addFunction( new SumFunction() );
        addFunction( new DistanceFunction() );
        addFunction( new SquareRootFunction() );   
        addFunction( new NaturalLogFunction() );   
        addFunction( new Log10Function() );   
        addFunction( new Log2Function() );   
        addFunction( new TesterFunction() );   
        addFunction( new IfFunction() );   
        addFunction( new ConcatenateFunction() );   
        addFunction( new ToStringFunction() );   
        addFunction( new ToIntegerFunction() );   
        
        for( String name : parameters.keySet() )
        {
            Parameter parameter = parameters.get( name );
            context.setParameter( name, parameter.getDefaultValue() );
        }
    }

    /**
     * The main() is just for testing.
     * 
     * @param args Command-line args - the first is a file with the pattern, the rest are input files to test.
     */
    public static void main( String[] args ) throws Exception
    {
        VIVA viva = new VIVA();
                
        viva.setPattern( new FileInputStream( args.length>0 ? args[0] : "test.pattern" ) );
        
        if( args.length>1 )
        {
            for( int i=1; i<args.length; i++ )
            {
                viva.testInputFile( args[i] );
            }
        }
        else
        {
            viva.testInputFile( "test.judge" );
        }
    }
}
