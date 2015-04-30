package org.vanb.viva.parameters;

import java.util.Arrays;

public abstract class StringListParameter extends Parameter
{
    protected String values[];
    
    public StringListParameter( String name, String values[], int defaultValueIndex )
    {
        super( name, String.class, values[defaultValueIndex] );
        this.values = Arrays.copyOf( values, values.length );
    }
    
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

}
