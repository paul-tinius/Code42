package codefourtytwo.orgtool.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * @author P. Tinius
 */
public class OrganizationTest
{
    private static Organization o1;
    private static Organization o2;
    private static Organization o3;

    @Before
    public void setUp( ) throws Exception
    {
        o1 = new Organization( 20 );
        o1.setParentOrgId( 1 );
        o1.setName( "Organization name" );

        o2 = new Organization( 20 );
        o2.setParentOrgId( 1 );
        o2.setName( "Organization name" );

        o3 = new Organization( 21 );
        o3.setParentOrgId( 2 );
        o3.setName( "Not The Same" );
    }

    @Test
    public void equalsTest( ) throws Exception
    {
        Assert.assertTrue( o1.equals( o2 ) );
    }

    @Test
    public void equalsFalseTest( ) throws Exception
    {
        Assert.assertFalse( o1.equals( o3 ) );
    }

    @Test
    public void compareToTest( ) throws Exception
    {
        Assert.assertTrue( o1.compareTo( o2 ) == 0 );
    }

    @Test
    public void compareToNotTest( ) throws Exception
    {
        Assert.assertTrue( o1.compareTo( o3 ) != 0 );
    }
}