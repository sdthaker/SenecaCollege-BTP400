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

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Implements the DoubleArrayFunctions' lambda and
 * necessary functions that are needed to fulfill the task 1 requirements.
 * @author Soham Thaker
 * @version 1.0
 * @see DoubleArrayFunctions
 *
 */
public class DoubleArrayFunctionsImplementation {

    /**
     * A DoubleArrayFunctions lambda that finds the minimum value in the collection.
     */
    public static final DoubleArrayFunctions min = (double[] arr) -> {
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] < min)
                min = arr[i];
        }
        return min;
    };

    /**
     * A DoubleArrayFunctions lambda that finds the maximum value in the collection.
     */
    public static final DoubleArrayFunctions max = (double[] arr) -> {
        double max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
        }
        return max;
    };

    /**
     * A DoubleArrayFunctions lambda that finds the sum of all numbers in the collection.
     */
    public static final DoubleArrayFunctions sum = (double[] arr) -> {
        double sum = 0.0;
        for (double v : arr) {
            sum += v;
        }
        return sum;
    };

    /**
     * A DoubleArrayFunctions lambda that finds the average of all numbers in the collection.
     */
    public static final DoubleArrayFunctions avg = (double[] arr) -> {
        return sum.applyDouble(arr) / arr.length;
    };

    /**
     * Gets a number from the user which is to be searched for in the collection.
     * @return A double value that the user entered which is to be searched.
     */
    public static double userInputForSearch(){
        Scanner scanner = new Scanner(System.in);
        double input = 0.0;
        boolean invalidInput = true;

        while(invalidInput) {
            try {
                System.out.print("Enter a number to check how many times does it appear in the collection: ");
                input = scanner.nextDouble();
                invalidInput = false;
            } catch (InputMismatchException ime) {
                System.out.println("Only double values are allowed! Please try again!");
                scanner.nextLine();
            }
        }

        return input;
    }

    /**
     * Gets elements in a collection from the user which on which multiple operations can be performed.
     * @return A double array that the user entered.
     */
    public static double[] userInputForCollection() {
        Scanner scanner = new Scanner(System.in);
        int length = 0;
        boolean invalidInput = true;

        while(invalidInput) {
            try {
                System.out.print("Enter number of array elements: ");
                length = scanner.nextInt();
                invalidInput = false;
            } catch (InputMismatchException ime) {
                System.out.println("Only int values are allowed! Please try again!");
                scanner.nextLine();
            }
        }

        double[] array = new double[length];
        invalidInput = true;

        for (int i = 0; i < length; i++) {
            while(invalidInput) {
                try {
                    System.out.print("Enter value for element " + (i+1) + " : ");
                    array[i] = scanner.nextDouble();
                    invalidInput = false;
                } catch (InputMismatchException ime) {
                    System.out.println("Only double values are allowed! Please try again!");
                    scanner.nextLine();
                }
            }
            invalidInput = true;
        }

        return array;
    }

    /**
     * Defines a DoubleArrayFunctions that traverses through the collection to find
     * how many times the value appears in the user created collection.
     * @param value The value that needs to searched for in a collection.
     * @return A DoubleArrayFunctions lambda that counts how many times
     *          the value parameter appears in the collection.
     */
    public static DoubleArrayFunctions counter(double value) {
        return (array) -> {
            int count = 0;
            for (double v : array) {
                if (v == value)
                    count++;
            }
            return count;
        };
    }
}
