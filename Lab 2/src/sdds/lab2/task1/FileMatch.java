/**********************************************
 Workshop #2
 Course:BTP 400 Semester 4
 Last Name: Thaker
 First Name: Soham
 ID: 011-748-159
 Section: NBB
 This assignment represents my own work in accordance with Seneca Academic Policy.
 S.T.
 Date:16/02/2022
 **********************************************/

package sdds.lab2.task1;

import java.io.*;
import java.util.Iterator;
import java.util.Vector;

/**
 * FileMatch class implementation serializes dummy data provided into <b>oldmast.txt</b>
 * and <b>trans.txt</b> and deserializes, serialized data from those two files into
 * a Vector of Account and TransactionRecord objects respectively. It also performs file
 * matching functionality to match similar records, by utilizing accountNumber
 * attributes in Account and TransactionRecord and then writes them onto a file
 * named <b>newmast.txt</b> which holds updated serialized master data after the
 * current period has ended or a logs the unmatched data to <b>log.txt</b> file
 * if no match found.
 * @author Soham Thaker
 * @version 1.0
 * @since 08/02/2022
 * @see sdds.lab2.task1.TransactionRecord
 * @see sdds.lab2.task1.Account
 */
public class FileMatch {

    /**
     * A vector of Account objects that is used to store account holder details
     * read from <b>oldmast.txt</b> file and perform operations like combining
     * balance, setting or getting the data members of underlying type, iterating
     * over the collection to perform file matching operation and writing data
     * back to <b>newmast.txt</b> file.
     */
    private final Vector<Account> account = new Vector<>();

    /**
     * A vector of TransactionRecord objects that is used to store account holder details
     * read from <b>trans.txt</b> file and perform operations like setting or getting the
     * data members of underlying type, iterating over the collection to perform file
     * matching operation and logging messages to <b>log.txt</b> file.
     */
    private final Vector<TransactionRecord> trRecord = new Vector<>();

    /**
     * The constructor plays a vital role in this application. It first serializes the
     * dummy data provided for this application which is written onto <b>oldmast.txt</b>
     * and <b>trans.txt</b> by calling serializeMasterData() and serializeTransactionData()
     * private methods, then the same data is read and deserialized by calling deserializeMasterData()
     * and deserializeTransactionData() methods, which stores the data into respective
     * instance variables.
     */
    public FileMatch(){
        serializeMasterData();
        serializeTransactionData();
        deserializeMasterData();
        deserializeTransactionData();
    }

