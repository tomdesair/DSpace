package org.dspace.content.bookmark;

import org.dspace.authorize.service.AuthorizeService;
import org.dspace.content.Item;
import org.dspace.content.bookmark.dao.BookmarkDAO;
import org.dspace.content.bookmark.service.BookmarkService;
import org.dspace.core.Constants;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class BookmarkServiceImpl implements BookmarkService {
    @Autowired(required = true)
    protected BookmarkDAO bookmarkDAO;

    @Autowired(required = true)
    protected AuthorizeService authorizeService;

    public BookmarkServiceImpl() {

    }

    @Override
    public Bookmark create(Context context) throws SQLException {
        Bookmark bookmark = bookmarkDAO.create(context, new Bookmark());
        update(context, bookmark);
        return bookmark;
    }

    @Override
    public Bookmark read(Context context, int id) throws SQLException {
        return bookmarkDAO.findByID(context, Bookmark.class, id);
    }

    @Override
    public void update(Context context, Bookmark bookmark) throws SQLException {
        if (context.getCurrentUser().equals(bookmark.getCreator())) {
            bookmarkDAO.save(context, bookmark);
        }
    }

    @Override
    public void delete(Context context, Bookmark bookmark) throws SQLException {
        if (context.getCurrentUser().equals(bookmark.getCreator())) {
            bookmarkDAO.delete(context, bookmark);
        }
    }

    @Override
    public List<Bookmark> findAll(Context context) throws SQLException {
        return bookmarkDAO.findAll(context, Bookmark.class);
    }

    @Override
    public List<Bookmark> findByEperson(Context context, EPerson ePerson) throws SQLException {
        return bookmarkDAO.findBookmarksByEPerson(context, ePerson);
    }

    @Override
    public List<Bookmark> findByItem(Context context, Item item) throws SQLException {
        return bookmarkDAO.findBookmarksByItem(context, item);
    }
}
