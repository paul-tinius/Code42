package codefourtytwo.orgtool.util;

import java.util.Objects;

/*
 * @author P. Tinius
 */
public class Util
{
    private static boolean isDigit( final String string )
    {
        return ( string != null ) && !string.isEmpty( ) && string.matches( "\\d+" );
    }

    public static int convert( final String string )
    {
        final String string2convert = string.replace( " ", "" );
        return isDigit( Objects.requireNonNull( string2convert ) ) ? Integer.parseInt( string2convert ) : -1;
    }
}
