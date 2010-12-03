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
     * @param context TODO
     * 
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( VIVAContext context );
}
