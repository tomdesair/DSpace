package org.dspace.authority.indexer;

import com.atmire.authority.AuthorityValue;

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
