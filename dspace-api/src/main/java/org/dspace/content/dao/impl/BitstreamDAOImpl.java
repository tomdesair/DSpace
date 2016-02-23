/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.content.dao.impl;

import org.dspace.content.Bitstream;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.content.dao.BitstreamDAO;
import org.dspace.core.AbstractHibernateDSODAO;
import org.dspace.core.Context;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Hibernate implementation of the Database Access Object interface class for the Bitstream object.
 * This class is responsible for all database calls for the Bitstream object and is autowired by spring
 * This class should never be accessed directly.
 *
 * @author kevinvandevelde at atmire.com
 */
public class BitstreamDAOImpl extends AbstractHibernateDSODAO<Bitstream> implements BitstreamDAO {

    @Override
    public List<Bitstream> findDeletedBitstreams(Context context) throws SQLException {
        Criteria criteria = createCriteria(context, Bitstream.class);
        criteria.add(Restrictions.eq("deleted", true));

        return list(criteria);

    }

    @Override
    public List<Bitstream> findDuplicateInternalIdentifier(Context context, Bitstream bitstream) throws SQLException {
        Criteria criteria = createCriteria(context, Bitstream.class);
        criteria.add(Restrictions.and(
                Restrictions.eq("internalId", bitstream.getInternalId()),
                Restrictions.not(Restrictions.eq("id", bitstream.getID()))
        ));

        return list(criteria);
    }

    @Override
    public List<Bitstream> findBitstreamsWithNoRecentChecksum(Context context) throws SQLException {
//        "select bitstream.deleted, bitstream.store_number, bitstream.size_bytes, "
//                    + "bitstreamformatregistry.short_description, bitstream.bitstream_id,  "
//                    + "bitstream.user_format_description, bitstream.internal_id, "
//                    + "bitstream.source, bitstream.checksum_algorithm, bitstream.checksum, "
//                    + "bitstream.name, bitstream.description "
//                    + "from bitstream left outer join bitstreamformatregistry on "
//                    + "bitstream.bitstream_format_id = bitstreamformatregistry.bitstream_format_id "
//                    + "where not exists( select 'x' from most_recent_checksum "
//                    + "where most_recent_checksum.bitstream_id = bitstream.bitstream_id )"

        return executeNamedQuery(context, "Bitstream.findBitstreamsWithNoRecentChecksum");
    }

    @Override
    public Iterator<Bitstream> findByCommunity(Context context, Community community) throws SQLException {
        return iterateOverNamedQuery(context, "Bitstream.findByCommunity", singleParam("community", community));
    }

    @Override
    public Iterator<Bitstream> findByCollection(Context context, Collection collection) throws SQLException {
        return iterateOverNamedQuery(context, "Bitstream.findByCollection", singleParam("collection", collection));
    }

    @Override
    public Iterator<Bitstream> findByItem(Context context, Item item) throws SQLException {
        return iterateOverNamedQuery(context, "Bitstream.findByItem", singleParam("item", item));
    }

    @Override
    public Iterator<Bitstream> findByStoreNumber(Context context, Integer storeNumber) throws SQLException {
        return iterateOverNamedQuery(context, "Bitstream.findByStoreNumber", singleParam("storeNumber", storeNumber));
    }

    @Override
    public Long countByStoreNumber(Context context, Integer storeNumber) throws SQLException {
        Criteria criteria = createCriteria(context, Bitstream.class);
        criteria.add(Restrictions.eq("storeNumber", storeNumber));
        return countLong(criteria);
    }
}
