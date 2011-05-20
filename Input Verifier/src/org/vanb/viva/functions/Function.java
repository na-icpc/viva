package org.vanb.viva.functions;

import java.util.*;
import org.vanb.viva.utils.*;

/** 
 * Interface for adding Functions
 * 
 * @author vanb
 */
public interface Function
{
    /**
     * This Function's name
     * 
     * @return the name
     */
    String getName();
    
    /** 
     * Given a list of parameter types, what will this Function return?
     * 
     * @param params Parameter types
     * @return return type
     */
    Class<?> returnType( Class<?> params[] );
    
    /**
     * How do you use this Function?
     * 
     * @return A description of how to use this Function
     */
    String usage();
    
    /**
     * Run this function.
     * 
     * @param parameters
     * @return The result of running the Function.
     */
    Object run( VIVAContext context, List<Object> parameters );
}
