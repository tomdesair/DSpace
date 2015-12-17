package org.dspace.rest;

import org.apache.log4j.Logger;
import org.dspace.authenticate.factory.AuthenticateServiceFactory;
import org.dspace.authenticate.service.AuthenticationService;
import org.dspace.core.Context;
import org.dspace.utils.DSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Roeland Dillen (roeland at atmire dot com)
 * Date: 04 Nov 2015
 */
public class DSpaceAuthenticationProvider implements AuthenticationProvider {
	private static Logger log = Logger.getLogger(DSpaceAuthenticationProvider.class);

	private DSpace dspace;

	public DSpace getDspace() {
		return dspace;
	}
	@Autowired
	public void setDspace(DSpace dspace) {
		this.dspace = dspace;
	}

	public static final String DSPACE_CONTEXT = "dspace.context";

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Context context = null;

		try {
			context=new Context();
			String name = authentication.getName();
			String password = authentication.getCredentials().toString();
            DspaceAuthenticationManager manager = new DspaceAuthenticationManager();
			if(manager.authenticate(authentication).isAuthenticated()){

					List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
					grantedAuths.add(new SimpleGrantedAuthority("ROLE_REST"));
					Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
					return auth;
				}

			}

		 catch (Exception e) {
			log.error("Error", e);
		} finally {
			if(context != null) {
				try {
					context.complete();
				} catch (SQLException e) {
					log.error(e.getMessage() + " occurred while trying to close");
				}
			}
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}


}

class DspaceAuthenticationManager implements AuthenticationManager {
    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    static {
        AUTHORITIES.add(new GrantedAuthorityImpl("ROLE_USER"));
    }

    public Authentication authenticate(Authentication auth) {
        AuthenticationService authenticationService = AuthenticateServiceFactory.getInstance().getAuthenticationService();
        Context context = new org.dspace.core.Context();

        if(authenticationService.authenticate(context, auth.getName(), auth.getCredentials().toString(), null, null)==1){

            return new UsernamePasswordAuthenticationToken(auth.getName(),
                    auth.getCredentials(), AUTHORITIES);
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}