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

package sdds.lab2.task2;

/**
 * Task1Tester is a class that merely instantiates a FileMatch class object
 * and then calls three important methods that read and write data from and to the file.
 *
 * @author Soham Thaker
 * @version 1.0
 * @since 08/02/2022
 * @see sdds.lab2.task2.FileMatch
 */
public class Task2Tester {

    /**
     * main function calls,
     * readMasterData function that attempts to read master data from a file,
     * readTransactionData function that attempts to read transaction data from a file,
     * writeData function that attempts to write data either to master file or a log file.
     *
     * @param args This is the only param into the main method passed from command line as command line args.
     */
    public static void main(String[] args) {
        FileMatch fm = new FileMatch();
        fm.viewTransactions();
        fm.viewOldMasterData();
        fm.writeData();
        fm.viewNewMasterData();
    }
}
