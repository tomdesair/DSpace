/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.authority.indexer;

import org.dspace.authority.AuthorityValue;
import org.dspace.content.Item;
import org.dspace.core.Context;

/**
 * User: kevin (kevin at atmire.com)
 * Date: 10-dec-2010
 * Time: 15:16:39
 */
public interface AuthorityIndexerInterface {

    public void init(Context context, Item item);

    public void init(Context context);

    public AuthorityValue nextValue();

    public boolean hasMore();

    public void close();
}
