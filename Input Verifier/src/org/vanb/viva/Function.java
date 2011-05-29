package org.vanb.viva;

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
     * This method should return null instead of a type if the parameters aren't right.
     * 
     * @param params Parameter types
     * @return return type
     */
    Class<?> getReturnType( Class<?> params[] );
    
    /**
     * How do you use this Function?
     * 
     * @return A description of how to use this Function
     */
    String getUsage();
}