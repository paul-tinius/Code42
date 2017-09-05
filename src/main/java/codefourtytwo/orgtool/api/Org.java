package codefourtytwo.orgtool.api;

import java.util.List;

/*
 * @author P. Tinius
 */
public interface Org
{
    /**
     * @return Returns the immediate children of this organization
     */
    List<Org> getChildOrgs( );

    /**
     * @return Returns the total number of files in this organization and any children
     */
    int getTotalNumUsers( );

    /**
     * @return Returns the total number of files in this organization and any children
     */
    int getTotalNumFiles( );

    /**
     * @return Returns the total number of bytes in this organization and any children
     */
    int getTotalNumBytes( );
}
