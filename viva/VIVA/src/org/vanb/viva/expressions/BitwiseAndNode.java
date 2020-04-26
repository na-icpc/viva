package org.vanb.viva.expressions;

/**
 * The Class BitwiseAndNode.
 */
public class BitwiseAndNode extends IntegerLongBinaryOperatorNode
{

    /**
     * Evaluate Long parameters.
     *
     * @param l the left expression
     * @param r the right expression
     * @return the result
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Long( l & r );
    }

    /**
     * Evaluate Integer parameters.
     *
     * @param l the left expression
     * @param r the right expression
     * @return the result
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Integer( l & r );
    }

}
