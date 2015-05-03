package org.vanb.viva.patterns;

import org.vanb.viva.utils.*;

/**
 * Generic Pattern
 * 
 * @author David Van Brackle
 */
public interface Pattern
{
    /**
     * Test to see if this pattern matches the input file
     * @param context The context in which VIVA is operating
     * 
     * @return true if this Pattern matches, otherwise false
     * @throws VIVAException
     */
    public boolean test( VIVAContext context ) throws VIVAException;
}
