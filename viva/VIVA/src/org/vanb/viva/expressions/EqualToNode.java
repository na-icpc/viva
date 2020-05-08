package org.vanb.viva.expressions;

/**
 * The Class EqualToNode.
 */
public class EqualToNode extends BooleanOperatorNode
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
        return new Boolean( Math.abs( l-r ) < deps );
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
        return new Boolean( Math.abs( l-r ) < feps );
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
        return new Boolean( l==r );
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
        return new Boolean( l==r );
    }

    /**
     * Evaluate.
     *
     * @param l the left string
     * @param r the right string
     * @return the object
     * @throws Exception the exception
     */
    @Override
    public Object evaluate( String l, String r ) throws Exception
    {
        return new Boolean( l.equals( r ) );
    }
}
