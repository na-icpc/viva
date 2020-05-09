/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.graphs;

/**
 * Base class for VIVA Graph objects.
 */
public class Base
{
    
    /** The object's id. */
    protected String id;
    
    /** Extra stuff for some algorithms. */
    protected Object extras;
        
    /**
     * Instantiates a new object.
     *
     * @param id the id
     */
    public Base( String id )
    {
        this.id = id;
    }

    /**
     * Equals.
     *
     * @param other the other
     * @return true, if successful
     */
    @Override
    public boolean equals( Object other )
    {
        boolean result = true;;
        
        if( other==null ) result = false;
        else if( other.getClass()!=this.getClass() ) result = false;
        else if( !((Base)other).id.equals(this.id)) result = false;
        
        return result;
    }
    
    /**
     * Hash code.
     * 
     * We'll match objects by their IDs, 
     * so the hash code should be the hash code of the ID.
     * That way, HashSets and HashMaps will work correctly.
     *
     * @return the hash code of the id.
     */
    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getID()
    {
        return id;
    }

    /**
     * Sets the extras.
     *
     * @param extras the new extras
     * @return what was just set
     */
    public Object setExtras( Object extras )
    {
        this.extras = extras;
        return extras;
    }

    /**
     * Gets the extras.
     *
     * @return the extras
     */
    public Object getExtras()
    {
        return extras;
    } 
    
    /**
     * Gets the extras.
     *
     * @param extras Default extras if current extras==null
     * @return the extras
     */
    public Object getExtras( Object extras )
    {
        if( this.extras==null ) this.extras = extras;
        return this.extras;
    } 
}
