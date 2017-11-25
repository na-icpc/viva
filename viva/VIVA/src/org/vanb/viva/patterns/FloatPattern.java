package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;

public class FloatPattern extends ValuePattern
{
    @Override
    public Object getValue( String token, VIVAContext context ) throws Exception
    {
        if( !context.javafloat && !ValuePattern.goodDouble.matcher( token ).matches() ) throw new Exception();
        return new Float( token.trim() );
    }
    
    public Class<?> getType()
    {
        return Float.class;
    }
       
    public Object getDefaultValue()
    {
        return new Float( 0 );
    }

}
