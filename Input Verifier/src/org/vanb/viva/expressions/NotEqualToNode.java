package org.vanb.viva.expressions;

public class NotEqualToNode extends BooleanOperatorNode
{
    @Override
    public Object evaluate( double l, double r ) throws Exception
    {
        return new Boolean( Math.abs( l-r ) >= deps );
    }

    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        return new Boolean( Math.abs( l-r ) >= feps );
    }

    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Boolean( l!=r );
    }

    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Boolean( l!=r );
    }
}
