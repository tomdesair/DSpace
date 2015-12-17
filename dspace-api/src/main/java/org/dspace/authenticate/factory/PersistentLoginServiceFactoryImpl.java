package org.dspace.authenticate.factory;

import org.dspace.authenticate.service.PersistentLoginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
public class PersistentLoginServiceFactoryImpl extends PersistentLoginServiceFactory{

    @Autowired(required = true)
    private PersistentLoginService persistentLoginService;
    @Override
    public PersistentLoginService getPersistentLoginService() {
        return persistentLoginService;
    }
}
