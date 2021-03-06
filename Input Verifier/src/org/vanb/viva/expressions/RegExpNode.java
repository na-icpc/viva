package org.vanb.viva.expressions;

import org.vanb.viva.parser.*;
import org.vanb.viva.utils.*;
import java.util.regex.*;

public class RegExpNode extends BinaryOperatorNode
{    
    private java.util.regex.Pattern pattern;

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
            pattern = java.util.regex.Pattern.compile( token );
        }
        catch( Exception e )
        {
            throw new ParseException( "Bad right operand to " + operator + " operator: Regexp doesn't compile" );            
        }
    }

    @Override
    public Object evaluate( VIVAContext context ) throws VIVAException
    {
        Matcher matcher = pattern.matcher( (String)left.evaluate( context ) );
        return new Boolean( matcher.matches() );
    }

    @Override
    public Class<?> getReturnType()
    {
        return Boolean.class;
    }

}
