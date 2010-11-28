package org.vanb.viva.patterns;

public class LongPattern extends ValuePattern
{
    @Override
    public Object getValue( String token ) throws Exception
    {
        return new Long( token );
    }

}
