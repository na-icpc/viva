/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

/**
 * Implementation of the Greater Than operator.
 */
public class GreaterThanNode extends BooleanOperatorNode
{
    
    /**
     * Evaluate
     *
     * @param l the left double
     * @param r the right double
     * @return the object
     * @throws Exception the exception
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(double, double)
     */
    @Override
    public Object evaluate( double l, double r ) throws Exception
    {
        return new Boolean( l>r );
    }

    /**
     * Evaluate
     *
     * @param l the left float
     * @param r the right float
     * @return the object
     * @throws Exception the exception
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(float, float)
     */
    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        return new Boolean( l>r );
    }

    /**
     * Evaluate
     *
     * @param l the left long
     * @param r the right long
     * @return the object
     * @throws Exception the exception
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(long, long)
     */
    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Boolean( l>r );
    }

    /**
     * Evaluate
     *
     * @param l the left int
     * @param r the right int
     * @return the object
     * @throws Exception the exception
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(int, int)
     */
    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Boolean( l>r );
    }

    /**
     * Evaluate
     *
     * @param l the left string
     * @param r the right string
     * @return the object
     * @throws Exception the exception
     * @see org.vanb.viva.expressions.BooleanOperatorNode#evaluate(java.lang.String, java.lang.String)
     */
    @Override
    public Object evaluate( String l, String r ) throws Exception
    {
        return new Boolean( l.compareTo( r )>0 );
    }
}
