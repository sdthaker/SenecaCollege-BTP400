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
 * @author Soham Thaker
 * @version 1.0.0
 * @see Librarian
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
public class Student {

    /**
     * Holds an array of JSON objects where each object is a student
     * object. The data that stored into this attribute is read from students.json file.
     */
    private JSONArray students = new JSONArray();

    /**
     * Holds the student's SID of the logged in student in the system.
     */
    private String sid = null;

    /**
     * Holds the file pointer which points where to read the data from.
     */
    private File file = null;

    /**
     * Default constructor.
     */
    public Student() {
    }

    /**
     * Sets the value of current instance's sid to the value of argument.
     *
     * @param sid A String that holds the information about the Student's SID.
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * Gets the value of current instance's sid.
     *
     * @return A String that contains the SID value of current student.
     */
    public String getSid() {
        return this.sid;
    }

    /**
     * Get the name of current student
     * @return A String that contains the name of the student
     */
    public String getName() {
        for(Object student: students){
            if(((JSONObject) student).get("sid").equals(sid)){
                return (String) ((JSONObject) student).get("name");
            }
        }
        return "";
    }

    /**
     * Sets the pointer to the file based on the string provided in the argument.
     *
     * @param f A filename to make the file pointer point to that file.
     */
    public void setFile(String f) {
        file = new File(f);
    }

    /**
     * This method validates the user input and makes sure that the user's input
     * was a valid input. It checks for length of the input, whether the input
     * only contains numbers or already existing sid in the system. Depending on
     * that it returns a signal to the caller whether the user is allowed to log
     * in or not. It also checks and updates the waiting list if any book was made
     * available to this student by checking it every time when the user logs in.
     * It makes a call to the Librarian module to check if the waiting list was updated;
     * if updated, then modifies the underlying json object and writes the updated
     * data to the students.json file.
     * @param userInputSid A String which holds the SID value that
     *                     the user used to log in into the system.
     * @return A String that lets the caller know whether the user is authorized to log in.
     */
    public String login(String userInputSid) {
        boolean check = false, updated = false;

        if (!userInputSid.matches("[0-9]+"))
            return "Only numbers are allowed for SID field!";

        if (userInputSid.length() != 9)
            return "SID must be exactly 9 digits in length!";

        for (Object elem : students) {
            if (((JSONObject) elem).get("sid").equals(userInputSid)) {
                check = true;
                this.sid = userInputSid;
                break;
            }
        }

        if (!check) {
            return "This user doesn't exist in the system. " +
                    "Please try different number!";
        }

        for (Object s : students) {
            if (((JSONObject) s).get("sid").equals(userInputSid)) {
                JSONArray waiting = (JSONArray) ((JSONObject) s).get("waiting");
                JSONArray borrowedBooks = (JSONArray) ((JSONObject) s).get("books");

                for (Iterator wIt = waiting.iterator(); wIt.hasNext(); ) {
                    Object w = wIt.next();

                    if (Librarian.status(Long.parseLong(sid), (Long) ((JSONObject) w).get("ID"))) {
                        wIt.remove();
                        borrowedBooks.add(w);
                        updated = true;
                    }
                }

                if (updated) {
                    writeDataToFile();
                }
            }
        }
        return "Success";
    }

    /**
     * This method validates the user input and makes sure that the user's input
     * was a valid input. It checks for length of the SID, whether the SID
     * only contains numbers or already existing SID in the system.
     * It also checks whether first and last names are only characters.
     * Depending on that it returns a signal to the caller whether the user is
     * successfully registered into the system. Besides, it appends the newly
     * registered student to the existing structure of students and adds
     * key value-pair of sid, name, borrowed books and books on waiting list.
     *
     * @param userInputFname User inputted string value of first name.
     * @param userInputLname User inputted string value of last name.
     * @param userInputSid   User inputted string value of SID.
     * @return A String that lets the caller know whether the user was registered into the system.
     */
    public String register(String userInputFname,
                           String userInputLname,
                           String userInputSid) {

        if (!userInputSid.matches("[0-9]+"))
            return "Only numbers are allowed for SID field!";

        if (userInputSid.length() != 9)
            return "SID must be exactly 9 digits in length!";

        for (Object elem : students) {
            if (((JSONObject) elem).get("sid").equals(userInputSid)) {
                return "SID already exists in the system, please enter a different SID!";
            }
        }

        if (!userInputFname.matches("[a-zA-Z]+"))
            return "Characters ranging from a-z or A-Z are allowed for first name!";

        if (!userInputLname.matches("[a-zA-Z]+")) {
            return "Characters ranging from a-z or A-Z are allowed for last name!";
        }

        JSONObject student = new JSONObject();
        JSONArray array = new JSONArray();
        student.put("name", userInputFname + " " + userInputLname);
        student.put("sid", userInputSid);
        student.put("books", array);
        student.put("waiting", array);
        students.add(student);
        return "Success";
    }

