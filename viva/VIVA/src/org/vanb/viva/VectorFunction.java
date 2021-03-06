/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva;

import java.util.List;

import org.vanb.viva.utils.VIVAContext;

/** 
 * Interface for adding Cumulative Functions
 * 
 * @author vanb
 */
public interface VectorFunction extends Function
{
    
    /**
     * Run this Function.
     *
     * @param context the context
     * @param parameters the parameters
     * @return The result of running the Function.
     * @throws Exception the exception
     */
    public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception;
}
