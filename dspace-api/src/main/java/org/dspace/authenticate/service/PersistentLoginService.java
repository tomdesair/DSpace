package org.dspace.authenticate.service;

import org.dspace.authenticate.PersistentLogin;
import org.dspace.authorize.AuthorizeException;
import org.dspace.core.Context;
import org.dspace.service.DSpaceCRUDService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public interface PersistentLoginService extends DSpaceCRUDService<PersistentLogin> {

    public PersistentLogin create(Context context, String username, String series, String token, Date date) throws SQLException, AuthorizeException;

    public List<PersistentLogin> findByToken(Context context, String token) throws SQLException;

    public List<PersistentLogin> findByName(Context context, String name) throws SQLException;

}
