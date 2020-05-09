/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

/**
 * The Class BitwiseXorNode.
 */
public class BitwiseXorNode extends IntegerLongBinaryOperatorNode
{
    
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
        return new Long( l ^ r );
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
        return new Integer( l ^ r );
    }

}
