package org.vanb.viva;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.parser.PatternParser;
import org.vanb.viva.parser.TokenMgrError;
import org.vanb.viva.patterns.Pattern;
import org.vanb.viva.utils.InputManager;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.functions.CompareFunctions;
import org.vanb.viva.functions.GlobalFunctions;
import org.vanb.viva.functions.GraphFunctions;
import org.vanb.viva.functions.MathFunctions;
import org.vanb.viva.functions.MiscFunctions;
import org.vanb.viva.functions.StringFunctions;
import org.vanb.viva.parameters.DepsParameter;
import org.vanb.viva.parameters.EOFStyleParameter;
import org.vanb.viva.parameters.EOLNStyleParameter;
import org.vanb.viva.parameters.FepsParameter;
import org.vanb.viva.parameters.IgnoreBlanksParameter;
import org.vanb.viva.parameters.IgnoreEOLNParameter;
import org.vanb.viva.parameters.JavaDoubleParameter;
import org.vanb.viva.parameters.JavaFloatParameter;
import org.vanb.viva.parameters.JavaIntParameter;
import org.vanb.viva.parameters.JavaLongParameter;
import org.vanb.viva.parameters.MaxErrsParameter;
import org.vanb.viva.parameters.Parameter;

/**
 * VIVA: vanb's Input Verification Assistant
 * 
 * @author vanb
 */
public class VIVA
{    
    /** default value for double epsilon */
    public static final double DEPS = 0.000001;
    
    /** Default value for float epsilon */
    public static final float FEPS = 0.000001F;
    
    /** Any double less than this (in absolute value) is 0. This is different from deps: it is used to prevent division by 0 */
    public static final double DZERO = 0.000000000000001;
    
    /** Any float less than this (in absolute value) is 0. This is different from feps: it is used to prevent division by 0 */
    public static final float FZERO = 0.0000000001F;
    
    /** The parsed pattern used to check input files */
    private Pattern pattern = null;
    
    /** VIVA's context */
    private VIVAContext context = new VIVAContext();
    
    /**
     * Adds a group of functions that are inner classes of a Class.
     *
     * @param functions the Class which holds the functions
     */
    private void addFunctions( Class<?> functions )
    {
        for( Class<?> function : functions.getClasses() ) if( Function.class.isAssignableFrom( function ) )
        {
            int mods = function.getModifiers();
            if( Modifier.isStatic( mods ) 
                    && Modifier.isPublic( mods ) 
                    && !Modifier.isAbstract( mods ) 
                    && !Modifier.isInterface( mods ) ) try
            {
                context.addFunction( (Function)function.newInstance() );
            }
            catch( InstantiationException e )
            {
                System.err.println( "Could not add function " + function.getCanonicalName() + ": " + e.getMessage() );
                e.printStackTrace();
            }
            catch( IllegalAccessException e )
            {
                System.err.println( "Could not add function " + function.getCanonicalName() + ": " + e.getMessage() );
                e.printStackTrace();
            }
        }
    }
    
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
                context.clearContext();
                context.input = new InputManager( filename, context );
                result = pattern.test( context );
                context.input.eofChecks();
                context.input.close();
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
     * Get the Context.
     * 
     * @return Context
     */
    public VIVAContext getContext()
    {
        return context;
    }
    
    /**
     * VIVA constructor adds all of the standard VIVA Functions.
     */
    public VIVA()
    { 
        addFunctions( MathFunctions.class );
        addFunctions( StringFunctions.class );
        addFunctions( CompareFunctions.class );
        addFunctions( MiscFunctions.class );
        addFunctions( GlobalFunctions.class );
        addFunctions( GraphFunctions.class );
        
        context.addParameter( new DepsParameter() );
        context.addParameter( new FepsParameter() );
        context.addParameter( new MaxErrsParameter() );
        context.addParameter( new IgnoreBlanksParameter() );
        context.addParameter( new IgnoreEOLNParameter() );
        context.addParameter( new EOLNStyleParameter() );
        context.addParameter( new EOFStyleParameter() );
        context.addParameter( new JavaIntParameter() );
        context.addParameter( new JavaLongParameter() );
        context.addParameter( new JavaDoubleParameter() );
        context.addParameter( new JavaFloatParameter() );
    }

