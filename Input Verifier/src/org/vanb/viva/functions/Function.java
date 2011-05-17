package org.vanb.viva.functions;

import java.util.*;

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
     * What parameters is this function expecting?
     * 
     * @return List of parameter types this Function expects
     */
    Class<?>[] parameters();
    
    /**
     * Run this function.
     * 
     * @param parameters
     * @return The result of running the Function.
     */
    Object run( List<Object> parameters );
}
