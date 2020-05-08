package org.vanb.viva.expressions;

/**
 * The Class MinusNode.
 */
public class MinusNode extends NumberBinaryOperatorNode
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
        return new Double( l-r );
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
        return new Float( l-r );
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
        return new Long( l-r );
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
        return new Integer( l-r );
    }

}
