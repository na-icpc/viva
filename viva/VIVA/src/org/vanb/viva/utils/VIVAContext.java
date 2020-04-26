package org.vanb.viva.utils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;

import org.vanb.viva.Function;
import org.vanb.viva.VIVA;
import org.vanb.viva.parameters.Parameter;

/**
 * This Class keeps track of context used while testing an input file.
 * 
 * @author vanb
 */
public class VIVAContext
{
    
    /**  Where output should go to. */
    public PrintStream err = System.out;
    
    /** Something to manage input (break into tokens, flag extra spaces, etc.) */
    public InputManager input;
    
    /**  Symbol Table plus associated values. */
    public SymbolTable<ValueManager> values;
    
    /**  Number of errors (too many and we fail fatally). */
    public int errcount;
    
    /**  Depth of control structure nesting. */
    public int testLevel = 0;
    
    /**  All of VIVA's functions indexed by name. */
    public HashMap<String,Function> functions = new HashMap<String,Function>();
    
    /**  Repetition count of current control structure. */
    public int index = -1;
    
    /**  Line separator, defaulting to the system default. */
    public String lineSeparator = System.getProperty( "line.separator" ); 
    
    /**  Controlling parameters, like deps and feps. */
    public HashMap<String,Parameter> parameters = new HashMap<String,Parameter>();
    
    /**  Floating point epsilon. */
    public float feps = VIVA.FEPS;
    
    /**  Double epsilon. */
    public double deps = VIVA.DEPS;
    
    /**  Was input file matched without errors?. */
    public boolean success;
    
    /**  Don't complain about extra blanks. */
    public boolean ignoreBlanks = false;
    
    /**  Don't complain about data over multiple lines. */
    public boolean ignoreEOLN = false;
    
    /**  Maximum number of errors before failing. */
    public int maxerrs = 25;
    
    /**  Allow Windows style EOF: No EOLN. */
    public boolean allowWindowsEOF = true;
    
    /**  Allow Linux style EOF: A Single EOLN. */
    public boolean allowLinuxEOF = true;
    
    /**  Use Java ints (allows leading 0s and +). */
    public boolean javaint = false;
    
    /**  Use Java longs (allows leading 0s and +). */
    public boolean javalong = false;
    
    /**  Use Java doubles (allows leading 0s, +, and scientific notation. */
    public boolean javadouble = false;
    
    /**  Use Java floats (allows leading 0s, +, and scientific notation. */
    public boolean javafloat = false;    
    
    /** Place for global stuff, if a function needs it. */
    public HashMap<String,Object> globals = new HashMap<String,Object>();
    
    /**
     * Create a Context.
     */
    public VIVAContext()
    {
        values = new SymbolTable<ValueManager>();
        errcount = 0;
        success = true;
    }
    
    /**
     * Add a new kind of parameter.
     * 
     * @param parameter Parameter to add
     */
    public void addParameter( Parameter parameter )
    {
        parameters.put( parameter.getName(), parameter );  
        Object defaultValue = parameter.getDefaultValue();
        parameter.setCurrentValue( defaultValue );
        parameter.action( this, defaultValue );
    }
    
    /**
     * Set a particular parameter.
     * 
     * @param name Parameter name
     * @param value New value
     */
    public void setParameter( String name, Object value )
    {
        Parameter parameter = parameters.get( name );
        parameter.setCurrentValue( value );
        parameter.action( this, value );
    }
    
    /**
     * Get the value of a particular parameter.
     *
     * @param name the name
     * @return The parameter's current value
     */
    public Object getParameter( String name )
    {
        Parameter parameter = parameters.get( name );
        return parameter.getCurrentValue();
    }
 
    /**
     * Throw an exception if we're at the top level.
     *
     * @param message Exception message
     * @throws VIVAException the VIVA exception
     */
    public void throwException( String message ) throws VIVAException
    {
        if( testLevel==0 )
        {
            success = false;
            throw new VIVAException( "At line " + input.getLine() + " token " + input.getToken() + ": " + message, 
                    input.getLine(), input.getToken() );
        }
    }
    
    /**
     * Show the user an error message if we're at the top level.
     *
     * @param message Error message
     * @throws VIVAException the VIVA exception
     */
    public void showError( String message ) throws VIVAException
    {
        if( testLevel==0 )
        {
            success = false;
            err.println( "At line " + input.getLine() + " token " + input.getToken() + ": " + message );
            ++errcount;
            if( errcount>=maxerrs )
            {
                throwException( "Too many errors. Exiting." );
            }
        }
    }
    
    /**
     * Add a new function to VIVA's function list.
     * 
     * @param function Function to add
     */
    public void addFunction( Function function )
    {
        functions.put( function.getName(), function );
    }
    
    /**
     * Gets the prefix for something in the globals.
     *
     * @param type the type
     * @return the prefix
     */
    private String getPrefix( Class<?> type )
    {
        return type==HashSet.class ? "list" : type.getSimpleName().toLowerCase();
    }
    
    /**
     * Puts a thing in the globals.
     *
     * @param name the name
     * @param thing the thing
     * @throws Exception the exception
     */
    public void setThing( Object name, Object thing ) throws Exception
    {
        String prefix = getPrefix( thing.getClass() );
        String key = prefix + ":" + name.toString();
        if( globals.containsKey( key ) ) throw new Exception( "Already a " + prefix + " called " +name.toString() );
        globals.put( key, thing );
    }
    
    /**
     * Gets a thing from the globals.
     *
     * @param type the type
     * @param name the name
     * @return the thing
     * @throws Exception the exception
     */
    public Object getThing( Class<?> type, Object name ) throws Exception
    {
        String prefix = getPrefix( type );
        Object thing = globals.get( prefix + ":" + name.toString() );
        if( thing==null ) throw new Exception( "No such " + type + " as " + name.toString() );
        return thing;
    }    
}
