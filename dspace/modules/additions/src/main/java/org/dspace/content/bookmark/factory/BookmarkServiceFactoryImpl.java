package org.dspace.content.bookmark.factory;

import org.dspace.content.bookmark.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jonas - jonas@atmire.com on 02/11/15.
 */
public class BookmarkServiceFactoryImpl extends BookmarkServiceFactory {


    @Autowired(required = true)
    private BookmarkService bookmarkService;

    @Override
    public BookmarkService getBookmarkService() {
        return bookmarkService;
    }
}
