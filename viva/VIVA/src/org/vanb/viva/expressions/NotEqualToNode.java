package org.vanb.viva.expressions;

/**
 * Implementation of the Not Equal To operator.
 */
public class NotEqualToNode extends BooleanOperatorNode
{
    
    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(double, double)
     */
    @Override
    public Object evaluate( double l, double r ) throws Exception
    {
        return new Boolean( Math.abs( l-r ) >= deps );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(float, float)
     */
    @Override
    public Object evaluate( float l, float r ) throws Exception
    {
        return new Boolean( Math.abs( l-r ) >= feps );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(long, long)
     */
    @Override
    public Object evaluate( long l, long r ) throws Exception
    {
        return new Boolean( l!=r );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.NumberBinaryOperatorNode#evaluate(int, int)
     */
    @Override
    public Object evaluate( int l, int r ) throws Exception
    {
        return new Boolean( l!=r );
    }

    /** (non-Javadoc)
     * @see org.vanb.viva.expressions.BooleanOperatorNode#evaluate(java.lang.String, java.lang.String)
     */
    @Override
    public Object evaluate( String l, String r ) throws Exception
    {
        return new Boolean( !l.equals( r ) );
    }
}
