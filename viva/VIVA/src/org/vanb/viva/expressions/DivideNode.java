/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import org.vanb.viva.VIVA;

/**
 * The Class DivideNode.
 */
public class DivideNode extends NumberBinaryOperatorNode
{
    
    /**
     * Evaluate.
     *
     * @param l the left double
     * @param r the right double
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( double l, double r ) throws Exception
    {
        if( Math.abs(r)<VIVA.DZERO )
        {
            throw new Exception( "Division by zero" );
        }
        return new Double( l/r );
    }

    /**
     * Evaluate.
     *
     * @param l the left float
     * @param r the right float
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        if( Math.abs(r)<VIVA.FZERO )
        {
            throw new Exception( "Division by zero" );
        }
        return new Float( l/r );
    }

    /**
     * Evaluate.
     *
     * @param l the left long
     * @param r the right long
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        if( r==0L )
        {
            throw new Exception( "Division by zero" );
        }
        return new Long( l/r );
    }

    /**
     * Evaluate.
     *
     * @param l the left int
     * @param r the right int
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        if( r==0 )
        {
            throw new Exception( "Division by zero" );
        }
        return new Integer( l/r );
    }

}

