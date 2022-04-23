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
import java.util.Scanner;

/**
 * <b>Craps</b> class contains multiple functions that let the user play the Craps game interactively.
 *
 * @author Soham Thaker
 * @version 1.0
 * @since 30/01/2022
 * @see sdds.lab1.task2.TestCraps
 */
public class Craps {

    /**
     * bankBalance in a private int data member that is initialized to 1000 for every instance since that's
     * the balance that each player holds when they first start playing the game.
     * */
    private int bankBalance = 1000;

    /**
     * <b>chatter</b> function creates an array of String messages that are displayed
     * everytime the user either wins or loses the game. It randomly generates
     * an index between 0 and 5, and returns the String stored at that index.
     * It is private since client doesn't need to know about the chatter function.
     * @return String stored at a randomly generated index
     * */
    private String chatter() {
        String[] chatter = {
                "Oh, you’re going for broke, huh?",
                "Aw c’mon, take a chance!",
                "You’re up big. Now’s the time to cash in your chips!",
                "Your wife's gonna be happy or sad when she comes to know about this?",
                "Oh look at you! It's someone's lucky day today, huh!",
                "At least don't curse the poor machine for your bad luck!"
        };

        return chatter[(int) (Math.random() * ((5)))];
    }

    /**
     * <b>wager</b> function asks for a user input to check the bank balance of the user.
     * If the balance is greater than current balance then it keeps on asking the user
     * to enter wager that is below the current balance of their bank account, otherwise
     * the craps game starts. The game continues to be played(by calling rollAndPlay() function)
     * in a loop until the user's balance doesn't drop either to 0, or the wager amount it greater
     * than current bank balance or until doesn't enter "q" to quit the game. It is public since client
     * directly interacts with this function. It is public since client directly interacts with this function.
     * It also checks if the user entered number is a valid type, otherwise throws am exception and asks user
     * enter the input again.
     *
     * @exception java.util.InputMismatchException if the extracted token is not an integer type.
     * */
    public void wager() {
        String userChoice = "";
        Scanner scan = new Scanner(System.in);
        int wager = 0;
        boolean check = true;

        do {
            try {
                System.out.print("Enter wager: $");
                wager = scan.nextInt();

                userChoice = scan.nextLine();           //need to add below line because nextInt doesn't consume the user entered \n
                check = false;
            }
            catch(Exception ex) {
                System.out.println("An error occured! You entered a value whose type is not accepted: " + ex);
                userChoice = scan.nextLine();           //need to add below line because nextInt doesn't consume the user entered \n
            }
        } while(check);

        while(wager > this.bankBalance) {
            System.out.print("You entered wager: " + wager +
                    " that is greater than your current bank balance: $"
                    + bankBalance + "\nPlease enter wager again: $");
            wager = scan.nextInt();
            //need to add below line because nextInt doesn't consume the user entered \n
            userChoice = scan.nextLine();
        }

        do {
            if(this.rollAndPlay()){
                this.bankBalance += wager;
                System.out.println(this.chatter() + "\nYour new bank balance is: $"
                                    + this.bankBalance + " & your wager is: $" + wager);
            }
            else{
                this.bankBalance -= wager;
                System.out.println(this.chatter() + "\nYour new bank balance is: $"
                                    + this.bankBalance + " & your wager is: $" + wager);
                if(this.bankBalance <= 0 || this.bankBalance < wager) {
                    System.out.println("Sorry, You're busted!");
                }
            }

            if(this.bankBalance > 0 && this.bankBalance >= wager) {
                System.out.print("Do you wanna continue playing? Enter " +
                        "\"q\" to quit or any other letter to continue: ");
                userChoice = scan.nextLine();
            }
        } while(!userChoice.equals("q") && this.bankBalance > 0 && this.bankBalance >= wager);
    }

    /**
     * <b>rollAndPlay</b> function rolls a die by randomly picking a number between 1 and 6.
     * Then the sum of both numbers is checked to see if the user won or lost the game.
     * If the sum is 2,3 or 12, the player loses, if it is 7 or 11, the player wins
     * and if it is 4, 5, 6, 8, 9, or 10 then a point is established at which point
     * is stored in a variable. Then both dices are rolled and summed in a
     * loop until either the sum is equal to point(player wins) or sum is equal to
     * 7(player loses). It is private since client doesn't need to know about the rollAndPlay function.
     * @return boolean indicating that user either won or lost the game
     * */
    private boolean rollAndPlay() {

        int dice1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
        int dice2 = 1 + (int)(Math.random() * ((6 - 1) + 1));

        int sum = dice1 + dice2;

        System.out.println("You rolled " + dice1 + " + " + dice2 + " = " + sum);

        if(sum == 2 || sum == 3 || sum == 12) {
            System.out.println("Craps, Better Luck Next Time, You lose");
            return false;
        }
        else if(sum == 7 || sum == 11) {
            System.out.println("Congratulations, You win");
            return true;
        }
        else{
            System.out.println("Point is (established) set to " + sum);
            int point = sum;

            do {
                dice1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
                dice2 = 1 + (int)(Math.random() * ((6 - 1) + 1));

                sum = dice1 + dice2;

                System.out.println("You rolled " + dice1 + " + " + dice2 + " = " + sum);
            }while(sum != 7 && point != sum);

            if(sum == 7) {
                System.out.println("Craps, Better Luck Next Time, You lose");
                return false;
            }
            else{
                System.out.println("Congratulations, You win");
                return true;
            }
        }
    }
}

