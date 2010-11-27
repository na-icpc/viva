package org.vanb.viva;

import org.vanb.viva.parser.*;

import java.io.*;

public class VIVA
{    
    public static String dir = "./";

    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        PatternParser parser = new PatternParser( new FileInputStream( dir + "test.pattern" ) );
        parser.multilinePattern();
    }

}
