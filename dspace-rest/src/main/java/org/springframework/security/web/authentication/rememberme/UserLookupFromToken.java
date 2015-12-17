package org.springframework.security.web.authentication.rememberme;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public interface UserLookupFromToken {

    public  String getUserEmailFromToken(String token);

}
