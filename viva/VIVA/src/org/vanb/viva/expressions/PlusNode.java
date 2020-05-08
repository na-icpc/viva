package org.vanb.viva.expressions;

/**
 * The Class PlusNode.
 */
public class PlusNode extends NumberBinaryOperatorNode
{
    
    /**
     * Evaluate.
     *
     * @param l the l
     * @param r the r
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( double l, double r ) throws Exception
    {
        return new Double( l+r );
    }

    /**
     * Evaluate.
     *
     * @param l the l
     * @param r the r
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        return new Float( l+r );
    }

    /**
     * Evaluate.
     *
     * @param l the l
     * @param r the r
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Long( l+r );
    }

    /**
     * Evaluate.
     *
     * @param l the l
     * @param r the r
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Integer( l+r );
    }

}
