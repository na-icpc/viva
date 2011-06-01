package org.vanb.viva.patterns;


public class FloatPattern extends ValuePattern
{

    @Override
    public Object getValue( String token ) throws Exception
    {
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
