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

// Invoice.java

/**
 * Declares necessary methods and attributes for the tester to test
 * lab requirements on class instances.
 */
public class Invoice {

    /**
     * Attribute of type int.
     */
    private final int partNumber;

    /**
     * Attribute of type String.
     */
    private final String partDescription;

    /**
     *  Attribute of type int.
     */
    private int quantity;

    /**
     *  Attribute of type double.
     */
    private double price;

    /**
     * Initializes the attributes with the arguments passed. Throws exception
     * if quantity or price arguments are below 0.
     * @param partNumber partNumber value for current instance.
     * @param partDescription partDescription value for current instance.
     * @param quantity quantity value for current instance.
     * @param price price value for current instance.
     */
    public Invoice(int partNumber, String partDescription, int quantity,
                   double price) {
        if (quantity <0) { // validate quantity
            throw new IllegalArgumentException("Quantity must be>= 0");
        }

        if (price <0.0) { // validate price
            throw new IllegalArgumentException(
                    "Price per item must be>= 0");
        }

        this.partNumber = partNumber;
        this.partDescription = partDescription;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Getter for pertNumber attribute.
     * @return partNumber for current instance.
     */
    public int getPartNumber() {
        return partNumber; // should validate
    }

    /**
     * Getter for partDescription attribute.
     * @return partDescription for current instance.
     */
    public String getPartDescription() {
        return partDescription;
    }

    /**
     * Setter for quantity.
     * @param quantity quantity to be assigned to the current instances' quantity attribute.
     */
    public void setQuantity(int quantity) {
        if (quantity <0) { // validate quantity
            throw new IllegalArgumentException("Quantity must be>= 0");
        }

        this.quantity = quantity;
    }

    /**
     * Getter for quantity attribute.
     * @return quantity attribute for current instance.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter for price.
     * @param price price to be assigned to the current instances' price attribute.
     */
    public void setPrice(double price) {
        if (price <0.0) { // validate price
            throw new IllegalArgumentException(
                    "Price per item must be>= 0");
        }

        this.price = price;
    }

    /**
     * Getter for price attribute.
     * @return price attribute for current instance.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Override of Object's toString method to print the current instance's attributes
     * in formatted manner.
     * @return Formatted string.
     */
    @Override
    public String toString() {
        return String.format(
                "Part #: %-2d  Description: %-15s  Quantity: %-4d  Price: $%,6.2f",
                getPartNumber(), getPartDescription(),
                getQuantity(), getPrice());
    }
}

