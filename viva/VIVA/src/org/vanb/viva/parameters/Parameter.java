/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * Abstract parent class for all Parameters.
 * 
 * @author vanb
 */
public abstract class Parameter
{
    /** The type of the value for this Parameter */
    private Class<?> type;
    
    /** The value to use if no value is specified */
    private Object defaultValue;
    
    /** Specified value */
    private Object currentValue;
    
    /** The Name of this parameter */
    protected String name;
    
    /** A consistent set of values for true/false parameters */
    protected static final String[] truefalse = { "true", "false", "t", "f", "yes", "no", "y", "n", "0", "1" };
    
    /**
     * Determine of the value of a true/false parameter means 'true' or 'false'.
     * 
     * @param value Value to check
     * @return true or false, based on the value
     */
    protected static boolean isTrue( Object value )
    {
        boolean result = false;
        if( value!=null )
        {
            String p = value.toString().toLowerCase();
            result = p.equals( "true" ) 
                  || p.equals( "t" )
                  || p.equals( "yes" )
                  || p.equals( "y" )
                  || p.equals( "1" );
        }
        return result;
    }
    
    /**
     * Set the fields of this Parameter.
     * This constructor can only be called by subclasses.
     * 
     * @param name Name of this parameter
     * @param type Type of values
     * @param defaultValue The default value
     */
    protected Parameter( String name, Class<?> type, Object defaultValue )
    {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    
    /**
     * Get the Type of this Parameter.
     * 
     * @return Type
     */
    public Class<?> getType()
    {
        return type;
    }
    
    /**
     * Set the current value of this Parameter.
     * 
     * @param value New Value for this Parameter
     */
    public void setCurrentValue( Object value )
    {
        currentValue = value;   
    }
    
    /**
     * Get the current value of this Parameter.
     * 
     * @return Current Value
     */
    public Object getCurrentValue()
    {
        return currentValue;
    }
    
    /**
     * Get the Default Value of this Parameter.
     * 
     * @return Default Value
     */
    public Object getDefaultValue()
    {
        return defaultValue;
    }
    
    /**
     * Get the Name of this Parameter.
     * 
     * @return Parameter Name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Determine if a value is a valid value for this Parameter.
     * 
     * @param value Value to test
     * @return true if value is valid, otherwise false
     */
    public abstract boolean isvalid( Object value );    
    
    /**
     * Return a pretty String which tells the user how to use this Parameter.
     * 
     * @return Usage String
     */
    public abstract String usage();    
    
    /**
     * Do whatever needs doing when the user specifies a value for this Parameter.
     * This usually means changing the Context.
     * 
     * @param context The Context
     * @param value New value for this Parameter.
     */
    public abstract void action( VIVAContext context, Object value );
    
    /**
     * Convert a String to the type required, or null if it can't be parsed.
     * 
     * @param token A String
     * @return The Object or null
     */
    public abstract Object convert( String token );
}
