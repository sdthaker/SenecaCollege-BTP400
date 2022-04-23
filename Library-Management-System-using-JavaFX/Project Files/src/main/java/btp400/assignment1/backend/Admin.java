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
 * This class contains the methods that can be used for an admin in the library system.
 * An admin can log into the system, register a librarian, delete a librarian, and view
 * a list of currently registered librarians. This admin class can also read files containing
 * admins and librarians, and can write files with info of librarians and list of librarians.
 * @author Nathaniel Ling
 * @version 1.0.0
 * @see JSONObject
 * @see JSONArray
 * @see JSONParser
 * @see ParseException
 * @see Iterator
 * @see File
 * @see FileHandler
 * @see FileWriter
 * @see FileReader
 * @see String
 * @see Object
 * @see IOException
 * @see Integer
 * @see Logger
 * @see SimpleFormatter
 * @see java.util.regex.Pattern
 * @see java.util.regex.Matcher
 */
public class Admin {

    /**
     * A JSONArray which holds JSONObjects of admin objects read from the admins.json file.
     */
    private JSONArray admins = new JSONArray();

    /**
     * A JSONArray which holds JSONObjects of librarian objects read from the librarians.json file.
     */
    private JSONArray librarians = new JSONArray();

    /**
     * A String which holds the username of the admin user currently logged in.
     */
    private String loggedInAdmin = null;

    /**
     * A File which points to the data source.
     */
    private File file = null;

    /**
     * A static int which is used as the base number to generate new Employee Id's.
     */
    private static int idGen = 100;

    /**
     * Default constructor for Admin class.
     */
    public Admin() {}

    /**
     * Getter function to return the value of loggedInAdmin.
     * @return String of the current admin username.
     */
    public String getLoggedInAdmin() {
        return loggedInAdmin;
    }

    /**
     * Setter function to set this.loggedInAdmin to the value of the parameter.
     * @param loggedInAdmin String which this.loggedInAdmin is set to.
     */
    public void setLoggedInAdmin(String loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
    }

    /**
     * Setter function to set this.file to the value of the parameter.
     * @param f String which this.file is set to.
     */
    public void setFile(String f) {
        this.file = new File(f);
    }

    /**
     * This method is used to validate the admin user by comparing the username and password entered
     * by the user to the admin objects in the admins JSONArray. It iterates through the admins array
     * and checks that both username input and password input have a match in the admins array. If there
     * is a match, the iteration ends and this.loggedInAdmin is set to the username input and returns a
     * success message. If there are no matches, it returns an error message.
     * @param usernameInput String which is compared to the "username" key of the admin object.
     * @param passwordInput String which is compared to the "password" key of the admin object.
     * @return String which indicates the success or error of the method.
     */
    public String login(String usernameInput, String passwordInput){
        boolean check = false;
        Iterator i = this.admins.iterator();

        while(i.hasNext()) {
            Object elem = i.next();
            if(((JSONObject)elem).get("username").equals(usernameInput) && ((JSONObject)elem).get("password").equals(passwordInput)){
                check = true;
                this.loggedInAdmin = usernameInput;
                break;
            }
        }
        return !check ? "The username or password entered does not exist in the system. Please try again." : "Success";
    }

    /**
     * Method for testing librarian login purposes only
     * @param userInputId Integer to compare against librarians "id" key
     * @param userInputPassword String to compare against librarians "password" key
     * @return String which indicates the success of the method
     */
    public String libLogin(int userInputId, String userInputPassword) {
        boolean check = false;
        String msg = "";
        Iterator v = this.librarians.iterator();
        while(v.hasNext()) {
            Object elem = v.next();

            if (Integer.parseInt(((JSONObject)elem).get("id").toString()) == userInputId && ((JSONObject)elem).get("password").equals(userInputPassword)) {
                check = true;
                break;
            }
            else if (!((JSONObject)elem).get("id").equals(userInputId)) {
                msg = "The id or password entered does not exist in the system. Please try again.";
            }
            else {
                msg = "Unknown error. Please try again.";
            }
        }
        return !check ? msg : "Success";
    }

