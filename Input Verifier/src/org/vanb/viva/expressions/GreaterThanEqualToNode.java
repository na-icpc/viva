package org.vanb.viva.expressions;

public class GreaterThanEqualToNode extends BooleanOperatorNode
{
    public Object evaluate( double l, double r )
    {
        return new Boolean( l>=r-deps );
    }

    @Override
    public Object evaluate( float l, float r )
    {
        return new Boolean( l>=r-feps);
    }

    @Override
    public Object evaluate( long l, long r )
    {
        return new Boolean( l>=r );
    }

    @Override
    public Object evaluate( int l, int r )
    {
        return new Boolean( l>=r );
    }
}
