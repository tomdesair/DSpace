package org.dspace.content.bookmark.service;

import org.dspace.content.bookmark.Bookmark;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;

import java.sql.SQLException;
import java.util.List;

public interface BookmarkService {
    public Bookmark create(Context context) throws SQLException;

    public Bookmark read(Context context, int id) throws SQLException;

    public void update(Context context, Bookmark bookmark) throws SQLException;

    public void delete(Context context, Bookmark bookmark) throws SQLException;

    public List<Bookmark> findAll(Context context) throws SQLException;

    public List<Bookmark> findByEperson(Context context, EPerson ePerson) throws SQLException;

    public List<Bookmark> findByItem(Context context, Item item) throws SQLException;
}
