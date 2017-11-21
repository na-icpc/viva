package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;

public class StringPattern extends ValuePattern
{
    @Override
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        return token;
    }
    
    public Class<?> getType()
    {
        return String.class;
    }
      
    public Object getDefaultValue()
    {
        return "";
    }

}
