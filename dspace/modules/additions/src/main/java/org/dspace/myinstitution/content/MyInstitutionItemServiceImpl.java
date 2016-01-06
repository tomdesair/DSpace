package org.dspace.myinstitution.content;

import org.apache.log4j.Logger;
import org.dspace.content.Item;
import org.dspace.content.ItemServiceImpl;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.myinstitution.content.service.MyInstitutionItemService;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public class MyInstitutionItemServiceImpl extends ItemServiceImpl implements MyInstitutionItemService {

    /* Log4j logger*/
    private static final Logger log =  Logger.getLogger(MyInstitutionItemServiceImpl.class);

    @Override
    public Iterator<Item> findBySubmitter(Context context, EPerson eperson) throws SQLException {

        Iterator<Item> bySubmitter = itemDAO.findBySubmitter(context, eperson);
        if(!bySubmitter.hasNext()){
                log.info("Call to find items for submitter: '"+eperson.getName()+"' returned no results");
        }
        return bySubmitter;
    }
}