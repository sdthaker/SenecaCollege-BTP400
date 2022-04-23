/**********************************************
 * Workshop #
 * Course:BTP400NBB - Semester 4
 * Last Name: Thaker
 * First Name: Soham
 * ID: 011-748-159
 * Section: NBB
 * This assignment represents my own work in accordance with Seneca Academic Policy.
 * Signature Date: 27/03/2022
 * **********************************************/

package btp400.lab3.task3;

/**
 * Tests the functions defined in InvoiceImplementation class.
 * @author Soham Thaker
 * @version 1.0
 * @see InvoiceImplementation
 */
public class InvoiceTester {

    /**
     * Tests the functions according to the lab requirements.
     * @param args Command line arguments passed from the terminal.
     */
    public static void main(String[] args) {

        System.out.println("=========Sort by Part Description=========");
        InvoiceImplementation.sortByPartDesc();

        System.out.println("\n=========Sort by Part Price=========");
        InvoiceImplementation.sortByPrice();

        System.out.println("\n=========Sort by Quantity=========");
        InvoiceImplementation.sortByQuantity();

        System.out.println("\n=========Sort by Invoice Value=========");
        InvoiceImplementation.sortByInvoiceValue();

        System.out.println("\n=========Sort by Invoice Value Within >=200 & <=500 range=========");
        InvoiceImplementation.sortByInvoiceValueInARange();
    }
}
