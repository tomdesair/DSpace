package org.dspace.myinstitution.content.service;

import org.dspace.content.Item;
import org.dspace.content.service.ItemService;
import org.dspace.core.Context;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public interface MyInstitutionItemService extends ItemService {

    public Iterator<Item> findAllDiscoverable(Context context) throws SQLException;

}
