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

package btp400.lab3.task1;

import java.text.DecimalFormat;

import static btp400.lab3.task1.DoubleArrayFunctionsImplementation.*;

/**
 * A tester class to test the functionality of task 1
 * @author Soham Thaker
 * @version 1.0
 * @see DoubleArrayFunctionsImplementation
 */
public class DAFTester {

    /**
     * A double array that holds the user entered values.
     */
    static double[] array;

    /**
     * Tests entire solution by calling necessary methods and displays the output to the user.
     * @param args Command line arguments passed from the terminal.
     */
    public static void main(String[] args) {
        array = userInputForCollection();
        System.out.println("Minimum number in collection is: " + min.applyDouble(array));
        System.out.println("Maximum number in collection is: " + max.applyDouble(array));
        System.out.println("Sum of all numbers in collection is: " +  new DecimalFormat("#.##").format(sum.applyDouble(array)));
        System.out.println("Average of all number in collection is: " + new DecimalFormat("#.##").format(avg.applyDouble(array)));

        double value = userInputForSearch();
        double count = counter(value).applyDouble(array);
        if(count == 0)
            System.out.println(value + " is not present in collection!");
        else
            System.out.println(value + " appears " + (int)count + " time(s) in the collection!");
    }
}
