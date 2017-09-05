package codefourtytwo.orgtool.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/*
 * @author P. Tinius
 */
public class FileGenerator
{
    private static final String SEPARATOR = ",";

    private static boolean random = false;
    private static int orgCount = 3;
    private static int maxOrgChildCount = 5;
    private static int maxUserCount = 5;
    private static int maxOrgLevels = 3;
    private static int maxFileCount = 20000;
    private static int maxByteCount = Integer.MAX_VALUE - 10240;

    private static long userIdCounter = 1;
    private static int orgIdCounter = 1;
    private static Random randomGenerator = new Random( );

    private static BufferedWriter orgWriter = null;
    private static BufferedWriter userWriter = null;

    private static String dataPath = new java.io.File( "" ).getAbsolutePath( ) + "/test-data/";

    public static void main( String args[] )
    {
        generateData( "org-data-file.csv",
                      "user-data-file.csv",
                      random,
                      orgCount,
                      maxOrgChildCount,
                      maxUserCount,
                      5,
                      maxFileCount,
                      maxByteCount );
    }

    /**
     * @param randomVal whether or not the "max" values are used or if they are random
     * @param orgCountVal top level org count
     * @param maxOrgChildCountVal maximum number of org children for each org (0 to max unless random)
     * @param maxUserCountVal maximum number of users for each org (0 to max unless random)
     * @param maxOrgLevelsVal maximum number of org levels (0 to max unless random)
     * @param maxFileCountVal maximum number of files for each user (0 to max unless random)
     * @param maxByteCountVal maximum number of bytes (0 to max unless random)
     */
    private static void generateData( String orgDataFilename,
                                      String userDataFilename,
                                      boolean randomVal,
                                      int orgCountVal,
                                      int maxOrgChildCountVal,
                                      int maxUserCountVal,
                                      int maxOrgLevelsVal,
                                      int maxFileCountVal,
                                      int maxByteCountVal )
    {
        random = randomVal;
        orgCount = orgCountVal;
        maxOrgChildCount = maxOrgChildCountVal;
        maxUserCount = maxUserCountVal;
        maxOrgLevels = maxOrgLevelsVal;
        maxFileCount = maxFileCountVal;
        maxByteCount = maxByteCountVal;

        generateData( orgDataFilename, userDataFilename );

    }

    private static void generateData( String orgDataFilename,
                                      String userDataFilename )
    {
        Date startTime = new Date( );
        try( FileWriter orgfw = new FileWriter( new File(dataPath + orgDataFilename ) );
             FileWriter userfw = new FileWriter( new File(dataPath + userDataFilename ) ) )
        {
            orgWriter = new BufferedWriter( orgfw );
            userWriter = new BufferedWriter( userfw );

            for ( int o = 1; o <= orgCount + 1; o++ )
            {
                writeData( orgIdCounter, 0, 0 );
                orgIdCounter = orgIdCounter + 1;
            }
        }
        catch ( IOException ioEx )
        {
            System.err.println( "Unable to generate test data." );
            ioEx.printStackTrace( System.err );
        }
        finally
        {
            try
            {
                if ( orgWriter != null )
                {
                    orgWriter.close( );
                }
            }
            catch ( IOException ioEx )
            {
                // Nothing to do here.
            }
            try
            {
                if ( userWriter != null )
                {
                    userWriter.close( );
                }
            }
            catch ( IOException ioEx )
            {
                // Nothing to do here.
            }
        }

        Date endTime = new Date( );
        System.out.println( "DATA GENERATED:  " + ( endTime.getTime( ) - startTime.getTime( ) )
                            + "ms to process " + orgIdCounter + " orgs " + userIdCounter + " users " );
    }

    static void writeData( Integer orgId,
                           Integer parentOrgId,
                           Integer level )
        throws IOException
    {
        writeToOrgFile( orgIdCounter, parentOrgId );

        int userCount = random ? maxUserCount : randomGenerator.nextInt( maxUserCount + 1 );
        for ( int u = 1; u <= userCount; u++ )
        {
            int fileCount = random ? randomGenerator.nextInt( maxFileCount + 1 ) : maxFileCount;
            long byteCount = random ? ( long ) ( randomGenerator.nextDouble( ) * ( maxByteCount + 1 ) ) : maxByteCount;
            writeToUserFile( userIdCounter, orgId, fileCount, byteCount );
            userIdCounter = userIdCounter + 1;
        }


        int childCount = 0;
        if ( level <= maxOrgLevels )
        {
            childCount = random ? randomGenerator.nextInt( maxOrgChildCount + 1 ) : maxOrgChildCount;
        }

        int pId = parentOrgId;
        int indentLevel = level;
        for ( int c = 1; c <= childCount; c++ )
        {
            if ( c == 1 )
            {
                pId = orgId;
                indentLevel = indentLevel + 1;
            }

            orgIdCounter = orgIdCounter + 1;
            writeData( orgIdCounter, pId, indentLevel );
        }
    }

    private static void writeToOrgFile( int orgId,
                                        int parentOrgId ) throws IOException
    {
        orgWriter.append( String.valueOf( orgIdCounter ) )
                 .append( SEPARATOR )
                 .append( String.valueOf( parentOrgId == 0 ? "null" : parentOrgId ) )
                 .append( SEPARATOR )
                 .append( "Org" )
                 .append( String.valueOf( orgId ) );
        orgWriter.newLine( );
        orgWriter.flush( );
    }

    private static void writeToUserFile( long userIdCounter,
                                         int orgId,
                                         int fileCount,
                                         long byteCount ) throws IOException
    {
        userWriter.append( String.valueOf( userIdCounter ) )
                  .append( SEPARATOR )
                  .append( String.valueOf( orgId ) )
                  .append( SEPARATOR )
                  .append( String.valueOf( fileCount ) )
                  .append( SEPARATOR )
                  .append( String.valueOf( byteCount ) );
        userWriter.newLine( );
        userWriter.flush( );
    }
}
