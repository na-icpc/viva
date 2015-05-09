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
     * @param list of rows of parameter values
     * @return The result of running the Function.
     */
    public Object run( VIVAContext context, List<List<Object>> parameters ) throws Exception;
}
