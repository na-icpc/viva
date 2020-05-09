/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

import java.util.Arrays;

/**
 * Superclass of all Parameters that have a list of Strings as possible values.
 * This includes true/false parameters.
 * 
 * @author vanb
 */
public abstract class StringListParameter extends Parameter
{
    
    /**  List of possible values. */
    public String values[];
    
    /**
     * Create a StringListParameter. 
     * Only subclasses may call this.
     * 
     * @param name Name of the Parameter.
     * @param values List of Values.
     * @param defaultValueIndex Index in the list of the default value
     */
    protected StringListParameter( String name, String values[], int defaultValueIndex )
    {
        super( name, String.class, values[defaultValueIndex] );
        this.values = Arrays.copyOf( values, values.length );
    }
    
    /**
     * Determine if a potential value is in the List.
     *
     * @param value the value
     * @return true, if is valid
     */
    @Override
    public boolean isvalid( Object value )
    {
        boolean ok;
        
        if( value instanceof String )
        {
            ok = false;
            for( String option : values )
            {
                if( option.equals( value ))
                {
                    ok=true;
                    break;
                }
            }
        }
        else
        {
            ok = false;
        }
        return ok;
    }

    
    /**
     * Describe how to use this Parameter.
     *
     * @return the string
     */
    @Override
    public String usage()
    {
        boolean first = true;
        String message = "Must be a String, one of:";
        for( String value : values )
        {
            message += (first?"":",") + " " + value;
            first = false;
        }
            
        return message;
    }
    
    /**
     * Convert.
     *
     * @param token the token
     * @return the object
     */
    @Override
    public Object convert( String token )
    {
        String result = null;
        for( String value : values ) if( value.equals( token ) ) result = token;;
        return result;
    }
}
