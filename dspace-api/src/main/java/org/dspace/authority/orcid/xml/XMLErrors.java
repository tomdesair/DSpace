package org.dspace.authority.orcid.xml;

import com.atmire.authority.util.XMLUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by: Antoine Snyers (antoine at atmire dot com)
 * Date: 16 Dec 2013
 */
public class XMLErrors {

    /**
     * log4j logger
     */
    private static Logger log = Logger.getLogger(XMLErrors.class);

    private static final String ERROR_DESC = "/orcid-message/error-desc";

    /**
     * Evaluates whether a given xml document contains errors or not.
     *
     * @param xml The given xml document
     * @return true if the given xml document is null
     * or if it contains errors
     */
    public static boolean check(Document xml) {

        if (xml == null) {
            return true;
        }

        String textContent = null;

        try {
            textContent = XMLUtils.getTextContent(xml, ERROR_DESC);
        } catch (XPathExpressionException e) {
            log.error("Error while checking for errors in orcid message", e);
        }

        return textContent == null;
    }

    public static String getErrorMessage(Document xml) {

        if (xml == null) {
            return "Did not receive an XML document.";
        }

        String textContent = null;

        try {
            textContent = XMLUtils.getTextContent(xml, ERROR_DESC);
        } catch (XPathExpressionException e) {
            log.error("Error while checking for errors in orcid message", e);
        }

        return textContent;
    }

}
