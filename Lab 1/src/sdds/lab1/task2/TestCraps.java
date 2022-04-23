/**********************************************
 Workshop #1
 Course:BTP400NBB - Semester 4
 Last Name: Thaker
 First Name: Soham
 ID:011-748-159
 Section:NBB
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date:30/01/2022
 **********************************************/

package sdds.lab1.task2;

/**
 * <b>TestCraps</b> class contains a main function that creates a <b>Craps</b>
 * instance and calls wager function on that instance that performs the action of
 * playing the Craps casino game.
 *
 * @author Soham Thaker
 * @version 1.0
 * @since 30/01/2022
 * @see sdds.lab1.task2.Craps
 */
public class TestCraps {

    /**
     * <b>main</b> function initializes a <b>Craps</b> object so that the user can play the game
     * after initialization. It calls wager method on that object which takes care of
     * playing the game for the user.
     * @param args This is the only param into the main method passed from command line as command line args.
     */
    public static void main(String[] args) {
        Craps c = new Craps();
        c.wager();
    }
}

