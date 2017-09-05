package codefourtytwo.orgtool.data;

import codefourtytwo.orgtool.api.OrgCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

/*
 * @author P. Tinius
 */
public class DataLoaderTest
{
    private static OrgCollection collection;

    private static final String ORG_DATA = "test-data/small-org-data-file.csv";
    private static final String ORG_USER_DATA = "test-data/small-user-data-file.csv";

    @Before
    public void setUp( ) throws Exception
    {
        collection = new DataLoader( Paths.get( ORG_DATA ), Paths.get( ORG_USER_DATA ) ).loadData( );
    }

    @Test
    public void loadDataTest( ) throws Exception
    {
        Assert.assertEquals( "1, 2, 160, 1600", collection.getOrg( 1 ).toString( ) );
    }

    @Test
    public void loadDataNullTest( ) throws Exception
    {
        Assert.assertNull( collection.getOrg( 234 ) );
    }
}