package org.vanb.viva.parameters;

import org.vanb.viva.utils.VIVAContext;

public abstract class Parameter
{
    private Class<?> type;
    private Object defaultValue;
    private Object currentValue;
    protected String name;
    
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
    
    public Parameter( String name, Class<?> type, Object defaultValue )
    {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    
    public Class<?> getType()
    {
        return type;
    }
    
    public void setCurrentValue( Object value )
    {
        currentValue = value;   
    }
    
    public Object getCurrentValue()
    {
        return currentValue;
    }
    
    public Object getDefaultValue()
    {
        return defaultValue;
    }
    
    public String getName()
    {
        return name;
    }
    
    public abstract boolean isvalid( Object value );    
    public abstract String usage();    
    public abstract void action( VIVAContext context, Object value );
}
