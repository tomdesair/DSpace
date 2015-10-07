package org.dspace.util;

import org.dspace.content.Bookmark;
import org.dspace.content.factory.ContentServiceFactory;
import org.dspace.content.service.BookmarkService;
import org.dspace.core.Context;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by tim on 06/10/15.
 */
public class BookmarkUpdater {
	protected BookmarkService bookmarkService;

	protected BookmarkUpdater() {
		bookmarkService = ContentServiceFactory.getInstance().getBookmarkService();
	}

	public void doUpdate() throws SQLException {
		Context ctx = new Context();
		Bookmark bookmark = new Bookmark();
		bookmark.setDateCreated(new Date());
		bookmark.setCreator(null);
		bookmark.setItem(null);
		bookmark.setTitle("Test bookmark");

		bookmarkService.create(ctx, bookmark);
	}

	public static void main(String... args) {
		BookmarkUpdater bmu = new BookmarkUpdater();

		try {
			bmu.doUpdate();
		}
		catch(SQLException sqle) {
			System.err.println(sqle.getLocalizedMessage());
			sqle.printStackTrace();
		}
	}
}
