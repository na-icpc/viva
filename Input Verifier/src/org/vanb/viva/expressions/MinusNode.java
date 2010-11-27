package org.vanb.viva.expressions;

public class MinusNode extends NumberBinaryOperatorNode
{
    @Override
    public Object evaluate( double l, double r )
    {
        return new Double( l-r );
    }

    @Override
    public Object evaluate( float l, float r )
    {
        return new Float( l-r );
    }

    @Override
    public Object evaluate( long l, long r )
    {
        return new Long( l-r );
    }

    @Override
    public Object evaluate( int l, int r )
    {
        return new Integer( l-r );
    }

}