    /**
     * The main() for command-line VIVA.
     * Return codes:
     * -2 = Bad usage
     * -1 = Pattern parsing failure
     *  0 = All files pass
     * >0 = Number of failures
     *
     * @param args Command-line args
     * @throws Exception the exception
     */
    public static void main( String[] args ) throws Exception
    {
        int retcode  = 0;
        if( args.length==0 )
        {
            System.out.println( "Usage: VIVA [parameters] <pattern file> (<input file>)*");
            System.out.println( "Parameters:" );
            System.out.println( "-deps=<double>" );
            System.out.println( "\tSet Double Precision Epsilon" );
            System.out.println( "\tDefault is 0.000001, must be >=0.0" );
            System.out.println( "-feps=<float>" );
            System.out.println( "\tSet Floating Point Epsilon" );
            System.out.println( "\tDefault is 0.000001, must be >=0.0" );
            System.out.println( "-maxerrs=<integer>" );
            System.out.println( "\tSet maximum number of errors before quitting on an input file" );
            System.out.println( "\tDefault is 25, must be >=0" );
            System.out.println( "-eofstyle=windows|linux|both|system" );
            System.out.println( "\tSet the EOF style to accept (File ends with EOLN?)" );
            System.out.println( "\tDefault is 'system'" );
            System.out.println( "-eolnstyle=windows|linux|mac|system" );
            System.out.println( "\tSet the EOLN character(s) to accept (linux=\\n, mac=\\r, windows=\\r\\n)" );
            System.out.println( "\tDefault is 'system'" );
            System.out.println( "-ignoreblanks=true|t|yes|y|1|false|f|no|n|0 (lower or upper case)" );
            System.out.println( "\tShould VIVA ignore extra blanks?" );
            System.out.println( "\tDefault is 'false'" );
            System.out.println( "-ignoreeoln=true|t|yes|y|1|false|f|no|n|0 (lower or upper case)" );
            System.out.println( "\tShould VIVA ignore newlines and allow patterns to extend across multiple lines?" );
            System.out.println( "\tDefault is 'false'" );
            System.out.println( "-javaint=true|t|yes|y|1|false|f|no|n|0 (lower or upper case)" );
            System.out.println( "\tShould VIVA use Java's more liberal integer parsing? (Allows leading '+' and 0s)" );
            System.out.println( "\tDefault is 'false'" );
            System.out.println( "-javalong=true|t|yes|y|1|false|f|no|n|0 (lower or upper case)" );
            System.out.println( "\tShould VIVA use Java's more liberal long parsing? (Allows leading '+' and 0s)" );
            System.out.println( "\tDefault is 'false'" );
            System.out.println( "-javafloat=true|t|yes|y|1|false|f|no|n|0 (lower or upper case)" );
            System.out.println( "\tShould VIVA use Java's more liberal floating point parsing? (Allows leading '+' and 0s, and scientific notation)" );
            System.out.println( "\tDefault is 'false'" );
            System.out.println( "-javadouble=true|t|yes|y|1|false|f|no|n|0 (lower or upper case)" );
            System.out.println( "\tShould VIVA use Java's more liberal double precision parsing? (Allows leading '+' and 0s, and scientific notation)" );
            System.out.println( "\tDefault is 'false'" );
            retcode = -2;
        }
        else
        {
            VIVA viva = new VIVA();      
            
            int arg = 0;
            VIVAContext context = viva.getContext();
            
            // Check for parameters. They look like this: -parameter=value
            for( arg=0; arg<args.length && args[arg].startsWith( "-" ) && retcode==0; ++arg )
            {
                // Look for the split between parameter name & value
                int p = args[arg].indexOf( '=' );
                if( p<0 )
                {
                    System.out.println( "No value specified for parameter " + args[arg] );
                    retcode = -2;
                }
                
                String parm=null;
                Object value=null;
                Parameter parameter=null;
                
                if( retcode==0 )
                {
                    // Look for this particular parameter
                    parm = args[arg].substring( 1, p );
                    parameter = context.parameters.get( parm );
                    if( parameter==null ) 
                    {
                        System.out.println( "No parameter named " + parm );
                        retcode = -2;
                    }
                }
                
                if( retcode==0 )
                {
                    //Parse the value
                    String valstr = args[arg].substring( p+1 );
                    Class<?> type = parameter.getType();   
                    if( type==Double.class )
                    {
                        try
                        {
                            value = Double.parseDouble( valstr );
                        }
                        catch( NumberFormatException nfe )
                        {
                            System.out.println( "Cannot parse " + valstr + " as a Double." );
                            retcode = -2;
                        }
                    }
                    else if( type==Float.class )
                    {
                        try
                        {
                            value = Float.parseFloat( valstr );
                        }
                        catch( NumberFormatException nfe )
                        {
                            System.out.println( "Cannot parse " + valstr + " as a Float." );
                            retcode = -2;
                        }
                    }
                    else if( type==Integer.class )
                    {
                        try
                        {
                            value = Integer.parseInt( valstr );
                        }
                        catch( NumberFormatException nfe )
                        {
                            System.out.println( "Cannot parse " + valstr + " as an Integer." );
                            retcode = -2;
                        }
                    }
                    else value = valstr.toLowerCase();
                    
                    if( retcode==0 )
                    {
                        // Check to see if the value is valid
                        if( !parameter.isvalid( value ) )
                        {
                            System.out.println( valstr + " is not a valid value for parameter " + parm );
                            retcode = -2;
                        }
                    }
                    
                    if( retcode==0 )
                    {
                        // Success? Set the parameter
                        context.setParameter( parm, value );
                    }
                    else
                    {
                        // Failure? Give the user a little more info.
                        System.out.println( parameter.usage() );
                    }
                }
            }
            
            if( retcode==0 )
            {
                // Look for pattern file
                if( arg>=args.length )
                {
                    System.out.println( "No pattern specified." );
                    retcode = -2;
                }
                else 
                {
                    if( !new File(args[arg]).exists() )
                    {
                        System.out.println( "Pattern file " + args[arg] + " doesn't exist!" );
                        retcode = -1;
                    }
                    else if( !viva.setPattern( new FileInputStream( args[arg] ) ) ) retcode = -1;
                    else ++arg;
                }
            }
            
            if( retcode==0 )
            {
                // Process input files
                for( int i=arg; i<args.length; i++ )
                {
                    if( !new File(args[i]).exists() )
                    {
                        System.out.println( "File " +args[i] + " doesn't exist!" );
                        ++retcode;
                    }
                    if( !viva.testInputFile( args[i] ) ) ++retcode;
                }
            }
        }
        
        System.exit( retcode );
    }
}
