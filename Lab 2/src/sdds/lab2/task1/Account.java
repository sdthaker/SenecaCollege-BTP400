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
 * Account class holds data, that is stored inside the master data file for each account.
 * It defines 2 constructors and getter setter methods for all the attributes in this class.
 * @author Soham Thaker
 * @version 1.0
 * @since 08/02/2022
 * @see sdds.lab2.task1.TransactionRecord
 * @see Serializable
 */
public class Account implements Serializable {
    /**
     * A unique account number that is being retrieved from the master
     * data file and then stored into this attribute.
     */
    private long accountNumber;

    /**
     * Balance of the account holder that is a running total of
     * transactions and payments made by the account holder at
     * the end of each billing cycle.
     */
    private double balance;

    /**
     * Holds the name of the account holder.
     */
    private String name;

    /**
     * A custom constructor that initializes an instance of Account and its data members
     * to the values that are passed in as arguments to this constructor.
     * @param accountNumber unique account number of the user.
     * @param name name of the user.
     * @param balance balance of the user.
     */
    public Account(long accountNumber, String name, double balance) {
        super();
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.name = name;
    }

    /**
     * A default constructor that initializes an empty instance of Account by calling the
     * three argument constructor. In other terms it initializes that current object's
     * attributes to its default value.
     */
    public Account(){
        this(0,"",0.0);
    }

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
     * A public getter method that returns the balance attribute stored within current object when called.
     * @return balance attribute of the current object.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * A public setter method that sets the balance attribute stored within current object when called.
     * @param balance a double number that is passed to this function when called so that
     *                current instance's balance attribute can be set to the value of argument.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * A public getter method that returns the name attribute stored within current object when called.
     * @return name attribute of the current object.
     */
    public String getName() {
        return name;
    }

    /**
     * A public setter method that sets the name attribute stored within current object when called.
     * @param name a String that is passed to this function when called so that
     *                current instance's name attribute can be set to the value of argument.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method stores result of balance stored within the current instance
     * and any transactions that the user might have in any given cycle back
     * into the current instance's balance attribute. It gets the transaction amount
     * be receiving an instance of TransactionRecord and making a call to getter
     * method of the received instance. A transaction amount can be a positive number
     * which indicates the amount was a purchase whereas a negative number indicates
     * the amount was a payment.
     * @param tr A TransactionRecord instance that is used to query the transaction amount
     *           whose return value is added back into the current instance's balance attribute.
     */
    public void combine(TransactionRecord tr){
        this.balance += tr.getTransactionAmount();
    }
}
