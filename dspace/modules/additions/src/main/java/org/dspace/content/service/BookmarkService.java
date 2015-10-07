package org.dspace.content.service;

import org.dspace.content.Bookmark;
import org.dspace.core.Context;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by tim on 06/10/15.
 */
public interface BookmarkService {
	Bookmark create(Context context, Bookmark bookmark) throws SQLException;
	Bookmark read(Context context, int id) throws SQLException;
	void update(Context context, Bookmark bookmark) throws SQLException;
	void delete(Context context, Bookmark bookmark) throws SQLException;
	List<Bookmark> findAll(Context context) throws SQLException;
}
