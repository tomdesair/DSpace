/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest;

import org.apache.log4j.Logger;
import org.dspace.authenticate.PersistentLogin;
import org.dspace.authenticate.factory.PersistentLoginServiceFactory;
import org.dspace.authenticate.service.PersistentLoginService;
import org.dspace.authorize.AuthorizeException;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.eperson.factory.EPersonServiceFactory;
import org.dspace.eperson.service.EPersonService;
import org.dspace.rest.common.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This class provide token generation, token holding and logging user into rest
 * api. For login use method login with class org.dspace.rest.common.User. If
 * you want to be deleted from holder, use method for logout.
 * 
 * @author Rostislav Novak (Computing and Information Centre, CTU in Prague)
 */
public class TokenHolder
{


    private static final Logger log = Logger.getLogger(TokenHolder.class);

    public static String TOKEN_HEADER = "rest-dspace-token";
    private static PersistentLoginService persistentLoginService = PersistentLoginServiceFactory.getInstance().getPersistentLoginService();
    /**
     * Collection holding the auth-token, and the corresponding EPerson's UUID
     */
    //static InmemoryTokenRepositoryWithUserLookupImpl inMemoryTokenRepository = new InmemoryTokenRepositoryWithUserLookupImpl();
    //static JdbcTokenRepositoryWithUserLookupImpl jdbcTokenRepositoryWithUserLookup = null;
    /**
     * Login user into rest api. It check user credentials if they are okay.
     * 
     * @param user
     *            User which will be logged into rest api.
     * @return Returns generated token, which must be used in request header
     *         under rest-api-token. If password is bad or user does not exist,
     *         it returns NULL.
     * @throws WebApplicationException
     *             It is thrown by SQLException if user could not be read from
     *             database. And by Authorization exception if context has not
     *             permission to read eperson.
     */
    public static String login(User user) throws WebApplicationException
    {

        AuthenticationManager am = new DspaceAuthenticationManager();
        Authentication request = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        Authentication result = am.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);


        EPersonService epersonService = EPersonServiceFactory.getInstance().getEPersonService();

        org.dspace.core.Context context = null;
        String token = null;

        try
        {
            if(result.isAuthenticated()) {
                context = new org.dspace.core.Context();


                EPerson ePerson = epersonService.findByEmail(context, user.getEmail());
                synchronized (TokenHolder.class) {
                    if (persistentLoginService.findByName(context, ePerson.getEmail()) != null && persistentLoginService.findByName(context, ePerson.getEmail()).size() > 0) {
                        token = persistentLoginService.findByName(context, ePerson.getEmail()).get(0).getToken();
                    }
                     else {
                        token = generateToken();
                        PersistentLogin persistentLogin =persistentLoginService.create(context,user.getEmail(),ePerson.getID().toString(),token,new Date());

                        persistentLoginService.update(context,persistentLogin);

                    }
                }
            }

                log.trace("User(" + user.getEmail() + ") has been logged in.");
                context.complete();
            }
        catch (SQLException e)
        {
            context.abort();
            log.error("Could not read user from database. Message:" + e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        } catch (AuthorizeException e) {
            e.printStackTrace();
        } finally
        {
            if ((context != null) && (context.isValid()))
            {
                context.abort();
                log.error("Something get wrong. Aborting context in finally statement.");
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
        }

        return token;
    }

    /**
     * Return EPerson for log into context.
     * 
     * @param token
     *            Token under which is stored eperson.
     * @return Return instance of EPerson if is token right, otherwise it
     *         returns NULL.
     */
    public static synchronized EPerson getEPerson(String token)
    {
        SecurityContextHolder.getContext();
        try {
            EPersonService epersonService = EPersonServiceFactory.getInstance().getEPersonService();
            Context context = new Context();
            List<PersistentLogin> persistentLogins = persistentLoginService.findByToken(context, token);
            if (persistentLogins.size() > 0) {
                return epersonService.findByEmail(context, persistentLogins.get(0).getUsername());
            }
            return null;
        } catch (SQLException e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Logout user from rest api. It delete token and EPerson from TokenHolder.
     * 
     * @param token
     *            Token under which is stored eperson.
     * @return Return true if was all okay, otherwise return false.
     */
    public static synchronized boolean logout(String token)
    {

        if ((token == null))
        {
            return false;
        }

        Context context = new Context();
        try {
            persistentLoginService.delete(context, (PersistentLogin) persistentLoginService.findByToken(context, token));
        } catch (SQLException e) {
            log.error(e);
            return false;
        } catch (AuthorizeException e) {
           log.error(e);
            return false;
        }finally {
            if(context!=null){
                context.abort();
            }
        }

        return true;
    }

    /**
     * It generates unique token.
     * 
     * @return String filled with unique token.
     */
    private static String generateToken()
    {
        return UUID.randomUUID().toString();
    }
   /* private static JdbcTokenRepositoryWithUserLookupImpl getJdbcTokenRepositoryWithUserLookup(){
        if(jdbcTokenRepositoryWithUserLookup==null){
            jdbcTokenRepositoryWithUserLookup= new JdbcTokenRepositoryWithUserLookupImpl();
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl("jdbc:postgresql://localhost:5434/dspace");
            ds.setUsername("dspace");
            ds.setPassword("dspace");
            jdbcTokenRepositoryWithUserLookup.setDataSource(ds);
        }
        return jdbcTokenRepositoryWithUserLookup;
    }*/

}

