package org.vanb.viva.functions;

import java.util.*;
import org.vanb.viva.utils.*;

/** 
 * Interface for adding Inline Functions
 * 
 * @author vanb
 */
public interface ScalarFunction extends Function
{
    /**
     * Run this function.
     * 
     * @param parameters
     * @return The result of running the Function.
     */
    Object run( VIVAContext context, List<Object> parameters );
}
