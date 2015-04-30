package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public class EOLNStyleParameter extends StringListParameter
{
    private static String options[] = { "system", "windows", "linux", "mac" };
    private static String values[] = { System.lineSeparator(), "\r\n", "\n", "\r" };
    
    public EOLNStyleParameter()
    {
        super( "eolnstyle", options, 0 );
    }

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
