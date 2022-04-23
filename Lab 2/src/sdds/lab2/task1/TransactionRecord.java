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

import java.io.Serializable;

/**
 * TransactionRecord class holds data, that is stored inside the transaction file for
 * each account for a given period. It defines 2 constructors and getter setter methods
 * for all the attributes in this class.
 * @author Soham Thaker
 * @version 1.0
 * @since 08/02/2022
 * @see Serializable
 */
public class TransactionRecord implements Serializable {

    /**
     * A unique account number that is being retrieved from the master
     * data file and then stored into this attribute.
     */
    private long accountNumber;

    /**
     * A transaction amount of the account holder that is either a
     * purchase, indicated as positive number or payment indicated as negative number
     * made by the account holder at any given time, during a transaction.
     */
    private double transactionAmount;

    /**
     * A custom constructor that initializes an instance of TransactionRecord
     * and its data members to the values that are passed in as arguments to this constructor.
     * @param accountNumber unique account number of the user.
     * @param transactionAmount a specific transaction amount generated once user makes a payment or purchase.
     */
    public TransactionRecord(long accountNumber, double transactionAmount) {
        this.accountNumber = accountNumber;
        this.transactionAmount = transactionAmount;
    }

    /**
     * A default constructor that initializes an empty instance of Account. In other terms
     * it initializes that current object's attributes to its default value.
     */
    public TransactionRecord() {this(0,0.0);}

    /**
     * A public getter method that returns the accountNumber attribute stored within current object when called.
     * @return accountNumber attribute of the current object.
     */
    public long getAccountNumber() {
        return accountNumber;
    }

    /**
     * A public setter method that sets the accountNumber attribute stored within current object when called.
     * @param accountNumber a long number that is passed to this function when called so that
     *                      current instance's accountNumber attribute can be set to the value of argument.
     */
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * A public getter method that returns the transactionAmount attribute stored within current object when called.
     * @return transactionAmount attribute of the account holder which stores the information regarding account's holders payments or purchases
     */
    public double getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * A public setter method that sets the transactionAmount attribute stored within current object when called.
     * @param transactionAmount a double number that is passed to this function when called so that
     *                          current instance's transactioAmount attribute can be set to the value of argument.
     */
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
