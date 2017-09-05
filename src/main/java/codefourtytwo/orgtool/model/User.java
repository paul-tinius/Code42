package codefourtytwo.orgtool.model;

import java.util.Objects;
import java.util.StringJoiner;

/*
 * @author P. Tinius
 */
public class User
{
    private final int uid;
    private final int numFiles;
    private final int numBytes;

    /**
     * @param uid the user id
     * @param numFiles the number of files associated with this user
     * @param numBytes the number of bytes associated with this user
     */
    public User( final int uid,
                 final int numFiles,
                 final int numBytes )
    {
        this.uid = uid;
        this.numFiles = numFiles;
        this.numBytes = numBytes;
    }

    /**
     * @return Returns the user id
     */
    public int getUid( )
    {
        return uid;
    }

    /**
     * @return Returns the number of files
     */
    public int getNumFiles( )
    {
        return numFiles;
    }

    /**
     * @return Returns the number of bytes
     */
    public int getNumBytes( )
    {
        return numBytes;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass( ) != o.getClass( ) ) return false;

        User that = ( User ) o;

        return Objects.equals( this.numBytes, that.numBytes ) &&
               Objects.equals( this.numFiles, that.numFiles ) &&
               Objects.equals( this.uid, that.uid );
    }

    @Override
    public int hashCode( )
    {
        return Objects.hash( numBytes, numFiles, uid );
    }

    @Override
    public String toString( )
    {
        return new StringJoiner( ", ", this.getClass( )
                                           .getSimpleName( ) + "[", "]" )
            .add( "numBytes = " + numBytes )
            .add( "numFiles = " + numFiles )
            .add( "uid = " + uid )
            .toString( );
    }
}
