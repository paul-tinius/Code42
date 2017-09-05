package codefourtytwo.orgtool.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * @author P. Tinius
 */
public class UserTest
{
    private static User o1;
    private static User o2;
    private static User o3;

    @Before
    public void setUp( ) throws Exception
    {
        o1 = new User( 1, 10, 1024 );
        o2 = new User( 1, 10, 1024 );
        o3 = new User( 1 + 1, 10 * 2, 1024 * 4 );
    }

    @Test
    public void equalsTest( ) throws Exception
    {
        Assert.assertTrue( o1.equals( o2 ) );
    }

    @Test
    public void equalsNotTest( ) throws Exception
    {
        Assert.assertFalse( o1.equals( o3 ) );
    }
}