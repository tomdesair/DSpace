package org.dspace.authenticate.dao;

import org.dspace.authenticate.PersistentLogin;
import org.dspace.core.Context;
import org.dspace.core.GenericDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public interface PersistentLoginDAO extends GenericDAO<PersistentLogin>{

    public List<PersistentLogin> findByToken(Context context, String token) throws SQLException;

    public List<PersistentLogin> findByName(Context context, String name) throws SQLException;


}
