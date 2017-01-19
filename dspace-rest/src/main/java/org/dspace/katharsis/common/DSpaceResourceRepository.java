package org.dspace.katharsis.common;

import io.katharsis.repository.ResourceRepositoryBase;
import org.dspace.eperson.factory.EPersonServiceFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.SQLException;
import java.util.Collection;

/**
 * TODO TOM UNIT TEST
 */
public abstract class DSpaceResourceRepository<T> extends ResourceRepositoryBase<T, String> {

    public DSpaceResourceRepository(final Class<T> resourceClass) {
        super(resourceClass);
    }

    protected static org.dspace.core.Context createContext() throws SQLException {
        org.dspace.core.Context context = new org.dspace.core.Context();
        //context.getDBConnection().setAutoCommit(false); // Disable autocommit.

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            Collection<SimpleGrantedAuthority> specialGroups = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
            for (SimpleGrantedAuthority grantedAuthority : specialGroups) {
                context.setSpecialGroup(EPersonServiceFactory.getInstance().getGroupService().findByName(context, grantedAuthority.getAuthority()).getID());
            }
            context.setCurrentUser(EPersonServiceFactory.getInstance().getEPersonService().findByEmail(context, authentication.getName()));
        }

        return context;
    }

}
