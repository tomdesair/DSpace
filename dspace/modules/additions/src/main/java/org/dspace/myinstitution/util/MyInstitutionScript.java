package org.dspace.myinstitution.util;

import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.eperson.factory.EPersonServiceFactory;
import org.dspace.eperson.service.EPersonService;
import org.dspace.myinstitution.content.factory.MyInstitutionContentServiceFactory;
import org.dspace.myinstitution.content.service.MyInstitutionItemService;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by jonas - jonas@atmire.com on 06/01/16.
 */
public class MyInstitutionScript {

    /* Log4j logger*/
    private static final Logger log = Logger.getLogger(MyInstitutionScript.class);
    protected MyInstitutionItemService myInstitutionItemService;
    protected EPersonService ePersonService;

    protected MyInstitutionScript() {
        myInstitutionItemService = MyInstitutionContentServiceFactory.getInstance().getMyInstitutionItemService();
        ePersonService = EPersonServiceFactory.getInstance().getEPersonService();
    }

    public static void main(String... args) throws Exception {
        CommandLineParser parser = new PosixParser();

        Options options = new Options();
        options.addOption("h", "help", false, "help");
        options.addOption("s", "submitter", true,
                "The e-mail address of the submitter");
        CommandLine line = parser.parse(options, args);

        if (line.hasOption('h')) {
            HelpFormatter myhelp = new HelpFormatter();
            myhelp.printHelp("MyInstitutionScript\n", options);
            System.out
                    .println("\nPrint handles of all items submitted by a given person: org.dspace.myinstitution.util.MyInstitutionScript -s submitter-e-mail");

            System.exit(0);
        }
        String email = null;
        if (line.hasOption("s")) {
            email = line.getOptionValue('s');
        }
        MyInstitutionScript myInstitutionScript = new MyInstitutionScript();
        try {
            myInstitutionScript.printByEperson(email);
        } catch (SQLException e) {
            log.error(e);
        }
    }

    public void printByEperson(String email) throws SQLException {
        Context ctx = new Context();
        EPerson byEmail = ePersonService.findByEmail(ctx, email);
        if (byEmail != null) {
            Iterator<Item> iterator = myInstitutionItemService.findBySubmitter(ctx, byEmail);
            while (iterator.hasNext()) {
                System.out.println(iterator.next().getHandle());
            }
        } else {
            System.out.println("Please enter an existing submitter");
        }
        ctx.complete();

    }
}
