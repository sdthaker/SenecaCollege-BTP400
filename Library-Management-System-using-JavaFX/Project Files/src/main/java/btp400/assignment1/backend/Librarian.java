package btp400.assignment1.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class manages all the students that registered in the system.
 * It is responsible for registering & logging in a student.
 * It also reads and writes json data related to students, to & from a file.
 * Other functionalities include viewing library catalogue, borrowing a book,
 * viewing borrowed books and return a book which mutates the existing JSONArray
 * instance variable that holds all the student information.
 *
 * @author Zia Syed
 * @version 1.0.0
 * @see Object
 * @see Iterator
 * @see String
 * @see File
 * @see FileHandler
 * @see FileWriter
 * @see FileReader
 * @see Logger
 * @see SimpleFormatter
 * @see IOException
 * @see ParseException
 * @see JSONArray
 * @see JSONObject
 * @see JSONParser
 */

public class Librarian {
    /**
     * Holds an object of the entire library, including books, magazines and videos.
     */
    static JSONObject libraryRecord;
    /**
     * Holds the file pointer which points where to read and write the data to and from respectively.
     */
    static String path;

    /**
     * Default constructor.
     */
    public Librarian() {

    }

    /**
     * Sets the pointer to the file based on the string provided in the argument.
     *
     * @param p A filename to make the file pointer point to that file.
     */
    public static void setPath(String p) {
        path = p;
    }

