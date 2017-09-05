package codefourtytwo.orgtool;

import codefourtytwo.orgtool.data.DataLoader;
import codefourtytwo.orgtool.data.DataOuter;

import java.io.IOException;
import java.nio.file.Paths;

/*
 * @author P. Tinius
 */
public class Main
{
    public static void main( String[] args ) throws IOException
    {
        // TODO add command line processing to allow organization and user data files to be specified
        new DataOuter( new DataLoader( Paths.get( "test-data/small-org-data-file.csv" ),
                                       Paths.get( "test-data/small-user-data-file.csv" ) ).loadData( ),
                       Paths.get( "test-data/organizations-output.txt" ) ).dumpData( );
    }
}
