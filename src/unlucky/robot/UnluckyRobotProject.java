/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unlucky.robot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Jamil
 */
public class UnluckyRobotProject {
    /**
     * 
     * @param args to print out all method
     */
    public static void main(String[] args) {
        
        Scanner console = new Scanner(System.in);
        int x = 0;
        int y = 0;
        int totalScore = 300;
        int itrCount = 0;
        int reward;
        int exceedBoundary = -2000;
        int move1 = -10;        // -10 for u
        int move2 = -50;        // -50 for the rest
        
        do {
            displayInfo(x, y, itrCount, totalScore);
            char direction = inputDirection();
            if (doesExced(x, y, direction)){
                System.out.println("Exceed boundary, -2000 damage applied");
                totalScore += exceedBoundary;
            }
            else{
                switch(direction){
                    case 'r':
                         x++;
                        break;
                    case 'l':
                         x--;
                        break;
                    case 'u':
                        y++;
                        break;
                    default:
                        y--;
                        break;
                }
            }
            if (direction == 'u')
                totalScore += move1;
            else 
                totalScore += move2;
            
            reward = punishOrMercy(direction, totalScore += reward());
            System.out.println("");
            itrCount++;
        }
        while (!isGameOver(x, y, totalScore, itrCount));
        evaluation(totalScore);
    }
    /**
     * To print a message about the beginning of iteration
     * @param x x coordinate of robot
     * @param y y coordinate of robot
     * @param itrCount the number of iteration 
     * @param totalScore total score
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore){
        System.out.printf("%s (X=%d, Y=%d) %s: %d %s: %d", "For point", x,
                 y, "at iteration", itrCount, "the total score is", totalScore);
        System.out.println("");
    }
    /**
     * To check if the robot exceed the grid limit after taking a step
     * @param x the inputed x
     * @param y the inputed y
     * @param direction the direction which is chosen by the user "u, d, r, l"
     * @return true if the robot exceed the grid limit after taking a step otherwise false
     */
    public static boolean doesExced(int x, int y, char direction){
        return (y == 4 && direction == 'u' || x == 0 && direction == 'l' 
                || y == 0 && direction == 'd' || x == 4 && direction == 'r');
    }
    /**
     * when robot makes a move and ends in a cell this called this function
     * it return a number as a reward 
     * @return -100, -200, -300, 300, 400 0r 600 for rewards
     */
    public static int reward(){
        Random random = new Random();
        int dice = random.nextInt(6) + 1;
        
        switch (dice){
            case (1):
                System.out.println("Dice: 1, reward: -100");
                return -100;
            case (2):
                System.out.println("Dice: 2, reward: -200");
                return -200;
            case (3):
                System.out.println("Dice: 3, reward: -300");
                return -300;
            case (4):
                System.out.println("Dice: 4, reward: 300");
                return 300;
            case (5):
                System.out.println("Dice: 5, reward: 400");
                return 400;
            default:
                System.out.println("Dice: 6, reward: 600");
                return 600;
        }
    }
    /**
     * reward or not, flipping a coin that returns tail or head  
     * @param direction the inputed direction
     * @param reward the initial reward
     * @return initial reward or zero
     */
    public static int punishOrMercy(char direction, int reward){
        Random rand = new Random();
        int coinFlip = rand.nextInt(2);
            
        if (direction == 'u' && coinFlip == 0 && reward < 0){
            System.out.println("Coin: Tail | Mercy, the negative reward is removed.");
            return 0;
        }
        else if (direction == 'u' && coinFlip == 1 && reward < 0){
            System.out.println("Coin: Head | No mercy, the negative rewarded is applied.");
            return reward;
        }
        else
            return reward;
    }
    /**
     * To convert the case into Title case
     * @param str the inputed string
     * @return String into title case
     */
    public static String toTitleCase(String str){
        str = str.toLowerCase();
        int idx = str.indexOf(" ");
        String firstName = Character.toTitleCase(str.charAt(0)) + str.substring(1, idx);
        String lastName = Character.toTitleCase(str.charAt(idx + 1)) + str.substring(idx + 2);
        return firstName + " " + lastName;
    }
    /**
     * To print the message based on the value of the total score
     * @param totalScore the total score 
     */
    public static void evaluation(int totalScore){
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter your name (only two words): ");
        String name = console.nextLine();
        String titleCaseName = toTitleCase(name);
        
        if (totalScore >= 2000)
            System.out.printf("%s, %s, %s %d", "Victory", titleCaseName, "your score is",
                    totalScore);
        else 
            System.out.printf("%s, %s, %s %d", "Mission failed", titleCaseName, 
                    "your score is", totalScore);
    }
    /**
     * To put a direction
     * @return  the direction r, l, u, d
     */
    public static char inputDirection(){
        Scanner console = new Scanner(System.in);
        char direction;
        do{
            System.out.print("Please input a valid direction: ");
            direction = console.next().charAt(0);
        } 
        while (direction != 'u' && direction != 'd' && direction != 'r'
                && direction != 'l');
        
        return direction;
    }
    /**
     * To check if the game is over, according to win or lose
     * @param x x coordinates of the robot
     * @param y y coordinates of the robot
     * @param totalScore the total score
     * @param itrCount total number of iteration
     * @return if the you win or lose
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        char direction = 0;
        return (totalScore <= -1000 || totalScore >= 2000 || itrCount > 20 
                || x == 4 && y == 4 || x == 4 && y == 0);
    }
}