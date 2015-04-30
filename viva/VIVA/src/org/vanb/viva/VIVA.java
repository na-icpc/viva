package org.vanb.viva;

import org.vanb.viva.parser.*;
import org.vanb.viva.parameters.*;
import org.vanb.viva.patterns.*;
import org.vanb.viva.utils.*;
import org.vanb.viva.functions.*;

import java.io.*;

/**
 * VIVA: vanb's Input Verification Assistant
 * 
 * @author vanb
 */
public class VIVA
{    
    
    private Pattern pattern = null;
    private VIVAContext context = new VIVAContext();
    
    /**
     * Tell VIVA the pattern to use (and parse it).
     * 
     * @param stream Input stream with the pattern
     * @return true if the pattern parsed successfully, otherwise false
     */
    public boolean setPattern( InputStream stream )
    {
        boolean success = true;
        try
        {
            PatternParser parser = new PatternParser( stream );
            parser.setFunctions( context.functions );
            parser.setParameters( context.parameters );
            pattern = parser.start();
            success = true;
        }
        catch( ParseException e )
        {
            pattern = null;
            context.err.println( "Pattern parsing failed: " + e.getMessage() );
            success = false;            
        }
        catch( TokenMgrError e )
        {
            pattern = null;
            context.err.println( "Pattern parsing failed: " + e.getMessage() );
            success = false;            
        }
        catch( Throwable e )
        {
            pattern = null;
            context.err.println( "Pattern parsing encountered Exception: " + e );
            e.printStackTrace( context.err );
            success = false;
        }
        
        return success;
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
        context.success = true;
        context.err.println( "<<< Testing file: " + filename + " >>>" );
        context.errcount = 0;
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
                context.input.eofChecks();
            }
            catch( Exception e )
            {
                context.err.println( "Fatal error: " + e.getMessage() );
                context.err.println( "Processing stopped." );
                result = false;
            }
        }
        
        result &= context.success;
        context.err.println( "<<< DONE Testing file: " + filename + " >>>" );
        
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
        addFunction( new ArcCosineFunction() );   
        addFunction( new ArcSineFunction() );   
        addFunction( new ArcTangent2Function() );   
        addFunction( new ArcTangentFunction() );   
        addFunction( new ConcatenateFunction() );   
        addFunction( new ConcatAllFunction() );   
        addFunction( new CosineFunction() );   
        addFunction( new CountFunction() );   
        //addFunction( new DepsFunction() );
        addFunction( new DistanceFunction() );
        addFunction( new EulersNumberToPowerFunction() );
        //addFunction( new FepsFunction() );
        addFunction( new HyperbolicCosineFunction() );   
        addFunction( new HyperbolicSineFunction() );   
        addFunction( new HyperbolicTangentFunction() );   
        addFunction( new IfFunction() );   
        addFunction( new LeftJustificationFunction() );
        addFunction( new LengthFunction() );
        addFunction( new Log10Function() );   
        addFunction( new Log2Function() );   
        addFunction( new NaturalLogFunction() );   
        addFunction( new PowerFunction() );   
        addFunction( new RightJustificationFunction() );   
        addFunction( new SineFunction() );   
        addFunction( new SquareRootFunction() );   
        addFunction( new SumFunction() );
        addFunction( new TangentFunction() );   
        addFunction( new TestFunction() );   
        addFunction( new TestAllFunction() );   
        addFunction( new ToDegreesFunction() );   
        addFunction( new ToDoubleFunction() );   
        addFunction( new ToFloatFunction() );   
        addFunction( new ToIntegerFunction() );   
        addFunction( new ToLongFunction() );   
        addFunction( new ToRadiansFunction() );   
        addFunction( new ToStringFunction() );   
        addFunction( new UniqueFunction() );
        addFunction( new IncreasingFunction() );
        addFunction( new DecreasingFunction() );
        addFunction( new NonIncreasingFunction() );
        addFunction( new NonDecreasingFunction() );
        
        context.addParameter( new DepsParameter() );
        context.addParameter( new FepsParameter() );
        context.addParameter( new MaxErrsParameter() );
        context.addParameter( new IgnoreBlanksParameter() );
        context.addParameter( new IgnoreEOLNParameter() );
        context.addParameter( new EOLNStyleParameter() );
        context.addParameter( new EOFStyleParameter() );
    }

    /**
     * The main() for command-line VIVA.
     * Return codes:
     * -2 = Bad usage
     * -1 = Pattern parsing failure
     *  0 = All files pass
     * >0 = Number of failures
     * 
     * @param args Command-line args - the first is a file with the pattern, the rest are input files to test.
     */
    public static void main( String[] args ) throws Exception
    {
        int retcode  = 0;
        if( args.length==0 )
        {
            System.out.println( "Usage: VIVA <pattern file> (<input file>)*");
            retcode = -2;
        }
        else
        {
            VIVA viva = new VIVA();                    
            if( !viva.setPattern( new FileInputStream( args[0] ) ) ) retcode = -1;
            
            if( retcode==0 )
            {
                for( int i=1; i<args.length; i++ )
                {
                    if( !viva.testInputFile( args[i] ) ) ++retcode;
                }
            }
        }
        
        System.exit( retcode );
    }
}
