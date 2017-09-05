package codefourtytwo.orgtool.data;

import codefourtytwo.orgtool.api.Org;
import codefourtytwo.orgtool.api.OrgCollection;
import codefourtytwo.orgtool.model.Organization;
import codefourtytwo.orgtool.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author P. Tinius
 */
public class Organizations
    implements OrgCollection
{
    private final Set<Org> orgs;

    /**
     * default constructor
     */
    Organizations( ) { this.orgs = new CopyOnWriteArraySet<>( ); }

    @Override
    public Org getOrg( final int orgId ) { return findById( orgId, orgs ); }

    @Override
    public List<Org> getOrgTree( final int orgId,
                                 final boolean inclusive )
    {
        final List<Org> o = new ArrayList<>( );

        final Org org = getOrg( orgId );
        if( org != null )
        {
            o.add( org );

            if ( inclusive )
            {
                o.addAll( org.getChildOrgs( ) );
            }
        }

        return new ArrayList<>( orgs );
    }

    List<Org> getAllOrgs( ) { return new ArrayList<>( orgs ); }

    /**
     * @param orgId the organization id
     * @param parentOrgId the parent organization id
     * @param name the organization name
     */
    void add( final int orgId, final int parentOrgId, final String name )
    {
        final Organization organization = new Organization( orgId );
        organization.setParentOrgId( parentOrgId );
        organization.setName( name );

        add( organization );
    }
    
    private void add( final Organization organization )
    {
        // its a parent so add it
        if( organization.getParentOrgId( ) == 0 ) { orgs.add( organization ); }

        final Org parent = findById( organization.getParentOrgId( ), orgs );
        if( parent != null )
        {
            parent.getChildOrgs( ).add( organization );
            if( orgs.contains( organization ) ) { orgs.remove( organization ); }
        }
        else // parent not found, add to top level
        {
            orgs.add( organization );
        }

        orphan( );
    }

    private void orphan( )
    {
        orgs.stream( )
            .filter( org -> ( ( Organization ) org ).getParentOrgId( ) != 0 )
            .forEach( org ->
                      {
                          final Org childParent = findById( ( ( Organization ) org ).getParentOrgId( ), orgs );
                          if( childParent != null )
                          {
                              childParent.getChildOrgs( ).add( org );
                              orgs.remove( org );
                          }
                      });
    }

    /**
     * @param userId the user's id
     * @param orgId the organization id this user is part of
     * @param numFiles the number of files this user has
     * @param numBytes the number of bytes this user has
     */
    void addUser( final int userId, final int orgId, final int numFiles, final int numBytes )
    {
        Optional<Org> optional = Optional.ofNullable( findById( orgId, orgs ) );
        if( !optional.isPresent( ) )
        {
            for( final Org org : orgs )
            {
                optional = Optional.ofNullable( findById( orgId, new HashSet<>( org.getChildOrgs( ) ) ) );
                if( optional.isPresent( ) )
                {
                    break;
                }
            }
        }

        optional.ifPresent( org -> ( ( Organization ) org ).put( new User( userId, numFiles, numBytes ) ) );
    }

    private Org findById( final int orgId, final Set<Org> orgs )
    {
        for( final Org org : orgs )
        {
            final Organization organization = ( ( Organization ) org );
            if( organization.getOrgId( ) == orgId )
            {
                return org;
            }

            final Org matched = findById( orgId, new HashSet<>( org.getChildOrgs( ) ) );
            if( matched != null )
            {
                return matched;
            }
        }

        // organization no found
        return null;
    }

    @Override
    public String toString( )
    {
        return new StringJoiner( ", ", this.getClass( )
                                           .getSimpleName( ) + "[", "]" )
            .add( "orgs = " + orgs )
            .toString( );
    }
}
