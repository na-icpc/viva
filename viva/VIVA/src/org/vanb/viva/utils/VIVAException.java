/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.utils;

/**
 * A VIVA Exception.
 * 
 * @author vanb
 */
public class VIVAException extends Exception
{
    /** Some strange thing needed for serialization */
    private static final long serialVersionUID = 7029393277012526494L;
    
    /** The root cause, if it was a Java Exception */
    private Exception cause;
    
    /** Where the problem happened in the input file */
    private int line, place;
    
    /**
     * Create a VIVAException.
     * 
     * @param message Error message
     * @param l Line number
     * @param p Column number in the line
     */
    public VIVAException( String message, int l, int p )
    {
        super( message );
        line = l;
        place = p;
        cause = null;
    }
    
    /**
     * Create a VIVAException that was caused by another Exception.
     * 
     * @param message VIVA message
     * @param l Line number
     * @param p Column number within the line
     * @param c Causing Exception
     */
    public VIVAException( String message, int l, int p, Exception c  )
    {
        this( message, l, p );
        cause = c;
    }
    
    /**
     * Get the line number where the problem happened.
     * 
     * @return Line number
     */
    public int getLine()
    {
        return line;
    }
    
    /**
     * Get the column number within the line where the problem happened.
     * 
     * @return Column number
     */
    public int getPlace()
    {
        return place;
    }
    
    /**
     * Get the root cause of the VIVAException.
     * 
     * @return Root cause
     */
    @Override
    public Exception getCause()
    {
        return cause;
    }
}
