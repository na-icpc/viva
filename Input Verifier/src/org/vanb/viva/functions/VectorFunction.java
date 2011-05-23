package org.vanb.viva.functions;

import java.util.*;
import org.vanb.viva.utils.*;

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
    Object run( VIVAContext context, List<List<Object>> parameters );
}
