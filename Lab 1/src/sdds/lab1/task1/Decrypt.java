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
 * <b>Decrypt</b> class is full-blown decryption class that takes care of decrypting the user input.
 * It contains various functionalities that support the decryption scheme. <b>TestDecrypt</b> class
 * instantiates a <b>Decrypt</b> instance and tests the decryption logic.
 *
 * @author Soham Thaker
 * @version 1.0
 * @since 30/01/2022
 * @see sdds.lab1.task1.TestEncryptDecrypt
 */
public class Decrypt {

    /**
     * <b>inputNum</b> holds the String value that the user enters in main and is then
     * passed to the instance of this class in a constructor call
     */
    private final String inputNum;

    /**
     * Constructor of Decrypt class accepts a String coming from main
     * and then sets the private data member inputNum to the value of the param.
     * This data member is used later on in decrypting the data.
     * @param userInput String param that contains user's input.
     */
    public Decrypt(String userInput){
        this.inputNum = userInput;
    }

    /**
     * <b>split</b> function splits the user inputted integer into ArrayList
     * for better decryption handling. It loops over the user inputted number
     * and adds the value of each character into the ArrayList object.
     * It is private since client doesn't need to know about the decryption logic.
     * @param array A reference to ArrayList object which will be used later on in the program.
     * */
    private void split(ArrayList<String> array) {
        for (int i = 0; i < inputNum.length(); i++) {
            array.add(String.valueOf(inputNum.charAt(i)));
        }
    }

    /**
     * <b>swap</b> function performs swapping of indexes by swapping 0th index with 2nd and
     * 1st index with 3rd so that the decryption logic is unbrechable. It is private since
     * client doesn't need to know about the decryption logic.
     * @param array A reference to the ArrayList object passed to it from decrypt function.
     * */
    private void swap(ArrayList<String> array) {
        for (int i = 0; i < array.size() / 2; i++) {
            String temp = array.get(i);
            array.set(i, array.get(i + 2));
            array.set(i + 2, temp);
        }
    }

    /**
     * <b>replace</b> function performs the mathematical calculation for decrypting data.
     * It loops over the ArrayList object and checks whether the number is less
     * than 7, if it is then deduces 10 from the result of 7 - the number that is
     * stored at each index which is then stored back into its respective index.
     * It is private since client doesn't need to know about the decryption logic
     * @param array A reference to the ArrayList object passed to it from decrypt function.
     * */
    private void replace(ArrayList<String> array) {
        for (int i = 0; i < array.size(); i++) {
            int temp = Integer.parseInt(array.get(i));

            //easier way of decrypting; commented out since I found this
            //technique after recording the video and writing the document

            //array.set(i, Integer.toString((temp + 3) % 10));

            if(temp < 7){
                array.set(i, Integer.toString(10 - (7 - temp)));
            }
            else{
                array.set(i, Integer.toString(temp - 7));
           }
        }
    }

    /**
     * <b>concat</b> function concatenates the decrypted value stored in
     * ArrayList object to a String so that it can later be displayed to the user.
     * It is private since client doesn't need to know about the decryption logic.
     * @param array A reference to the ArrayList object passed to it from
     *              decrypt function.
     * @return A String object that contains concatenated value of the decrypted number.
     * */
    private String concat(ArrayList<String> array) {
        StringBuilder decrypt = new StringBuilder();
        for (String i : array) {
            decrypt.append(i);
        }
        return decrypt.toString();
    }


    /**
     * <b>decrypt</b> function decrypts all the user input.
     * It calls 4 methods, part of the Decrypt class that helps us decrypt
     * the user inputted data. This method is called by main which performs
     * decryption logic on user input. It is public since client directly interacts
     * with this function
     * @return decrypted String of numbers.
     */
    public String decrypt(){
        ArrayList<String> array = new ArrayList<>();
        this.split(array);
        this.swap(array);
        this.replace(array);
        return this.concat(array);
    }
}

