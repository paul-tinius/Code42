package codefourtytwo.orgtool.util;

import org.junit.Assert;
import org.junit.Test;

/*
 * @author P. Tinius
 */
public class UtilTest
{
    @Test
    public void convertTest( ) throws Exception
    {
        Assert.assertEquals(1234, Util.convert( " 1234" ) );
    }

    @Test
    public void convertEmptyTest( ) throws Exception
    {
        Assert.assertEquals( -1, Util.convert( "" ) );
    }

    @Test
    public void convertEmptyStringTest( ) throws Exception
    {
        Assert.assertEquals( -1, Util.convert( " " ) );
    }

    @Test(expected = NullPointerException.class )
    public void convertNullTest( ) throws Exception
    {
        Util.convert( null );
    }
}