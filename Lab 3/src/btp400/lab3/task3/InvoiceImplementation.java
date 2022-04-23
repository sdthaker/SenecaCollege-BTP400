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

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implements the functions that meet the requirements of the lab.
 * @author Soham Thaker
 * @version 1.0
 * @see Invoice
 * @see ArrayList
 * @see java.util.stream.Stream
 */
public class InvoiceImplementation {

    /**
     * Creates an ArrayList of Invoice using the dummy data provided by the lab and returns it to the caller.
     * @return An ArrayList of type Invoice that contains multiple Invoice elements.
     */
    private static ArrayList<Invoice> setupInvoice() {
        ArrayList<Invoice> invoice = new ArrayList<>();

        invoice.add(new Invoice(83, "Electric sander", 7, 57.98));
        invoice.add(new Invoice(24, "Power saw", 18, 99.99));
        invoice.add(new Invoice(7, "Sledge hammer", 11, 21.50));
        invoice.add(new Invoice(77, "Hammer", 76, 11.99));
        invoice.add(new Invoice(39, "Lawn mover", 3, 79.50));
        invoice.add(new Invoice(68, "Screwdriver", 106, 6.99));
        invoice.add(new Invoice(56, "Jig saw", 21, 11.00));
        invoice.add(new Invoice(3, "Wrench", 34, 7.50));

        return invoice;
    }

    /**
     * Operates the collection on a stream where the stream sorts each
     * Invoice object by part description using Comparator functional interface
     * and then prints each Invoice object using forEach.
     */
    public static void sortByPartDesc(){
        setupInvoice()
                .stream()
                .sorted(Comparator.comparing(Invoice::getPartDescription))
                .forEach(System.out::println);
    }

    /**
     * Operates the collection on a stream where the stream sorts each
     * Invoice object by price using Comparator functional interface
     * and then prints each Invoice object out using forEach.
     */
    public static void sortByPrice(){
        setupInvoice()
                .stream()
                .sorted(Comparator.comparing(Invoice::getPrice))
                .forEach(System.out::println);
    }

    /**
     * Operates the collection on a stream where the stream sorts each
     * Invoice object by quantity using Comparator functional interface,
     * maps the object to part description and quantity
     * and then prints out each object using forEach.
     */
    public static void sortByQuantity(){
        setupInvoice()
                .stream()
                .sorted(Comparator.comparing(Invoice::getQuantity))
                .map(invoice -> String.format("Description: %-15s Quantity: %-4d"
                        ,invoice.getPartDescription(), invoice.getQuantity()))
                .forEach(System.out::println);
    }

    /**
     * Operates the collection on a stream where the stream sorts each
     * Invoice object by value of the invoice using Comparator functional
     * interface, maps the object to part description and quantity
     * and then prints out each object using forEach.
     */
    public static void sortByInvoiceValue(){
        setupInvoice()
                .stream()
                .sorted(Comparator.comparing(invoice -> invoice.getPrice() * invoice.getQuantity()))
                .map(invoice ->
                        String.format("Description: %-15s Value: %-2.2f"
                                ,invoice.getPartDescription(), (invoice.getQuantity() * invoice.getPrice())))
                .forEach(System.out::println);
    }

    /**
     * Operates the collection on a stream where the stream filters
     * each invoice object's value within a range, sorts each
     * Invoice object by value of the invoice using Comparator functional
     * interface maps the object to part description and quantity
     * and then prints out each object using forEach.
     */
    public static void sortByInvoiceValueInARange() {
        setupInvoice()
                .stream()
                .filter(invoice -> (invoice.getPrice() * invoice.getQuantity() >= 200
                                    && invoice.getPrice() * invoice.getQuantity() <= 500))
                .sorted(Comparator.comparing(invoice -> invoice.getPrice() * invoice.getQuantity()))
                .map(invoice ->
                        String.format("Description: %-15s Value: %-2.2f"
                                ,invoice.getPartDescription(), (invoice.getQuantity() * invoice.getPrice())))
                .forEach(System.out::println);
    }
}
