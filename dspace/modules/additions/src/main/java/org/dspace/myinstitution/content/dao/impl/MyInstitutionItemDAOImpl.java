package org.dspace.myinstitution.content.dao.impl;

import org.dspace.content.Item;
import org.dspace.content.dao.impl.ItemDAOImpl;
import org.dspace.core.Context;
import org.dspace.myinstitution.content.dao.MyInstitutionItemDAO;
import org.hibernate.Query;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public class MyInstitutionItemDAOImpl extends ItemDAOImpl implements MyInstitutionItemDAO {
    @Override
    public Iterator<Item> findAllDiscoverable(Context context) throws SQLException {
        Query query = createQuery(context, "FROM Item where discoverable=true");
        return iterate(query);
    }
}
