/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.core;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import java.sql.SQLException;
import java.util.*;

/**
 * Hibernate implementation for generic DAO interface.  Also includes additional
 * Hibernate calls that are commonly used.
 * Each DAO should extend this class to prevent code duplication.
 *
 * @author kevinvandevelde at atmire.com
 */
public abstract class AbstractHibernateDAO<T> implements GenericDAO<T> {

    @Override
    public T create(Context context, T t) throws SQLException {
        getHibernateSession(context).persist(t);
        return t;
    }

    @Override
    public void save(Context context, T t) throws SQLException {
        //Isn't required, is just here for other DB implementation. Hibernate auto keeps track of changes.
    }

    protected Session getHibernateSession(Context context) throws SQLException {
        return ((Session) context.getDBConnection().getSession());
    }

    @Override
    public void delete(Context context, T t) throws SQLException {
        getHibernateSession(context).delete(t);
    }

    @Override
    public List<T> findAll(Context context, Class<T> clazz) throws SQLException {
        return list(createCriteria(context, clazz));
    }

    @Override
    public T findUnique(Context context, String query) throws SQLException {
        @SuppressWarnings("unchecked")
        T result = (T) createQuery(context, query).uniqueResult();
        return result;
    }

    @Override
    public T findByID(Context context, Class clazz, UUID id) throws SQLException {
        if(id == null)
        {
            return null;
        }
        @SuppressWarnings("unchecked")
        T result = (T) getHibernateSession(context).get(clazz, id);
        return result;
    }

    @Override
    public T findByID(Context context, Class clazz, int id) throws SQLException {
        @SuppressWarnings("unchecked")
        T result = (T) getHibernateSession(context).get(clazz, id);
        return result;
    }

    @Override
    public List<T> findMany(Context context, String query) throws SQLException {
        @SuppressWarnings("unchecked")
        List<T> result = (List<T>) createQuery(context, query).list();
        return result;
    }

    /**
     * Execute a JPA Criteria query and return a collection of results.
     *
     * @param context
     * @param query
     * @return
     * @throws SQLException
     */
    public List<T> findMany(Context context, Query query) throws SQLException {
        @SuppressWarnings("unchecked")
        List<T> result = (List<T>) query.list();
        return result;
    }

    public Criteria createCriteria(Context context, Class<T> persistentClass) throws SQLException {
        return getHibernateSession(context).createCriteria(persistentClass);
    }

    public Criteria createCriteria(Context context, Class<T> persistentClass, String alias) throws SQLException {
        return getHibernateSession(context).createCriteria(persistentClass, alias);
    }

    public Query createQuery(Context context, String query) throws SQLException {
        return getHibernateSession(context).createQuery(query);
    }

    public Query prepareNamedQuery(Context context, String queryName) throws SQLException {
        return prepareNamedQuery(context, queryName, null);
    }

    public Query prepareNamedQuery(Context context, String queryName, final Map<String, Object> parameters) throws SQLException {
        Query query = getHibernateSession(context).getNamedQuery(queryName);

        if(MapUtils.isNotEmpty(parameters)) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return query;
    }

    public List<T> executeNamedQuery(final Context context, final String queryName) throws SQLException {
        return executeNamedQuery(context, queryName, null);
    }

    public List<T> executeNamedQuery(final Context context, final String queryName, final Map<String, Object> parameters) throws SQLException {
        Query query = prepareNamedQuery(context, queryName, parameters);

        @SuppressWarnings("unchecked")
        List<T> result = query.list();
        return result;
    }

    public List<T> list(Criteria criteria)
    {
        @SuppressWarnings("unchecked")
        List<T> result = (List<T>) criteria.list();
        return result;
    }

    public List<T> list(Query query)
    {
        @SuppressWarnings("unchecked")
        List<T> result = (List<T>) query.list();
        return result;
    }

    /**
     * Retrieve a unique result from the query.  If multiple results CAN be
     * retrieved an exception will be thrown,
     * so only use when the criteria state uniqueness in the database.
     * @param criteria
     * @return
     */
    public T uniqueResult(Criteria criteria)
    {
        @SuppressWarnings("unchecked")
        T result = (T) criteria.uniqueResult();
        return result;
    }

    /**
     * Retrieve a single result from the query.  Best used if you expect a
     * single result, but this isn't enforced on the database.
     * @param criteria
     * @return
     */
    public T singleResult(Criteria criteria)
    {
        List<T> list = list(criteria);
        if(CollectionUtils.isNotEmpty(list))
        {
            return list.get(0);
        }else{
            return null;
        }

    }

    public T uniqueResult(Query query)
    {
        @SuppressWarnings("unchecked")
        T result = (T) query.uniqueResult();
        return result;
    }

    public Iterator<T> iterate(Query query)
    {
        @SuppressWarnings("unchecked")
        Iterator<T> result = (Iterator<T>) query.iterate();
        return result;
    }

    public Iterator<T> iterateOverNamedQuery(final Context context, final String queryName, final Map<String, Object> params) throws SQLException {
        Query query = prepareNamedQuery(context, queryName, params);

        @SuppressWarnings("unchecked")
        Iterator<T> result = (Iterator<T>) query.iterate();
        return result;
    }

    public int count(Criteria criteria)
    {
        return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    public int count(Query query)
    {
        return ((Long) query.uniqueResult()).intValue();
    }

    public long countLong(Criteria criteria)
    {
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    protected Map<String, Object> singleParam(final String key, final Object value) {
        Map<String, Object>  result = new HashMap<>();
        result.put(key, value);
        return result;
    }

}
