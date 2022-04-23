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

/**
 * A functional interface that declares a lambda which can be used to perform some operation on it.
 * @author Soham Thaker
 * @version 1.0
 * @see FunctionalInterface
 */
@FunctionalInterface
public interface DoubleArrayFunctions {
    /**
     * A lambda function that performs some operation on double array.
     * @param array An array of doubles.
     * @return Double value.
     */
    double applyDouble( double[] array );
}