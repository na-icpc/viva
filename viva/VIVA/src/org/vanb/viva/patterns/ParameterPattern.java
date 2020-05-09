/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.patterns;

import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class ParameterPattern.
 */
public class ParameterPattern implements Pattern
{
    
    /** The name. */
    String name;
    
    /** The value. */
    Object value;
    

    
    /**
     * Instantiates a new parameter pattern.
     *
     * @param name the name
     * @param value the value
     */
    public ParameterPattern(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Test.
     *
     * @param context the context
     * @return true, if successful
     * @throws VIVAException the VIVA exception
     */
    @Override
    public boolean test( VIVAContext context ) throws VIVAException
    {
        context.setParameter( name, value );
        return true;
    }

}
