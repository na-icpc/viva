package org.vanb.viva.patterns;

import org.vanb.viva.utils.InputManager;

/**
 * Generic Pattern
 * 
 * @author David Van Brackle
 */
public interface Pattern
{
    /**
     * Test to see if this pattern matches the input file
     * 
     * @param input A controller for the input source
     * @return true if this Pattern matches, otherwise false
     */
    public boolean test( InputManager input );
}
