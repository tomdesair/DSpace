package org.dspace.content;

import org.dspace.content.dao.BookmarkDAO;
import org.dspace.content.service.BookmarkService;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by tim on 06/10/15.
 */
public class BookmarkServiceImpl implements BookmarkService {
	@Autowired(required = true)
	protected BookmarkDAO bookmarkDAO;

	public BookmarkServiceImpl() {

	}

	@Override
	public Bookmark create(Context context, Bookmark bookmark) throws SQLException {
		return bookmarkDAO.create(context, bookmark);
	}

	@Override
	public Bookmark read(Context context, int id) throws SQLException {
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
}
