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
