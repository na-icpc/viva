package org.vanb.viva.patterns;


public class FloatPattern extends ValuePattern
{

    @Override
    public Object getValue( String token ) throws Exception
    {
        return new Float( token );
    }
    
    
    public String getType()
    {
        return "Float";
    }
}