    /**
     * This method opens a file to read data from, parses the JSON data into
     * an Object libraryRecordObject. If an exception happens during IO operation
     * or during parsing the json data, it will be logged to a logger file handled
     * and gets handled gracefully by this method.
     */
    public static void readFile() {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(path)) {
            libraryRecord = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("IOException happened on the file while " +
                    "reading data from \"" + path + "\" file");
            e.printStackTrace();
        }
    }

    /**
     * This method opens a file to write data to, stringifies the JSON Object
     * and then write the data to the json file. If an exception happens during
     * IO operation it will be logged to a logger file handled
     * and gets handled gracefully by this method.
     */
    public static void writeFile() {
        try (FileWriter file = new FileWriter(path)) {
            file.write(libraryRecord.toJSONString());
        } catch (IOException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("IOException happened on the file while " +
                    "writing data to \"" + path + "\" file");
            e.printStackTrace();
        }
    }

    /**
     * This method simply calls the readFile method to instantiate the libraryRecord object
     * to get the books JSONArray. The return value of getBooks method is simply returned
     * as a JSONArray.
     * @return JSONArray that holds whole catalogue of the available books in the library.
     */
    public static JSONArray getBooks() {
        readFile();
        return (JSONArray) libraryRecord.get("book");
    }

    /**
     * This method simply calls the readFile method to instantiate the libraryRecord object.
     * It also calls the getBooks function to get the entire
     * array of books, so a single book record can be return  a JSONObject
     * The return value of getBooks method is simply returned as a JSONArray.
     * @param ID The ID/ISBN of the book to get the record JSONObject returned.
     * @return JSONArray that holds whole catalogue of the available books in the library.
     */
    public static JSONObject getBook(long ID) {
        readFile();
        JSONArray books = getBooks();

        for ( Object book : books ) {
            long bookID = (long) ((JSONObject) book).get("ID");
            if (bookID == ID) {
                return (JSONObject) book;
            }
        }

        return null;
    }

    /**
     * This method basically takes the book information as parameters, creates a new object
     * which contains the needed information, pull the information from the Library record to be
     * appended to, and basically initializes the book itself to be added to the JSON database.
     * The validation part will check if the same book with the same ISBN exists, then it will not
     * add the book. Other validation for form fields includes checking whether only numbers were
     * entered for ID, pages and author and only alphabets were entered for name and author field.
     * @param ID The ID/ISBN of the book, unique number.
     * @param name The name of the book.
     * @param author The author of the book.
     * @param pages The total number of pages in the book.
     * @param stock The total available stock of the book.
     * @return String value, that will inform user of the error for any specific field if any.
     */
    public static String addBook(String ID, String name, String author,
                                 String pages, String stock) {
        JSONArray books = getBooks();
        JSONArray borrowedBy = new JSONArray();
        JSONArray waitingList = new JSONArray();
        JSONObject newBook = new JSONObject();
        boolean isPresent= false;

        if (!ID.matches("[0-9]+")){
            return "Only numbers are allowed for ISBN field!";
        }

        if (!name.matches("[a-zA-Z]+")){
            return "Only alphabets are allowed for Book Name field!";
        }

        if(!author.matches("[a-zA-Z]+")){
            return "Only alphabets are allowed for Author Name field!";
        }

        if (!pages.matches("[0-9]+")){
            return "Only numbers are allowed for Total Pages field!";
        }

        if (!stock.matches("[0-9]+")){
            return "Only numbers are allowed for Total Stock field!";
        }

        for ( Object book : books ) {
            long bookID = (long) ((JSONObject) book).get("ID");
            if (bookID == Long.parseLong(ID)) {
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            newBook.put("ID", Long.parseLong(ID));
            newBook.put("name", name);
            newBook.put("author", author);
            newBook.put("pages", Long.parseLong(pages));
            newBook.put("stock", Long.parseLong(stock));
            newBook.put("borrowedBy", borrowedBy);
            newBook.put("waitingList", waitingList);

            books.add(newBook);
            writeFile();
            return "Success";
        }
        return "A Book is already present in library with same ISBN!";
    }

    /**
     * Gets the entire catalog of books from the LibraryRecord object.
     *
     * @return A JSONArray that contains the all the books inside the library.
     */
    public static JSONArray booksCatalog() {
        return getBooks();
    }

    /**
     * This method automates the return process for the books, whenever a student ID
     * is passed, it will check the status of that student and only process the return if
     * that student was present inside the borrowed array. This is basically a validation check
     * that is the student was available only then it will remove that id from the list, as
     * well as remove the first person from queue inside the waitingList array, and append it
     * to the waiting list. This basically automates the process where the waiting-list will get
     * updated as there is a book available, and issues it to the next available student in queue.
     * @param studentID A unique identification number of the student.
     * @param bookID ISBN of the book, to be able to process information of that book.
     * @return Boolean value, that will mean true for successful return and false for error.
     */
    public static boolean returnBook(long studentID, long bookID) {
        JSONObject book = getBook(bookID);
        JSONArray borrowedBy = (JSONArray) book.get("borrowedBy");
        JSONArray waitingList = (JSONArray) book.get("waitingList");
        long currentStock = (long) book.get("stock");

        for ( Object student : borrowedBy ) {
            if ((long) student == studentID) {
                borrowedBy.remove(student);
                if (waitingList.size() > 0) {
                    borrowedBy.add(waitingList.get(0));
                    waitingList.remove(waitingList.get(0));
                } else {
                    book.put("stock", currentStock + 1);
                }

                writeFile();
                return true;
            }
        }

        return false;
    }

    /**
     * This method automates the borrowing process for the books, whenever a student ID
     * is passed, it will check the status of that student and only process the borrow if
     * the stock is over 0, which will then append that student inside the waiting list.
     * The book ID being passed will be used to get that book object and assign that student
     * to the borrowed books list, or waiting list, respectively.
     * @param studentID A unique identification number of the student.
     * @param bookID ISBN of the book, to be able to process information of that book.
     * @return Boolean value, that will mean true for successful borrowing and false for waiting list queue.
     */
    public static boolean borrowBook(long studentID, long bookID) {
        JSONObject book = getBook(bookID);
        JSONArray borrowedBy = (JSONArray) book.get("borrowedBy");
        JSONArray waitingList = (JSONArray) book.get("waitingList");
        long currentStock = (long) book.get("stock");

        if (currentStock > 0) {
            borrowedBy.add(studentID);
            book.put("stock", currentStock - 1);
            writeFile();
            return true;
        } else {
            waitingList.add(studentID);
            writeFile();
            return false;
        }
    }

    /**
     * This method is to delete a book, whenever a book ID is passed. It will get that
     * book JSONObject and use the remove method, to remove that from the books array.
     * The new books array will be replaced inside the Library record and will be
     * written to the file accordingly.
     * @param bookID ISBN of the book, to be able to process information of that book.
     * @return Boolean value, that will mean true for successful deletion and false for error.
     */
    public static boolean deleteBook(long bookID) {
        JSONArray books = getBooks();
        JSONObject book = getBook(bookID);

        books.remove(books.indexOf(book));
        libraryRecord.put("book", books);
        writeFile();
        return true;
    }

    /**
     * This method check the current status of a specific student, that if they are
     * still part of the waiting list, or they have moved to the borrowed list when
     * another student returned a book.
     * @param studentID A unique identification number of the student.
     * @param bookID ISBN of the book, to be able to process information of that book.
     * @return Boolean value, that will mean true for borrow list and false for waiting list.
     */
    public static boolean status(long studentID, long bookID) {
        JSONObject book = getBook(bookID);
        JSONArray waitingList = (JSONArray) book.get("waitingList");

        for ( Object student : waitingList ) {
            if ((long) student == studentID) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method is responsible for creating a Logger instance and a file handler
     * that will store the logs for any exceptions that might happen when the application
     * is running.
     * @return A logger instance that can be used to log information on logger files.
     */
    private static Logger setupLogger(){
        try {
            Logger logger = Logger.getLogger("fileLog");
            FileHandler fh = new FileHandler("resources/librarian/LibrarianLogger.txt", true);

            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            return logger;
        }
        catch (IOException ioe){
            System.out.println("File write error while writing data to the logger file.");
        }
        return null;
    }
}
