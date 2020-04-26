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
     * @param context the context
     * @param parameters the parameters
     * @return The result of running the Function.
     * @throws Exception the exception
     */
    public Object run( VIVAContext context, List<Object> parameters ) throws Exception;
}
