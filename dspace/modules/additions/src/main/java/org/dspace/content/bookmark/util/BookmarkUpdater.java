package org.dspace.content.bookmark.util;

import org.apache.commons.cli.*;
import org.dspace.content.bookmark.Bookmark;
import org.dspace.content.Item;
import org.dspace.content.bookmark.factory.BookmarkServiceFactory;
import org.dspace.content.factory.ContentServiceFactory;
import org.dspace.content.bookmark.service.BookmarkService;
import org.dspace.content.service.ItemService;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.eperson.factory.EPersonServiceFactory;
import org.dspace.eperson.service.EPersonService;
import org.dspace.handle.factory.HandleServiceFactory;
import org.dspace.handle.service.HandleService;

import java.sql.SQLException;
import java.util.*;


public class BookmarkUpdater {
    protected BookmarkService bookmarkService;
    protected EPersonService ePersonService;
    protected ItemService itemService;
    protected HandleService handleService;

    protected BookmarkUpdater() {
        bookmarkService = BookmarkServiceFactory.getInstance().getBookmarkService();
        ePersonService = EPersonServiceFactory.getInstance().getEPersonService();
        itemService = ContentServiceFactory.getInstance().getItemService();
        handleService = HandleServiceFactory.getInstance().getHandleService();

    }

    public void doUpdate(String epersonMail, Map<String, String> handlesAndTitles) throws SQLException {
        Context ctx = new Context();
        EPerson ePerson = ePersonService.findByEmail(ctx, epersonMail);
        ctx.setCurrentUser(ePerson);
        for (String key : handlesAndTitles.keySet()) {
            // Don't add invalid objects
            if (handleService.resolveToObject(ctx, key) != null) {
                createBookmark(ctx, ePerson, key, handlesAndTitles.get(key));
            }
        }
        ctx.complete();
    }

    private void createBookmark(Context ctx, EPerson ePerson, String handle, String title) throws SQLException {
        Bookmark bookmark = bookmarkService.create(ctx);
        bookmark.setDateCreated(new Date());
        bookmark.setCreator(ePerson);
        bookmark.setItem((Item) handleService.resolveToObject(ctx, handle));
        bookmark.setTitle(title);
        bookmarkService.update(ctx,bookmark);

    }

    public static void main(String... args) throws ParseException {

        BookmarkUpdater bmu = new BookmarkUpdater();
        CommandLineParser parser = new PosixParser();
        Map<String, String> handlesAndTitles = new HashMap<>();
        Options options = createOptions();

        CommandLine line = parser.parse(options, args);

        printOptionsHelp(options, line);

        try {

            if (line.hasOption("f")) {
                if (line.hasOption("i")) {
                    bmu.printBookmarksBasedOnItem(line.getOptionValue("i"));
                }
            }
            String epersonMail = line.getOptionValue("e");
            addHandlesAndTitles(handlesAndTitles, line);
            bmu.doUpdate(epersonMail, handlesAndTitles);
            if (line.hasOption("p")) {
                bmu.printEpersonsBookmarks(epersonMail);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getLocalizedMessage());
            sqle.printStackTrace();
        }
    }

    private static void printOptionsHelp(Options options, CommandLine line) {
        if (line.hasOption('h')) {
            HelpFormatter myhelp = new HelpFormatter();
            myhelp.printHelp("Usages : \n", options);
            System.out.println("\nAdd a single bookmark based on eperson,handle and possibly a title: BookmarkUpdater -e atmirenv@gmail.com -i 123456789/4 -t 'A title'");
            System.out.println("\nAdd multiple bookmarks to a provided eperson: BookmarkUpdater -e atmirenv@gmail.com -m");
            System.out.println("\nAdding the -p option will show all the bookmarks currently associated with the given eperson");
            System.out.println("\nIf the f option has been provided (as well as a handle (i), all bookmarks with this given item will be shown");
            System.out.println("\nIf no options are provided (apart from the required eperson), a fallback to the addition of multiple bookmarks will be used");
            System.exit(0);
        }
    }

    private static void addHandlesAndTitles(Map<String, String> handlesAndTitles, CommandLine line) {
        String title;
        // If the "multiple" option has been enabled, keep asking user for input until he types stop
        // When no handle is supplied, default to this behaviour as well
        if (line.hasOption("m") || !line.hasOption("i")) {
            System.out.println("Enter valid handles(invalid handles will be skipped)\nYou can stop adding bookmarks by typing stop");
            Scanner scanner = new Scanner(System.in);
            String handle = scanner.nextLine();
            while (!handle.equals("stop")) {
                if (handlesAndTitles.containsKey(handle)) {
                    System.out.println("This item has already been bookmarked by this user");
                } else {
                    Scanner titleScanner = new Scanner(System.in);
                    System.out.println("Enter a title for this bookmark");
                    title = titleScanner.nextLine();
                    handlesAndTitles.put(handle, title);
                }
                System.out.println("Enter another handle");
                handle = scanner.nextLine();
            }

        } else {
            // The user has provided a single handle to bookmark
            if (line.hasOption("i")) {
                if (line.hasOption("t")) {
                    title = line.getOptionValue("t");
                } else {
                    title = "No title provided for the bookmark";
                }
                handlesAndTitles.put(line.getOptionValue("i"), title);
            }
        }
    }

    private static Options createOptions() {
        Options options = new Options();
        Option epers = new Option("e", "eperson", true, "The eperson's email adress");
        epers.setRequired(true);
        options.addOption(epers);
        options.addOption("i", "itemhandle", true, "The handle of an item to add");
        options.addOption("m", "multiple", false, "Create multiple bookmarks");
        options.addOption("t", "title", true, "Enter a title");
        options.addOption("h", "help", false, "help");
        options.addOption("p", "print", false, "Print the bookmarks currently connected to a given eperson");
        options.addOption("f", "findbyitem", false, "Print the bookmarks currently connected to a given itemhandle");

        return options;
    }

    public void printEpersonsBookmarks(String epersonMail) throws SQLException {
        Context ctx = new Context();

        EPerson ePerson = ePersonService.findByEmail(ctx, epersonMail);
        List<Bookmark> bookMarksByEperson = bookmarkService.findByEperson(ctx, ePerson);
        printBookmarks(bookMarksByEperson);

        ctx.complete();
    }

    public void printBookmarksBasedOnItem(String handle) throws SQLException {
        Context ctx = new Context();

        Item item = (Item) handleService.resolveToObject(ctx, handle);
        if (item != null) {
            List<Bookmark> bookMarksByEperson = bookmarkService.findByItem(ctx, item);
            printBookmarks(bookMarksByEperson);
        }

        ctx.complete();
    }

    private void printBookmarks(List<Bookmark> bookMarksByEperson) {
        for (Bookmark b : bookMarksByEperson) {
            System.out.println("Generated UUI :" + b.getId());
            System.out.println("Title :" + b.getTitle());
            System.out.println("Date of creation :" + b.getDateCreated());
            System.out.println("Creator : " + b.getCreator().getFullName());
            System.out.println("Item : " + ((b.getItem() != null) ? b.getItem().getName() : "No item provided for this bookmark"));
            System.out.println("");
        }
    }
}
