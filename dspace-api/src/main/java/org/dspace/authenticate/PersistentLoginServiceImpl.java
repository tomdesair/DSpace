package org.dspace.authenticate;

import org.apache.log4j.Logger;
import org.dspace.authenticate.dao.PersistentLoginDAO;
import org.dspace.authenticate.service.PersistentLoginService;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.dao.ResourcePolicyDAO;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public class PersistentLoginServiceImpl implements PersistentLoginService {

    /* Log4j logger*/
    private static final Logger log = Logger.getLogger(PersistentLoginServiceImpl.class);

    @Autowired(required = true)
    protected PersistentLoginDAO persistentLoginDAO;


    public PersistentLoginServiceImpl() {

    }

    @Override
    public PersistentLogin create(Context context, String username, String series, String token, Date date) throws SQLException, AuthorizeException {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setLastUsed(date);
        persistentLogin.setSeries(series);
        persistentLogin.setToken(token);
        persistentLogin.setUsername(username);
        return persistentLoginDAO.create(context, persistentLogin);
    }

    @Override
    public PersistentLogin create(Context context) throws SQLException, AuthorizeException {
        return null;
    }

    @Override
    public PersistentLogin find(Context context, int id) throws SQLException {
        return persistentLoginDAO.findByID(context, PersistentLogin.class, id);
    }

    @Override
    public void update(Context context, PersistentLogin persistentLogin) throws SQLException, AuthorizeException {
        persistentLoginDAO.save(context,persistentLogin);
    }

    @Override
    public void delete(Context context, PersistentLogin persistentLogin) throws SQLException, AuthorizeException {
        persistentLoginDAO.delete(context, persistentLogin);
    }

    @Override
    public List<PersistentLogin> findByToken(Context context, String token) throws SQLException {
        return persistentLoginDAO.findByToken(context, token);
    }

    @Override
    public List<PersistentLogin> findByName(Context context, String name) throws SQLException {
        return persistentLoginDAO.findByName(context,name);
    }
}
