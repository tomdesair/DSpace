package org.dspace.content.service;

import org.dspace.content.Bookmark;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface BookmarkService {
    public Bookmark create(Context context) throws SQLException;

    public Bookmark read(Context context, UUID id) throws SQLException;

    public void update(Context context, Bookmark bookmark) throws SQLException;

    public void delete(Context context, Bookmark bookmark) throws SQLException;

    public List<Bookmark> findAll(Context context) throws SQLException;

    public List<Bookmark> findByEperson(Context context, EPerson ePerson) throws SQLException;

    public List<Bookmark> findByItem(Context context, Item i) throws SQLException;
}
