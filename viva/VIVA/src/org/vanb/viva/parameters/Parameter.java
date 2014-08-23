package org.vanb.viva.parameters;

public class Parameter
{
    private Class<?> type;
    private Object defaultValue;
    
    
    public static final String[] truefalse = { "true", "false", "t", "f", "yes", "no", "y", "n", "0", "1" };
    public static boolean isTrue( Object param )
    {
        boolean result = false;
        if( param!=null )
        {
            String p = param.toString().toLowerCase();
            result = p.equals( "true" ) 
                  || p.equals( "t" )
                  || p.equals( "yes" )
                  || p.equals( "y" )
                  || p.equals( "1" );
        }
        return result;
    }
    
    public Parameter( Class<?> t, Object dv )
    {
        type = t;
        defaultValue = dv;
    }
    
    public Class<?> getType()
    {
        return type;
    }
    
    public boolean isvalid( Object value )
    {
        return true;
    }
    
    public String usage()
    {
        return "This parameter can take any value of any type.";
    }
    
    public Object getDefaultValue()
    {
        return defaultValue;
    }
}