    /**
     * This method is responsible for creating a Logger instance and a file handler
     * that will store the logs for any exceptions that might happen when the application
     * is running.
     * @return A logger instance that can be used to log information on logger files.
     */
    private Logger setupLogger(){
        try {
            Logger logger = Logger.getLogger("fileLog");
            FileHandler fh = new FileHandler("resources/student/studentLogger.txt", true);

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

    /**
     * This method opens a file to read data from, parses the JSON data into
     * an Object, then casts that Object to JSONArray to get the underlying
     * array which holds the relevant student information, finally assigning
     * it to the students attribute. If an exception happens during IO operation
     * or during parsing the json data, it will be logged to a logger file handled
     * and gets handled gracefully by this method.
     */
    public void readDataFromFile() {

        JSONParser parser = new JSONParser();

        try (FileReader in = new FileReader(file)) {
            Object obj = parser.parse(in);
            students = (JSONArray) obj;
        }
        catch (IOException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("IOException happened on the file while " +
                    "reading data from \"" + file.getName() + "\" file");
        }
        catch (ParseException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("JSON Parsing exception when reading from " +
                    "\"" + file.getName() + "\" file");
        }
    }

    /**
     * This method opens a file to write data to, stringifies the JSON array
     * and then write the data to the json file. If an exception happens during
     * IO operation it will be logged to a logger file handled
     * and gets handled gracefully by this method.
     */
    public void writeDataToFile() {
        try (FileWriter file = new FileWriter(this.file)) {
            file.write(String.valueOf(students));
        } catch (IOException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("IOException happened on the file while " +
                    "writing data to \"" + file.getName() + "\" file");
        }
    }

    /**
     * This method simply calls the getBooks method on Librarian module which returns
     * the books JSONArray. The return value of getBooks method is simply returned
     * to the caller.
     * @return JSONArray that holds whole catalogue of the available books in the library.
     */
    public JSONArray searchBooks() {
        return Librarian.getBooks();
    }

    /**
     * This method is responsible to run the functionality of borrowing a book. It iterates over the
     * students JSONArray and finds the sid that matches with the logged in student. It then checks
     * the borrowed books and waiting list JSONArray to see if the book is already part of either
     * of these two arrays. If it is, then it simply returns a signal, otherwise informs the Librarian
     * module by passing the logged in student's sid and the borrowing book's id. Depending on the return
     * value, it adds the book to the borrowed books if Librarian module returned true/issued the book
     * or adds the book to the waiting list otherwise if Librarian module returned false/rejected the
     * issuance the book for the logged in student.
     * @param book A JSONObject that holds the data of the book that needs to be borrowed.
     * @return A String that acts a signal which sends the information whether the books
     *          borrowed or put on waiting list.
     */
    public String borrowABook(JSONObject book) {

        for (Object student : students) {
            if (((JSONObject) student).get("sid").equals(sid)) {

                JSONArray books = ((JSONArray) ((JSONObject) student).get("books"));
                JSONArray waitingList = ((JSONArray) ((JSONObject) student).get("waiting"));

                for (Object b : books) {
                    if (((JSONObject) b).get("ID").equals(book.get("ID"))) {
                        return "Book already borrowed!";
                    }
                }
                for (Object wL : waitingList) {
                    if (((JSONObject) wL).get("ID").equals(book.get("ID"))) {
                        return "Book still in waiting list!";
                    }
                }

                if (Librarian.borrowBook(Long.parseLong(sid), (Long) book.get("ID"))) {
                    ((JSONArray) ((JSONObject) student).get("books")).add(book);
                    ((JSONArray) ((JSONObject) student).get("waiting")).remove(book);
                    return "Book successfully borrowed!";
                } else {
                    ((JSONArray) ((JSONObject) student).get("waiting")).add(book);
                    return "Book put on waiting list!";
                }
            }
        }
        return "";
    }

    /**
     * This method iterates over the attribute students, finds the JSON object
     * whose SID matches with the currently logged in student, extracts the books
     * array out of that object, iterates over that book object to find the book whose
     * uid matches with the argument, removes the book from the books JSONArray and
     * returns the book back to library by making a call to the Librarian module
     * and passing the logged in student's sid and the book id.
     * @param book A book that needs to be returned.
     * @return A boolean value that indicates whether book was returned successfully.
     */
    public boolean returnABook(JSONObject book) {
        for (Object s : students) {
            if (((JSONObject) s).get("sid").equals(sid)) {
                JSONArray books = (JSONArray) ((JSONObject) s).get("books");
                for (Object b : books) {
                    if (((JSONObject) b).get("ID").equals(book.get("ID"))) {
                        if (Librarian.returnBook(Long.parseLong(sid), (Long) book.get("ID"))) {
                            books.remove(b);
                            return true;
                        }
                        break;
                    }
                }
                break;
            }
        }
        return false;
    }

    /**
     * This is a helper method is used in unit tests to test if a book was
     * returned or borrowed successfully. It takes an uid of the book and
     * goes through the entire structure to find if it exists. If it exists
     * it returns the uid of the book or returns null.
     * @param id Ad id of that book that needs to looked up.
     * @return A string that has a value of either a null value or the uid found in the structure.
     */
    public JSONObject getABorrowedBook(Long id) {
        for (Object s : students) {
            if (((JSONObject) s).get("sid").equals(sid)) {
                JSONArray books = (JSONArray) ((JSONObject) s).get("books");
                for (Object b : books) {
                    if (((JSONObject)b).get("ID").equals(id)) {
                        return (JSONObject) b;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method iterates over the student JSONArray and returns the books array
     * for the logged in student.
     * @return A JSONArray that holds books which were borrowed by logged in student.
     */
    public JSONArray getAllBorrowedBooks() {
        for (Object s : students) {
            if (((JSONObject) s).get("sid").equals(sid)) {
                return (JSONArray) ((JSONObject) s).get("books");
            }
        }
        return null;
    }
}