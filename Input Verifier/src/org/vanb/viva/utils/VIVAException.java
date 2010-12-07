package org.vanb.viva.utils;

public class VIVAException extends Exception
{
    private static final long serialVersionUID = 7029393277012526494L;
    private Exception cause;
    private int line, place;
    
    public VIVAException( String message, int l, int p )
    {
        super( message );
        line = l;
        place = p;
        cause = null;
    }
    
    public VIVAException( String message, int l, int p, Exception c  )
    {
        this( message, l, p );
        cause = c;
    }
    
    public int getLine()
    {
        return line;
    }
    
    public int getPlace()
    {
        return place;
    }
    
    public Exception getCause()
    {
        return cause;
    }
}