    /**
     * This method serializes the dummy data provided for this application
     * and writes it onto a file named <b>oldmast.txt</b>. First it adds all the data into a collection of
     * Account objects, then opens the file in output stream which is to be overwritten
     * in byte mode followed by serializing each Account object and then writing it
     * onto the file. If during the process any type of IOException is thrown, this method
     * gracefully handles it. It also implements try-with-resources code where try block automatically closes
     * the opened resource when cleaning of the resources needs to be done.
     */
    private void serializeMasterData() {
        Vector<Account> acc = new Vector<>();

        acc.add(new Account(100, "Alan Jones", 348.17));
        acc.add(new Account(300, "Mary Smith", 27.19));
        acc.add(new Account(500, "Sam Sharp", 0.00));
        acc.add(new Account(700, "Suzy Green", -14.22));

        File file = new File("src/sdds/lab2/task1/oldmast.txt");

        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);){
            for (Account a: acc) {
                oos.writeObject(a);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while serializing the object's state to oldmast.txt");
        }
    }

    /**
     * This method serializes the dummy data provided for this application and writes it onto
     * a file named <b>trans.txt</b>. First it adds all the data into a collection of
     * TransactionRecord objects, then opens the file in output stream which is to be overwritten
     * in byte mode followed by serializing each TransactionRecord object and then writing it
     * onto the file. If during the process any type of IOException is thrown, this method
     * gracefully handles it. It also implements try-with-resources code where try block
     * automatically closes the opened resource when cleaning of the resources needs to be done.
     */
    private void serializeTransactionData() {
        Vector<TransactionRecord> tr = new Vector<>();

        tr.add(new TransactionRecord(100, 27.14));
        tr.add(new TransactionRecord(300, 62.11));
        tr.add(new TransactionRecord(400, 100.56));
        tr.add(new TransactionRecord(900, 82.17));

        File file = new File("src/sdds/lab2/task1/trans.txt");

        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);){
            for (TransactionRecord t: tr) {
                oos.writeObject(t);
            }
        }
        catch(IOException e) {
            System.out.println("An error occurred while serializing the object's state to trans.txt");
        }
    }

    /**
     * This method deserializes the serialized object information that is stored within
     * <b>oldmast.txt</b> by opening the file in input stream mode and then deserializes the object information
     * stored onto the file by casting the data and then adds it to collection instance variable "account",
     * to make it accessible later to perform operations onto that collection object. If during
     * the process any type of Exception is thrown, this method gracefully handles it.
     * It also implements try-with-resources code where try block automatically closes the opened
     * resource when cleaning of the resources needs to be done.
     */
    private void deserializeMasterData(){
        File file = new File("src/sdds/lab2/task1/oldmast.txt");

        try(FileInputStream fin = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fin);){
            while(true){
                Account a = (Account) ois.readObject();
                account.add(a);
            }
        }
        catch(EOFException e){
            //ignore the exception
            //System.out.println("No more records in the file to read");
        }
        catch (ClassNotFoundException e) {
            System.out.println("A class named Account doesn't exist");
        }
        catch(IOException e) {
            System.out.println("An error occurred while deserializing the object's state from oldmast.txt");
        }
    }

    /**
     * This method deserializes the serialized object information that is stored within
     * <b>trans.txt</b> by opening the file in input stream mode and then deserializes the object information
     * stored onto the file by casting the data and then adds it the collection instance variable "trRecord",
     * to make it accessible later to perform operations onto that collection object. If during
     * the process any type of Exception is thrown, this method gracefully handles it.
     * It also implements try-with-resources code where try block automatically closes the opened
     * resource when cleaning of the resources needs to be done.
     */
    private void deserializeTransactionData(){

        File file = new File("src/sdds/lab2/task1/trans.txt");

        try(FileInputStream fin = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fin);){
            while(true){
                TransactionRecord tr = (TransactionRecord) ois.readObject();
                trRecord.add(tr);
            }
        }
        catch(EOFException e){
            //ignore the exception
            //System.out.println("No more records in the file to read");
        }
        catch (ClassNotFoundException e) {
            System.out.println("A class named TransactionRecord doesn't exist");
        }
        catch(IOException e) {
            System.out.println("An error occurred while deserializing the object's state from trans.txt");
        }
    }

    /**
     * This method iterates over the collection of Account and TransactionRecord
     * vector objects to check if there is match between the records of transaction and
     * master data by comparing the account number of an account element over the number of
     * trRecord elements. It uses a nested for loop and iterators to iterate over the collection.
     * The inner loop checks whether a single account number appears in any of the collection of
     * transaction record elements, if it does then combine the balance of current account with
     * the transaction amount of the current transaction record, remove that TransactionRecord
     * object from the collection and break out of the loop to avoid further iterations.
     * The purpose to remove the element is so that we are left with filtered collection of
     * TransactionRecord objects whose account number does not match with collection of Account
     * objects. Later, once the iteration is over, this filtered collection is used to log the
     * unmatched accounts who did not have any data stored on master data file by calling
     * "writeToLogFile()" method. Also, this method calls "writeToMasterFile()" method which writes
     * the updated collection of Accounts to another file.
     */
    public void writeData(){

        for (Iterator<Account> aIt = account.iterator(); aIt.hasNext();) {
            Account a = aIt.next();
            for (Iterator<TransactionRecord> trIt = trRecord.iterator(); trIt.hasNext();) {
                TransactionRecord tr = trIt.next();

                if(tr.getAccountNumber() == a.getAccountNumber()) {
                    a.combine(tr);
                    trIt.remove();
                    break;
                }
            }
        }

        writeToMasterFile();
        writeToLogFile();
    }

    /**
     * This method writes the collection data of instance variable "account" to the <b>newmast.txt</b> file
     * by serializing each element. It opens the file in output stream which is to be overwritten
     * in byte mode followed by serializing each Account object by iterating over the collection
     * which was updated in "writeData()" method and then writing that object onto the file.
     * If during the process any type of IOException is thrown, this method
     * gracefully handles it. It also implements try-with-resources code where try block
     * automatically closes the opened resource when cleaning of the resources needs to be done.
     */
    private void writeToMasterFile() {

        File file = new File("src/sdds/lab2/task1/newmast.txt");

        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);){
            for (Account acc: account) {
                oos.writeObject(acc);
            }
        }
        catch(IOException e) {
            System.out.println("An error occurred while serializing the object's state to newmast.txt");
        }
    }

    /**
     * This method writes the collection data of trRecord to the <b>log.txt</b> file.
     * It opens a file in write mode; if file doesn't exist then creates it or overwrites the
     * existing contents. It iterates over the filtered collection of TransactionRecord data
     * member which was updated in "writeData()" method and writes a string with appended
     * account number stored within each element of the collection onto the opened file.
     * The FileWriter throws an IOException when writing to a file which is
     * gracefully handled by this method. It aso implements try-with-resources code
     * where try block automatically closes the opened resource when cleaning of the
     * resources needs to be done.
     */
    private void writeToLogFile() {

        try(FileWriter writer = new FileWriter("src/sdds/lab2/task1/log.txt")){
            for (TransactionRecord tr: trRecord) {
                writer.write("Unmatched transaction record for account number: "
                        + tr.getAccountNumber() + "\n");
            }
        }
        catch(IOException e) {
            System.out.println("Error occurred while opening file in write mode!");
        }
    }

    /**
     * This method simply displays the deserialized data stored within the instance variable "account",
     * to the console for the user to view master data stored within <b>oldmast.txt</b> file.
     * It iterates over the collection and prints the elements in proper format.
     */
    public void viewOldMasterData() {
        System.out.println("Old master data contents: ");
        System.out.println("Accountholder name, Account Number, Balance");
        for (Account a: account) {
            System.out.printf("%18s%16d%9.2f\n", a.getName(), a.getAccountNumber(), a.getBalance());
        }
        System.out.println();
    }

    /**
     * This method simply displays the deserialized data stored within the instance variable "account",
     * to the console for the user to view the updated account information(master data)
     * after the current period has ended. It iterates over the collection and prints
     * the elements in proper format.
     */
    public void viewNewMasterData() {
        System.out.println("New master data contents: ");
        System.out.println("Accountholder name, Account Number, Balance");
        for (Account a: account) {
            System.out.printf("%18s%16d%9.2f\n", a.getName(), a.getAccountNumber(), a.getBalance());
        }
    }

    /**
     * This method simply displays the deserialized data stored within the instance variable "trRecord",
     * to the console for the user to view transaction record information stored within
     * <b>trans.txt</b> file. It iterates over the collection and prints the elements
     * in proper format.
     */
    public void viewTransactions() {
        System.out.println("\nTransaction records: ");
        System.out.println("Account Number, Transaction Amount");
        for (TransactionRecord tr: trRecord) {
            System.out.printf("%14d%20.2f\n", tr.getAccountNumber(), tr.getTransactionAmount());
        }
        System.out.println();
    }
}
