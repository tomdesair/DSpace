package org.dspace.authority;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * This class generates AuthorityValue that do not have a solr record yet.
 * <p/>
 * This class parses the ‹AuthorityValue›.generateString(),
 * creates an AuthorityValue instance of the appropriate type
 * and then generates another instance using ‹AuthorityValue›.newInstance(info).
 *
 * Created by: Antoine Snyers (antoine at atmire dot com)
 * Date: 07 Mar 2014
 */
public class AuthorityValueGenerator {

    public static final String SPLIT = "::";
    public static final String GENERATE = "will be generated" + SPLIT;


    public static AuthorityValue generate(String uid, String content, String field) {
        AuthorityValue nextValue = null;

        nextValue = generateRaw(uid, content, field);


        if (nextValue != null) {
            nextValue.setId(UUID.randomUUID().toString());
            nextValue.updateLastModifiedDate();
            nextValue.setCreationDate(new Date());
            nextValue.setField(field);
        }

        return nextValue;
    }

    protected static AuthorityValue generateRaw(String uid, String content, String field) {
        AuthorityValue nextValue;
        if (uid != null && uid.startsWith(AuthorityValueGenerator.GENERATE)) {
            String[] split = StringUtils.split(uid, SPLIT);
            String type = null, info = null;
            if (split.length > 0) {
                type = split[1];
                if (split.length > 1) {
                    info = split[2];
                }
            }
            AuthorityValue authorityType = AuthorityValue.getAuthorityTypes().getEmptyAuthorityValue(type);
            nextValue = authorityType.newInstance(info);
        } else {
            Map<String, AuthorityValue> fieldDefaults = AuthorityValue.getAuthorityTypes().getFieldDefaults();
            nextValue = fieldDefaults.get(field).newInstance(null);
            if (nextValue == null) {
                nextValue = new AuthorityValue();
            }
            nextValue.setValue(content);
        }
        return nextValue;
    }

    public static AuthorityValue update(AuthorityValue value) {
        AuthorityValue updated = generateRaw(value.generateString(), value.getValue(), value.getField());
        if (updated != null) {
            updated.setId(value.getId());
            updated.setCreationDate(value.getCreationDate());
            updated.setField(value.getField());
            if (updated.hasTheSameInformationAs(value)) {
                updated.setLastModified(value.getLastModified());
            }else {
                updated.updateLastModifiedDate();
            }
        }
        return updated;
    }
}
