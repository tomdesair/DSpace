package org.dspace.content.dao;

import org.dspace.content.Bookmark;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.core.GenericDAO;
import org.dspace.eperson.EPerson;

import java.sql.SQLException;
import java.util.List;

public interface BookmarkDAO extends GenericDAO<Bookmark> {

    public List<Bookmark> findBookmarksByEPerson(Context context, EPerson ep) throws SQLException;

    public List<Bookmark> findBookmarksByItem(Context context, Item i) throws SQLException;

}
