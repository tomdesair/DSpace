package org.dspace.myinstitution.util;

import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.myinstitution.content.factory.MyInstitutionContentServiceFactory;
import org.dspace.myinstitution.content.service.MyInstitutionItemService;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 05/01/16.
 */
public class MyInstitutionScript {

    protected MyInstitutionItemService myInstitutionItemService;

    protected MyInstitutionScript(){
        myInstitutionItemService = MyInstitutionContentServiceFactory.getInstance().getMyInstitutionItemService();
    }

    public static void main (String ... args){
        MyInstitutionScript myInstitutionScript = new MyInstitutionScript();
        try {
            myInstitutionScript.printAllDiscoverable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllDiscoverable() throws SQLException {
        Context ctx = new Context();
        Iterator<Item> iterator = myInstitutionItemService.findAllDiscoverable(ctx);
        if(iterator.hasNext()){
            System.out.println("Printing all discoverable items");
        } else {
            System.out.println("No discoverable items found");
        }
        while (iterator.hasNext()){
            System.out.println(iterator.next().getHandle());
        }
        ctx.complete();
    }

}
