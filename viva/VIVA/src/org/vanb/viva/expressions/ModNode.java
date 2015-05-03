package org.vanb.viva.expressions;

import org.vanb.viva.VIVA;

public class ModNode extends NumberBinaryOperatorNode
{
    @Override
    public Object evaluate( double l, double r ) throws Exception
    {
        if( Math.abs(r)<VIVA.DZERO )
        {
            throw new Exception( "Division by zero" );
        }
        return new Double( l % r );
    }

    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        if( Math.abs(r)<VIVA.FZERO )
        {
            throw new Exception( "Division by zero" );
        }
        return new Float( l % r );
    }

    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        if( r==0 )
        {
            throw new Exception( "Division by zero" );
        }
        return new Long( l % r );
    }

    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        if( r==0 )
        {
            throw new Exception( "Division by zero" );
        }
        return new Integer( l % r );
    }

}

