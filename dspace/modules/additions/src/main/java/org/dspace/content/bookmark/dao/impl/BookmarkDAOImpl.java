package org.dspace.content.bookmark.dao.impl;

import org.dspace.content.bookmark.Bookmark;
import org.dspace.content.Item;
import org.dspace.content.bookmark.dao.BookmarkDAO;
import org.dspace.core.AbstractHibernateDAO;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

public class BookmarkDAOImpl extends AbstractHibernateDAO<Bookmark> implements BookmarkDAO {

    @Override
    public List<Bookmark> findBookmarksByEPerson(Context context, EPerson ep) throws SQLException {
        Criteria criteria = createCriteria(context,Bookmark.class);
        criteria.add(Restrictions.and(Restrictions.eq("creator",ep)));
        return list(criteria);
    }

    @Override
    public List<Bookmark> findBookmarksByItem(Context context, Item item) throws SQLException {
        Query query = createQuery(context, "from Bookmark where item = :item order by date_created");
        query.setParameter("item", item);
        return list(query);
    }

}
