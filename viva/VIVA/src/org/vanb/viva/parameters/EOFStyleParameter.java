package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

/**
 * The Class EOFStyleParameter.
 */
public class EOFStyleParameter extends StringListParameter
{
    
    /** The options. */
    private static String options[] = { "both", "windows", "linux", "system" };
    
    /**
     * Instantiates a new EOF style parameter.
     */
    public EOFStyleParameter()
    {
        super( "eofstyle", options, 3 );
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
        
        if( v.equals( options[0] ) ) // both
        {
            context.allowWindowsEOF = true;
            context.allowLinuxEOF = true;
        }
        if( v.equals( options[1] ) ) // Windows
        {
            context.allowWindowsEOF = true;
            context.allowLinuxEOF = false;
        }
        if( v.equals( options[2] ) ) // Linux
        {
            context.allowWindowsEOF = false;
            context.allowLinuxEOF = true;
        }
        if( v.equals( options[3] ) ) // system
        {
            if( System.getProperty( "os.name" ).toLowerCase().contains( "win" ) )
            {
                context.allowWindowsEOF = true;
                context.allowLinuxEOF = false;                
            }
            else
            {
                context.allowWindowsEOF = false;
                context.allowLinuxEOF = true;                
            }    
        }
    }
}
