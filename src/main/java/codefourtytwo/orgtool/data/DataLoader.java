package codefourtytwo.orgtool.data;

import codefourtytwo.orgtool.api.OrgCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static codefourtytwo.orgtool.util.Util.convert;

/**
 * @author P. Tinius
 */
public class DataLoader
{
    private final Path organizationData;
    private final Path userData;

    /**
     * @param organizationData the organization data file
     * @param userData the organization user data file
     */
    public DataLoader( final Path organizationData, final Path userData )
    {
        this.organizationData = organizationData;
        this.userData = userData;
    }

    public OrgCollection loadData( ) throws IOException
    {
        final CSVParser csv = new CSVParser( );

        OrgCollection organizations = loadOrganizationData( csv );
        loadOrganizationUserData( csv, organizations );
        return organizations;
    }

    private void loadOrganizationUserData( final CSVParser csv,
                                           OrgCollection organizations )
        throws IOException
    {
        Files.lines( userData )
             .forEach( line ->
                       {
                           if ( !line.startsWith( "#" ) )
                           {
                               final String[] parts = csv.parse( line );
                               if ( parts.length == 4 )
                               {
                                   // userId,orgId,numFiles,numBytes
                                   ( ( Organizations ) organizations ).addUser( convert( parts[ 0 ] ),
                                                                                convert( parts[ 1 ] ),
                                                                                convert( parts[ 2 ] ),
                                                                                convert( parts[ 3 ] ) );
                               }
                               // skip badly formatted lines
                           }
                       } );
    }

    private OrgCollection loadOrganizationData( final CSVParser csv )
        throws IOException
    {
        Organizations organizations = new Organizations( );

        Files.lines( organizationData )
             .forEach( ( String line ) ->
                       {
                           if ( !line.startsWith( "#" ) )
                           {
                               final String[] parts = csv.parse( line );
                               if ( parts.length == 3 )
                               {
                                   organizations.add( convert( parts[ 0 ] ),
                                                      parts[ 1 ].equalsIgnoreCase( "null" )
                                                      ? 0 : convert( parts[ 1 ] ),
                                                      parts[ 2 ] );
                               }
                               // skip badly formatted lines
                           }
                       } );

        return organizations;
    }

    private static class CSVParser
    {
        /*
         * This Pattern will match on either quoted text or text between commas, including
         * whitespace, and accounting for beginning and end of line.
         */
        private final Pattern csvPattern = Pattern.compile( "\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)" );
        private ArrayList<String> allMatches = null;
        private Matcher matcher = null;

        CSVParser( )
        {
            allMatches = new ArrayList<>( );
            matcher = null;
        }

        String[] parse( String csvLine )
        {
            matcher = csvPattern.matcher( csvLine );
            allMatches.clear( );
            String matchX;
            while ( matcher.find( ) )
            {
                matchX = matcher.group( 1 );
                if ( matchX != null )
                {
                    allMatches.add( matchX );
                }
                else
                {
                    allMatches.add( matcher.group( 2 ) );
                }
            }

            final int size = allMatches.size( );
            if ( size > 0 )
            {
                return allMatches.toArray( new String[ size ] );
            }
            else
            {
                return new String[ 0 ];
            }
        }
    }
}
