package codefourtytwo.orgtool.data;

import codefourtytwo.orgtool.api.Org;
import codefourtytwo.orgtool.api.OrgCollection;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;

/**
 * @author P. Tinius
 */
public class DataOuter
{
    private final OrgCollection orgCollection;
    private final Path outfile;

    public DataOuter( final OrgCollection orgCollection, final Path outfile )
    {
        this.orgCollection = orgCollection;
        this.outfile = outfile;
    }

    public void dumpData( ) throws FileNotFoundException
    {
        try ( PrintStream printStream = new PrintStream( outfile.toFile( ) ) )
        {
            List<Org> list = ( ( Organizations ) orgCollection ).getAllOrgs( );
            list.forEach( parent ->
                          {
                              printStream.printf( "%s%n", parent.toString( ) );
                              printStream.flush( );

                              if( !parent.getChildOrgs( ).isEmpty( ) )
                              {
                                  dumpChildren( printStream, parent.getChildOrgs( ), "\t" );
                              }
                          } );
        }
    }

    private void dumpChildren( final PrintStream printStream, final List<Org> children, final String prefix )
    {
        children.forEach( child ->
                        {
                            printStream.printf( "%s%s%n", prefix, child.toString( ) );
                            printStream.flush( );

                            if( !child.getChildOrgs( ).isEmpty( ) )
                            {
                                dumpChildren( printStream, child.getChildOrgs( ), prefix + '\t' );
                            }
                        } );
    }
}
