package org.springframework.security.web.authentication.rememberme;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public class JdbcTokenRepositoryWithUserLookupImpl extends JdbcTokenRepositoryImpl implements UserLookupFromToken {

    public static final String DEF_TOKEN_BY_NAME_SQL =
            "select username,series,token,last_used from persistent_logins where username = ?";

    @Override
    public String getUserEmailFromToken(String token) {
        return getMailForToken(token).getUsername();
    }

    private PersistentRememberMeToken getMailForToken(String token) {
        try {
            return getJdbcTemplate().queryForObject(DEF_TOKEN_BY_NAME_SQL, new RowMapper<PersistentRememberMeToken>() {
                public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new PersistentRememberMeToken(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4));
                }
            }, token);
        } catch(EmptyResultDataAccessException zeroResults) {
            if(logger.isDebugEnabled()) {
                logger.debug("Querying token for series '" + token + "' returned no results.", zeroResults);
            }
        }catch(IncorrectResultSizeDataAccessException moreThanOne) {
            logger.error("Querying token for series '" + token + "' returned more than one value. Series" +
                    " should be unique");
        } catch(DataAccessException e) {
            logger.error("Failed to load token for series " + token, e);
        }

        return null;
    }

}
