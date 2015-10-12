package org.dspace.content.dao.impl;

import org.dspace.content.Bookmark;
import org.dspace.content.Item;
import org.dspace.content.dao.BookmarkDAO;
import org.dspace.core.AbstractHibernateDAO;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.hibernate.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BookmarkDAOImpl extends AbstractHibernateDAO<Bookmark> implements BookmarkDAO {

    @Override
    public List<Bookmark> findBookmarksByEPerson(Context context, EPerson ep) throws SQLException {
        Query query = createQuery(context, "from Bookmark where creator = :creator order by date_created");
        query.setParameter("creator", ep);
        return list(query);
    }

    @Override
    public List<Bookmark> findBookmarksByItem(Context context, Item i) throws SQLException {
        Query query = createQuery(context, "from Bookmark where item = :item order by date_created");
        query.setParameter("item", i);
        return list(query);
    }

}