    /**
     * This method registers a new librarian to the system and writes the changes into the
     * librarians.json file. It takes input from the user for fname, lname, email, and password,
     * and generates an id using the generateId method. It restricts the symbols allowed for fname
     * and lname to alphabetical symbols only. The email must follow a typical email format.
     * If all the cases are passed, a new JSONObject is created to store the values entered by the
     * user, along with the generated id, and adds it to the librarians JSONArray.
     * @param libInputFname String which will set the first half of the value for the "name" key for the librarian object.
     * @param libInputLname String which will set the second half of the value for the "name" key for the librarian object.
     * @param libInputEmail String which will set the value for the "email" key for the librarian object.
     *                      Also must be unique amongst other "email" keys in the librarians JSONArray.
     * @param libInputPassword String which will set the value for the "password" key for the librarian object.
     * @return String value that indicates the success of the method.
     */
    public String registerLibrarian(String libInputFname, String libInputLname, String libInputEmail, String libInputPassword) {
        Iterator v = this.librarians.iterator();

        Object elem;
        do {
            if (!v.hasNext()){
                if (!libInputFname.matches("[a-zA-Z]+")) {
                    return "Only alphabetical symbols are allowed for first name";
                }

                if (!libInputLname.matches("[a-zA-Z]+")) {
                    return "Only alphabetical symbols are allowed for last name";
                }

                if (!isValidEmailAddress(libInputEmail)) {
                    return "Email Address must follow the following format: 'example@email.com'";
                }

                JSONObject librarian = new JSONObject();
                librarian.put("id", generateId());
                librarian.put("name", libInputFname + " " + libInputLname);
                librarian.put("email", libInputEmail);
                librarian.put("password", libInputPassword);
                this.librarians.add(librarian);
                return "Success";
            }
            elem = v.next();
        } while(!((JSONObject)elem).get("email").equals(libInputEmail));

        return "Email already exists in the system, please enter a different email";
    }

    /**
     * This method deletes a librarian from the librarians JSONArray that has an id
     * that matches the inputId from the user. If a match is found, the librarian object
     * is removed from the librarians JSONArray.
     * @param inputId Integer value which is compared against the "id" key of the librarian objects.
     * @return String which indicates the success of the method.
     */
    public String deleteLibrarian(int inputId) {
        boolean check = false;
        Iterator v = this.librarians.iterator();
        while(v.hasNext()) {
            Object elem = v.next();

            if (Integer.parseInt(((JSONObject)elem).get("id").toString()) == inputId) {
                check = true;
                librarians.remove(librarians.indexOf(elem));
                break;
            }
        }
        return !check ? "No Librarian matches the employee Id entered." : "Success";
    }

    /**
     * This method accesses the librarians.json file using the setFile method
     * and reads the data from the file using readDataFromFile method.
     * @return JSONArray of librarians which holds the librarian JSONObjects.
     */
    public JSONArray viewLibrarians() {
        return librarians;
    }

    /**
     * This method creates a Logger instance and FileHandler that stores
     * the logs for exceptions that may occur in the adminLogger.txt file.
     * @return Logger that can log information on logger files.
     */
    private Logger setupLogger(){
        try {
            Logger logger = Logger.getLogger("fileLog");
            FileHandler fh = new FileHandler("resources/admin/adminLogger.txt", true);

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
     * This method generates a unique id for a librarian JSONObject based on the current
     * number of librarians in the JSONArray.
     * @return Integer which holds the value intended for the "id" key of the librarian JSONObject.
     */
    private int generateId() {
        Iterator v = this.librarians.iterator();
        Object obj;
        int counter=0;
        while(v.hasNext()) {
            obj = v.next();
            if (Integer.parseInt(((JSONObject)obj).get("id").toString()) == (idGen + this.librarians.size() + counter)) {
                counter++;
            }
        }
        return idGen + this.librarians.size() + counter;
    }

    /**
     * This method checks the format of the String to match a typical email format.
     * e.g. this.example1@email.com would work, t@.his@email.ca.com would not work.
     * @param email String to be checked for proper email formatting.
     * @return boolean returns true if the email format is valid, else returns false.
     */
    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * This method reads a file using FileReader and JSONParser to parse the
     * data into an object. If the data to be read is a JSONArray of admins,
     * this.admins JSONArray is set to the parsed data object. If it is a
     * JSONArray of librarians, this.librarians JSONArray is set to the
     * data. Any exceptions are logged by setupLogger method to the adminLogger.txt file.
     * @param admin boolean which indicates whether the data should be admin or librarian.
     */
    public void readDataFromFile(boolean admin) {
        JSONParser p = new JSONParser();

        try (FileReader in = new FileReader(file)){
            Object obj = p.parse(in);
            if (admin) {
                this.admins = (JSONArray) obj;
            }
            else {
                this.librarians = (JSONArray) obj;
            }
        } catch (IOException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("IOException occurred while reading data from \""+file.getName()+"\" file");
        } catch (ParseException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("ParseException while reading data from \""+file.getName()+"\" file");
        }
    }

    /**
     * This method writes data to the file that this.file points to.
     * The FileWriter writes the String returned by String.valueOf method
     * of the JSONArray. Any exceptions are logged using setupLogger and
     * are written to adminLogger.txt
     */
    public void writeDataToFile() {
        try (FileWriter file = new FileWriter(this.file)) {
            file.write(String.valueOf(this.librarians));
        } catch (IOException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("IOException occurred while writing data to \""+file.getName()+"\" file");
        }
    }
}
