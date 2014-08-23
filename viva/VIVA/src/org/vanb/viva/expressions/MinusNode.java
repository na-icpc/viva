package org.vanb.viva.expressions;

public class MinusNode extends NumberBinaryOperatorNode
{
    @Override
    public Object evaluate( double l, double r ) throws Exception
    {
        return new Double( l-r );
    }

    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        return new Float( l-r );
    }

    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Long( l-r );
    }

    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Integer( l-r );
    }

}
