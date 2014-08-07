/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.authority.indexer;

import org.dspace.authority.AuthorityValue;

/**
 * User: kevin (kevin at atmire.com)
 * Date: 7-dec-2010
 * Time: 10:12:54
 */
public interface AuthorityIndexingService {


    public void indexContent(AuthorityValue value, boolean force);

    public void cleanIndex() throws Exception;

    public void commit();

}
