package org.dspace.authenticate.factory;

import org.dspace.authenticate.service.PersistentLoginService;
import org.dspace.utils.DSpace;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public abstract class PersistentLoginServiceFactory {

    public abstract PersistentLoginService getPersistentLoginService();

    public static PersistentLoginServiceFactory getInstance(){
        return new DSpace().getServiceManager().getServiceByName("persistentLoginServiceFactory",PersistentLoginServiceFactory.class);
    }
}
