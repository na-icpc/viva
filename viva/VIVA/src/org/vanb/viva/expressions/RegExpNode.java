/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
package org.vanb.viva.expressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vanb.viva.parser.ParseException;
import org.vanb.viva.utils.VIVAContext;
import org.vanb.viva.utils.VIVAException;

/**
 * The Class RegExpNode.
 */
public class RegExpNode extends BinaryOperatorNode
{    
    
    /** The pattern. */
    private Pattern pattern;

    /**
     * Instantiate.
     *
     * @param lhs the lhs
     * @param rhs the rhs
     * @throws ParseException the parse exception
     */
    @Override
    public void instantiate( ExpressionNode lhs, ExpressionNode rhs )
            throws ParseException
    {
        right = rhs;
        left = lhs;
        
        if( !lhs.getReturnType().equals( String.class  ) )
        {
            throw new ParseException( "Bad left operand to " + operator + " operator: Expecting String, got " + lhs.getReturnType().getSimpleName() );
        }
        
        if( !rhs.getReturnType().equals( String.class  ) )
        {
            throw new ParseException( "Bad right operand to " + operator + " operator: Expecting String, got " + rhs.getReturnType().getSimpleName() );
        }
        
        if( !(rhs instanceof ConstantNode) )
        {
            throw new ParseException( "Bad right operand to " + operator + " operator: Must be a Constant" );
        }
        
        try
        {
            // A bit of an eccentricity here - the parser follows Java conventions for
            // string constants, meaning that any backslash has to be doubled.
            // Java turns double backslashes into a single backslash. So,
            // we've got to do that, too.
            String token = ((String)rhs.evaluate( null )).replace( "\\\\", "\\" );
            pattern = Pattern.compile( token );
        }
        catch( Exception e )
        {
            throw new ParseException( "Bad right operand to " + operator + " operator: Regexp doesn't compile" );            
        }
    }

    /**
     * Evaluate.
     *
     * @param context the context
     * @return the object
     * @throws VIVAException the VIVA exception
     */
    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Matcher matcher = pattern.matcher( (String)left.evaluate( context ) );
        return new Boolean( matcher.matches() );
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public Class<?> getReturnType()
    {
        return Boolean.class;
    }

}
