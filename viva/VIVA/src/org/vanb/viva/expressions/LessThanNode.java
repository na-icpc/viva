package org.vanb.viva.expressions;

/**
 * Implementation of the Less Than operator.
 */
public class LessThanNode extends BooleanOperatorNode
{
    
    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(double, double)
     */
    public Object evaluate( double l, double r ) throws Exception
    {
        return new Boolean( l<r );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(float, float)
     */
    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        return new Boolean( l<r );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(long, long)
     */
    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Boolean( l<r );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(int, int)
     */
    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Boolean( l<r );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.BooleanOperatorNode#evaluate(java.lang.String, java.lang.String)
     */
    @Override
    public Object evaluate( String l, String r ) throws Exception
    {
        return new Boolean( l.compareTo( r )<0 );
    }
}
