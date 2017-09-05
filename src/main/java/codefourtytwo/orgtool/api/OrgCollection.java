package codefourtytwo.orgtool.api;

import java.util.List;

/*
 * @author P. Tinius
 */
public interface OrgCollection
{
    /**
     * @param orgId the organization id
     *
     * @return Returns the organization associated with the supplied {@code orgId}
     */
    Org getOrg( final int orgId );

    /**
     * @param inclusive
     *
     * @return Returns a list of organization below this node in the tree;
     */
    List<Org> getOrgTree( final int orgId, final boolean inclusive );
}
