package org.vanb.viva.parameters;

public class Parameter
{
    private Class<?> type;
    
    public Parameter( Class<?> t )
    {
        type = t;
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
}
