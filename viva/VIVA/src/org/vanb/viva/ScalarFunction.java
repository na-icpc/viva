package org.vanb.viva;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

/** 
 * Interface for adding Inline Functions
 * 
 * @author vanb
 */
public interface ScalarFunction extends Function
{
    /**
     * Run this Function.
     * 
     * @param parameters
     * @return The result of running the Function.
     */
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception;
}
