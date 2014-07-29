package org.dspace.authority.orcid;

import com.atmire.authority.AuthorityValue;
import com.atmire.authority.orcid.model.Bio;
import com.atmire.authority.orcid.model.Work;
import com.atmire.authority.orcid.xml.XMLtoBio;
import com.atmire.authority.orcid.xml.XMLtoWork;
import com.atmire.authority.rest.RestSource;
import org.apache.log4j.Logger;
import org.dspace.utils.DSpace;
import org.w3c.dom.Document;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO extends RestSource
 * <p/>
 * Created by: Antoine Snyers (antoine at atmire dot com)
 * Date: 12 Dec 2013
 */
public class Orcid extends RestSource {

    /**
     * log4j logger
     */
    private static Logger log = Logger.getLogger(Orcid.class);

    private static Orcid orcid;

    public static Orcid getOrcid() {
        if (orcid == null) {
            orcid = new DSpace().getServiceManager().getServiceByName("OrcidSource", Orcid.class);
        }
        return orcid;
    }

    private Orcid(String url) {
        super(url);
    }

    public Bio getBio(String id) {
        Document bioDocument = restConnector.get(id + "/orcid-bio");
        XMLtoBio converter = new XMLtoBio();
        Bio bio = converter.convert(bioDocument).get(0);
        bio.setOrcid(id);
        return bio;
    }

    public List<Work> getWorks(String id) {
        Document document = restConnector.get(id + "/orcid-works");
        XMLtoWork converter = new XMLtoWork();
        return converter.convert(document);
    }

    public List<Bio> queryBio(String name, int start, int rows) {
        Document bioDocument = restConnector.get("search/orcid-bio?q=" + URLEncoder.encode("\"" + name + "\"") + "&start=" + start + "&rows=" + rows);
        XMLtoBio converter = new XMLtoBio();
        return converter.convert(bioDocument);
    }

    @Override
    public List<AuthorityValue> queryAuthorities(String text, int max) {
        List<Bio> bios = queryBio(text, 0, max);
        List<AuthorityValue> authorities = new ArrayList<AuthorityValue>();
        for (Bio bio : bios) {
            authorities.add(OrcidAuthorityValue.create(bio));
        }
        return authorities;
    }

    @Override
    public AuthorityValue queryAuthorityID(String id) {
        Bio bio = getBio(id);
        return OrcidAuthorityValue.create(bio);
    }
}
