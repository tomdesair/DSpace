package org.dspace.authenticate.dao.impl;

import org.apache.tools.ant.types.resources.Restrict;
import org.dspace.authenticate.PersistentLogin;
import org.dspace.authenticate.dao.PersistentLoginDAO;
import org.dspace.core.AbstractHibernateDAO;
import org.dspace.core.Context;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public class PersistentLoginDAOImpl extends AbstractHibernateDAO<PersistentLogin> implements PersistentLoginDAO {
    @Override
    public List<PersistentLogin> findByToken(Context context, String token) throws SQLException {
        Criteria criteria = createCriteria(context, PersistentLogin.class);
        criteria.add(Restrictions.and(Restrictions.eq("token",token)));
        return list(criteria);
    }

    @Override
    public List<PersistentLogin> findByName(Context context, String name) throws SQLException {
        Criteria criteria = createCriteria(context, PersistentLogin.class);
        criteria.add(Restrictions.and(Restrictions.eq("username",name)));
        return list(criteria);
    }
}
