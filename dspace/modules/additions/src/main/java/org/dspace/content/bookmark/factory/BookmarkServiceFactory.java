package org.dspace.content.bookmark.factory;

import org.dspace.content.bookmark.service.BookmarkService;
import org.dspace.utils.DSpace;

/**
 * Abstract factory to get services for the content.bookmark package, use BookmarkServiceFactory.getInstance() to retrieve an implementation
 *
 * Created by jonas - jonas@atmire.com on 02/11/15.
 */
public abstract class BookmarkServiceFactory {

    public abstract BookmarkService getBookmarkService();

    public static BookmarkServiceFactory getInstance(){
        return new DSpace().getServiceManager().getServiceByName("bookmarkServiceFactory", BookmarkServiceFactory.class);
    }
}
