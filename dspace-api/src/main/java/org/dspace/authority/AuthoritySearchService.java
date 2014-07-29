/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.authority;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.net.MalformedURLException;
import java.util.List;

/**
 * User: kevin (kevin at atmire.com)
 * Date: 7-dec-2010
 * Time: 10:13:07
 */
public interface AuthoritySearchService {

    QueryResponse search(SolrQuery query) throws SolrServerException, MalformedURLException;

    List<String> getAllIndexedMetadataFields() throws Exception;

}
