package org.springframework.security.web.authentication.rememberme;

import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public class InmemoryTokenRepositoryWithUserLookupImpl extends InMemoryTokenRepositoryImpl implements UserLookupFromToken{

    public synchronized String getUserEmailFromToken(String token) {
        Iterator<String> series = seriesTokens.keySet().iterator();

        while (series.hasNext()) {
            String seriesId = series.next();

            PersistentRememberMeToken tokenFromMap = seriesTokens.get(seriesId);

            if (token.equals(tokenFromMap.getTokenValue())) {
               return tokenFromMap.getUsername();
            }
        }
        return null;
    }
}
