package org.dspace.myinstitution.content;

import org.dspace.content.Item;
import org.dspace.content.ItemServiceImpl;
import org.dspace.core.Context;
import org.dspace.myinstitution.content.dao.MyInstitutionItemDAO;
import org.dspace.myinstitution.content.service.MyInstitutionItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public class MyInstitutionItemServiceImpl extends ItemServiceImpl implements MyInstitutionItemService {

    @Autowired(required = true)
    protected MyInstitutionItemDAO myInstitutionItemDAO;
    @Override
    public Iterator<Item> findAllDiscoverable(Context context) throws SQLException {
        return myInstitutionItemDAO.findAllDiscoverable(context);
    }
}
