package org.dspace.myinstitution.content.factory;

import org.dspace.myinstitution.content.service.MyInstitutionItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public class MyInstitutionContentServiceFactoryImpl extends MyInstitutionContentServiceFactory {

    @Autowired(required = true)
    private MyInstitutionItemService myInstitutionItemService;

    @Override
    public MyInstitutionItemService getMyInstitutionItemService() {
        return myInstitutionItemService;
    }
}
