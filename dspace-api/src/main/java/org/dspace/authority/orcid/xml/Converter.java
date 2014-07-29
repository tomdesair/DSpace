package org.dspace.authority.orcid.xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * Created by: Antoine Snyers (antoine at atmire dot com)
 * Date: 18 Dec 2013
 */
public abstract class Converter<T> {

    /**
     * log4j logger
     */
    private static Logger log = Logger.getLogger(Converter.class);


    protected void processError(Document xml) {
        String errorMessage = XMLErrors.getErrorMessage(xml);
        log.error("The orcid-message reports an error: " + errorMessage);
    }

    public abstract T convert(Document document);
}
