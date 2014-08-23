package org.vanb.viva.expressions;

public class BitwiseAndNode extends IntegerLongBinaryOperatorNode
{

    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Long( l & r );
    }

    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Integer( l & r );
    }

}
