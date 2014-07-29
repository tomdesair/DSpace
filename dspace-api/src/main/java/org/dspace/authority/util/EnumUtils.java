package org.dspace.authority.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by: Antoine Snyers (antoine at atmire dot com)
 * Date: 17 Dec 2013
 */
public class EnumUtils {

    /**
     * log4j logger
     */
    private static Logger log = Logger.getLogger(EnumUtils.class);

    private static String getEnumName(String value) {
        return StringUtils.isNotBlank(value) ?
                value.toUpperCase().trim().replaceAll("[^a-zA-Z]", "_")
                : null;
    }

    public static <E extends Enum<E>> E lookup(Class<E> enumClass, String enumName) {
        try {
            return Enum.valueOf(enumClass, getEnumName(enumName));
        } catch (Exception ex) {
            log.warn("Did not find an "+enumClass.getSimpleName()+" for value '"+enumName+"'");
            return null;
        }
    }
}
