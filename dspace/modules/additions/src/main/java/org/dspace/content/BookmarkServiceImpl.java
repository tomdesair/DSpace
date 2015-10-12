package org.dspace.content;

import org.dspace.content.dao.BookmarkDAO;
import org.dspace.content.service.BookmarkService;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


public class BookmarkServiceImpl implements BookmarkService {
	@Autowired(required = true)
	protected BookmarkDAO bookmarkDAO;

	public BookmarkServiceImpl() {

	}

	@Override
	public Bookmark create(Context context) throws SQLException {
		return bookmarkDAO.create(context, new Bookmark());
	}

    @Override
	public Bookmark read(Context context, UUID id) throws SQLException {
		return bookmarkDAO.findByID(context, Bookmark.class, id);
	}

	@Override
	public void update(Context context, Bookmark bookmark) throws SQLException {
		bookmarkDAO.save(context, bookmark);
	}

	@Override
	public void delete(Context context, Bookmark bookmark) throws SQLException {
		bookmarkDAO.delete(context, bookmark);
	}

	@Override
	public List<Bookmark> findAll(Context context) throws SQLException {
		return bookmarkDAO.findAll(context, Bookmark.class);
	}

    @Override
    public List<Bookmark> findByEperson(Context context, EPerson ePerson) throws SQLException {
        return bookmarkDAO.findBookmarksByEPerson(context,ePerson);
    }

    @Override
    public List<Bookmark> findByItem(Context context, Item i) throws SQLException {
        return bookmarkDAO.findBookmarksByItem(context,i);
    }
}
