package org.dspace.myinstitution.content.factory;

import org.dspace.myinstitution.content.service.MyInstitutionItemService;
import org.dspace.utils.DSpace;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public abstract class MyInstitutionContentServiceFactory {

    public abstract MyInstitutionItemService getMyInstitutionItemService();

    public static MyInstitutionContentServiceFactory getInstance(){
        return new DSpace().getServiceManager().getServiceByName("myInstitutionContentServiceFactory", MyInstitutionContentServiceFactory.class);
    }
}