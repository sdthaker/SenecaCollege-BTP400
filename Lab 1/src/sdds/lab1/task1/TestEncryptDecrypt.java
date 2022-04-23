package sdds.lab1.task1;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <b>TestEncryptDecrypt</b> class contains a main function that runs the logic of
 * Encrypting and Decrypting a user entered number. It accepts a user input and passes
 * that to the <b>Encrypt</b> instance which then encrypts the user input using an
 * encryption scheme. It then accepts a user input and passes that to the <b>Decrypt</b>
 * instance which then decrypts the user input using a decryption scheme.
 *
 * @author Soham Thaker
 * @version 1.0
 * @since 30/01/2022
 * @see sdds.lab1.task1.Decrypt
 * @see sdds.lab1.task1.Encrypt
 */
public class TestEncryptDecrypt {

    /**
     * Purpose of <b>main</b> function is to accept a user input and validate if user
     * entered exactly 4 digits. Once it confirms that data, it then forwards the value
     * to Encrypt class that takes care of encryption. Once the encryption is done this function
     * displays the encrypted data back to the user. The above procedure is followed for Decrypt class as well.
     * So this function does 2 tasks in one run, encrypting the user input, showing the encrypted number
     * and decrypting the user input, showing original number back. Besides, it also generates exception
     * if the user enters the numbers in any other datatype other than int.
     *
     * @param args This is the only param into the main method passed from command line as command line args.
     * @exception NumberFormatException if the string does not contain parsable integer.
     * @exception InputMismatchException if the extracted token is not an integer type.
     */
    public static void main(String[] args) {

        int num;
        String s;
        Scanner scan = new Scanner(System.in);
        boolean check = true;

        //Test Encrypt
        do {
            try {

                System.out.print("Enter a 4 digit number to encrypt: ");
                num = scan.nextInt();
                s = Integer.toString(num);

                //Input is taken in integer since if user enters 0123 which
                //is not a whole number it will drop the 0 at beginning
                //leading with integer to be of 3 in length, must be 4
                //acc to the assignment specification
                while (s.length() != 4) {
                    System.out.print("You entered number with length, "
                            + s.length() + ". The number should be exactly 4 digits in length: ");
                    num = scan.nextInt();
                    s = Integer.toString(num);
                }

                check = false;
                Encrypt e = new Encrypt(s);
                System.out.println(num + " is now encrypted to: " + e.encrypt());
                s = scan.nextLine();
            } catch (Exception ex) {                //throw exception if user entered an incorrect type
                System.out.println("An error occured! You entered a value whose type is not accepted: " + ex);
                s = scan.nextLine();
            }
        } while (check);


        check = true;
        //Test Decrypt
        do {
            try {
                System.out.print("\nEnter a 4 digit number to decrypt: ");
                s = scan.nextLine();
                num = Integer.parseInt(s);

                //Get input in String because if the encrypted number begins with 0
                //then Integer data type will drop that 0 since it is not considered as whole number
                while (s.length() != 4) {
                    System.out.print("You entered number with length, " + s.length() +
                            ". Please input the number again or enter \"q\" to quit to quit the program: ");
                    s = scan.nextLine();
                    num = Integer.parseInt(s);
                }

                check = false;
                Decrypt d = new Decrypt(s);
                System.out.println(s + " is now decrypted to: " + d.decrypt());

            } catch (Exception ex) {                        //throw exception if user entered an incorrect type
                System.out.print("An error occured! You entered a value whose type is not accepted: "
                        + ex);
            }
        } while (check);
    }
}
