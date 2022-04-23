/**********************************************
 Workshop #1
 Course:BTP400NBB - Semester 4
 Last Name: Thaker
 First Name: Soham
 ID:011-748-159
 Section:NBB
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: ST
 Date:30/01/2022
 **********************************************/

package sdds.lab1.task1;
import java.util.ArrayList;

/**
 * <b>Encrypt</b> class is full-blown encryption class that takes care of encrypting the user input.
 * It contains various functionalities that support the encryption scheme. <b>TestEncrypt</b> class
 * instantiates a <b>Encrypt</b> instance and tests the encryption logic.
 *
 * @author Soham Thaker
 * @version 1.0
 * @since 30/01/2022
 * @see sdds.lab1.task1.TestEncryptDecrypt
 */
public class Encrypt {

    /**
     * <b>inputNum</b> holds the int value that the user enters in main and is then
     * passed to the instance of this class in a constructor call
     */
    private final int inputNum;

    /**
     * Constructor of Encrypt class accepts a String
     * coming from main and then sets the private data member inputNum
     * to the value of the param. This data member is used later on in
     * encrypting the data.
     * @param userInput String param that contains user's input.
     */
    public Encrypt(String userInput) {
        this.inputNum = Integer.parseInt(userInput);
    }

    /**
     * <b>split</b> function splits the user inputted integer into ArrayList for better encryption handling.
     * It loops over the input until there is no remainder left after dividing the number with 10 everytime.
     * It splits and adds the number at 0th location every time in the ArrayList so that we don't
     * have to reverse it later. It is private since client doesn't need to know about the encryption logic.
     * @return A reference to ArrayList object which will be used later on in the program.
     * */
    private ArrayList<Integer> split() {

        ArrayList<Integer> array = new ArrayList<>();
        int num = this.inputNum;
        do {
            array.add(0, num % 10);
            num = num / 10;
        } while (num > 0);

        return array;
    }

    /**
     * <b>replace</b> function performs the mathematical calculation for encrypting data. It
     * adds each number in ArrayList object with 7 and then divides it by 10 to get
     * the remainder which is then stored back into its respective index. It is private since
     * client doesn't need to know about the encryption logic.
     * @param array A reference to the ArrayList object passed to it from encrypt function.
     * */
    private void replace(ArrayList<Integer> array) {
        for (int i = 0; i < array.size(); i++) {
            array.set(i, ((array.get(i) + 7) % 10));
        }
    }

    /**
     * <b>swap</b> function performs swapping of indexes by swapping 0th index with 2nd and
     * 1st index with 3rd so that the encryption logic is unbreachable. It is private since
     * client doesn't need to know about the encryption logic.
     * @param array A reference to the ArrayList object passed to it from encrypt function.
     * */
    private void swap(ArrayList<Integer> array) {
        for (int i = 0; i < array.size() / 2; i++) {
            int temp = array.get(i);
            array.set(i, array.get(i + 2));
            array.set(i + 2, temp);
        }
    }

    /**
     * <b>concat</b> function concatenates the encrypted value stored in
     * ArrayList object to a String so that it can later be displayed to the user.
     * It is private since client doesn't need to know about the encryption logic.
     * @param array A reference to the ArrayList object passed to it from
     *              encrypt function.
     * @return A String object that contains concatenated value of the encrypted number.
     * */
    private String concat(ArrayList<Integer> array) {
        //concatenate to string and return the result since returning an
        //Integer will drop 0 if a 0 appears at the start for an encrypted number
        StringBuilder encrypt = new StringBuilder();
        for (Integer i : array) {
            encrypt.append(i);
        }
        return encrypt.toString();
    }

    /**
     * <b>encrypt</b> function encrypts all the user input.
     * It calls 4 methods, part of the Encryption class that helps us encrypt
     * the user inputted data. This method is called by main which performs
     * encryption logic on user input. It is public since client directly interacts
     * with this function.
     * @return encrypted String of numbers.
     */
    public String encrypt() {
        ArrayList<Integer> array;
        array = this.split();
        this.replace(array);
        this.swap(array);
        return this.concat(array);
    }
}
