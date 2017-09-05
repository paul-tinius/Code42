package codefourtytwo.orgtool.model;

import codefourtytwo.orgtool.api.Org;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/*
 * @author P. Tinius
 */
public class Organization
    implements Comparable<Organization>,
               Org
{
    private final int id;                           // organization id
    private final List<User> users;                 // users in this organization
    private final List<Org> children;               // child organization

    private int parentOrgId = 0;                    // parent organization id
    private String name;                            // organization name

    /**
     * @param id the organization id
     */
    public Organization( final int id )
    {
        this.id = id;

        this.children = new ArrayList<>( );
        this.users = new ArrayList<>( );
    }

    /**
     * @return Returns the organization id
     */
    public int getOrgId( ) { return id; }

    /**
     * @return Returns the parent organization id
     */
    public int getParentOrgId( ) { return parentOrgId; }
    public void setParentOrgId( final int parentOrgId ) { this.parentOrgId = parentOrgId; }

    /**
     * @return Returns the organizational name
     */
    public String getName( ) { return name; }
    public void setName( final String name )  { this.name = name; }

    /**
     * @return Returns sorted set of organization users
     */
    public List<User> getUsers( ) { return users; }

    /**
     * @return Returns the immediate children of this organization
     */
    @Override
    public List<Org> getChildOrgs( )
    {
        return children;
    }

    /**
     * @return Returns the total number of files in this organization and any children
     */
    @Override
    public int getTotalNumUsers( ) { return getUsers( ).size( ); }

    /**
     * @return Returns the total number of files in this organization and any children
     */
    @Override
    public int getTotalNumFiles( )
    {
        final int userFiles = getUsers( ).stream( )
                                         .mapToInt( User::getNumFiles )
                                         .sum( );
        final int[ ] childrenFiles = new int[ 1 ];

        children.forEach( child -> childrenFiles[ 0 ] = ( ( Organization ) child ).getUsers( )
                                                                                  .stream( )
                                                                                  .mapToInt( User::getNumFiles )
                                                                                  .sum( ) );
        return userFiles + childrenFiles[ 0 ];
    }

    /**
     * @return Returns the total number of bytes in this organization and any children
     */
    @Override
    public int getTotalNumBytes( )
    {
        final int userBytes = getUsers( ).stream( )
                                         .mapToInt( User::getNumBytes )
                                         .sum( );
        final int[ ] childrenBytes = new int[ 1 ];

        children.forEach( child -> childrenBytes[ 0 ] = ( ( Organization ) child ).getUsers( )
                                                                                  .stream( )
                                                                                  .mapToInt( User::getNumBytes )
                                                                                  .sum( ) );
        return userBytes + childrenBytes[ 0 ];
    }

    /**
     * @param user the user belonging to this organization
     */
    public void put( final User user ) { users.add( user ); }

    /**
     * @param child the child organization for this organization
     */
    public void put( final Organization child ) { children.add( child ); }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass( ) != o.getClass( ) ) return false;

        Organization that = ( Organization ) o;

        return Objects.equals( this.getOrgId(), that.getOrgId() ) &&
               Objects.equals( this.getName(), that.getName() ) &&
               Objects.equals( this.getParentOrgId(), that.getParentOrgId() );
    }

    @Override
    public int compareTo( final Organization other ) { return Integer.compare( getOrgId( ), other.getOrgId( ) ); }

    @Override
    public int hashCode( ) { return Objects.hash( id ); }

    public String dump( )
    {
        return new StringJoiner( ", " ).add( "orgId = " + getOrgId( ) )
                                                .add( "parentId = " + getParentOrgId( ) )
                                                .toString( );
    }

    @Override
    public String toString( )
    {
        return new StringJoiner( ", " ).add( Integer.toString( getOrgId( ) ) )
                                                .add( Integer.toString( getTotalNumUsers( ) ) )
                                                .add( Integer.toString( getTotalNumFiles( ) ) )
                                                .add( Integer.toString( getTotalNumBytes( ) ) )
                                                .toString( );
    }
}
