package org.dspace.myinstitution.content.dao;

import org.dspace.content.Item;
import org.dspace.content.dao.ItemDAO;
import org.dspace.core.Context;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public interface MyInstitutionItemDAO extends ItemDAO {

    public Iterator<Item> findAllDiscoverable(Context context) throws SQLException;

}
