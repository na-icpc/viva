/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class EOLNStyleParameter.
 */
public class EOLNStyleParameter extends StringListParameter
{
    
    /** The options. */
    private static String options[] = { "system", "windows", "linux", "mac" };
    
    /** The values. */
    private static String values[] = { System.lineSeparator(), "\r\n", "\n", "\r" };
    
    /**
     * Instantiates a new EOLN style parameter.
     */
    public EOLNStyleParameter()
    {
        super( "eolnstyle", options, 0 );
    }

    /**
     * Action.
     *
     * @param context the context
     * @param value the value
     */
    @Override
    public void action( VIVAContext context, Object value )
    {
        String v = (String)value;
        
        for( int i=0; i<options.length; i++ )
        {
            if( v.equals( options[i]  ) ) 
            {
                context.lineSeparator = values[i];
                break;
            }
        }
    }
}
