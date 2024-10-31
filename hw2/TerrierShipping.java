/*
 * TerrierShipping.java
 * CS 112, Boston University
 *
 * Completed by: Jack Weber jrw142@bu.edu
 * 
 * Calculates the total shipping charge for a collection of items.
 */

import java.util.*;

public class TerrierShipping {     
    /*
     * getShippingType - gets the type of shipping as an integer
     */
    public static int getShippingType(Scanner console){
        System.out.println("Available shipping types:");
        System.out.println("  1) one-day");
        System.out.println("  2) two-day");
        System.out.println("  3) standard");
        System.out.println();
        
        System.out.print("What type of shipping? (enter the number) ");
        int type = console.nextInt();
        return type;
    }

    /*
     * getItemType - gets the type of item as a single-character string
     */
    public static String getItemType(Scanner console){
        System.out.println();
        System.out.println("Item type:");
        System.out.println("  B) book");
        System.out.println("  C) clothing");
        System.out.println("  E) electronics");
        System.out.println("  T) toy");
        System.out.println();
        
        System.out.print("What type of item? (enter the letter or Q to quit) ");
        String itemType = console.next();
        return itemType;
    }
    
    /* 
     * PUT YOUR ADDITIONAL HELPER METHODS HERE.
     * Remember that you must have at least three additional methods that:
     *   - take one or more parameters
     *   - return a value 
     */

    // Does the calculations based on item category for one day shipping
    // Takes in a String c for item category and int p for pounds
    public static int oneDay(String c, int p) {
        // Toys
        if (c.equals("T")) {
            return 499 + (p * 199);
        }
        // Electronics
        if (c.equals("E")) {
            return 599 + (p * 199);
        }
        // Books and clothes
        if (c.equals("B") || c.equals("C")) {
            if (p >= 2) {
                return 399 + (p * 60);
            }
            return 499;
        }
        return -1;
    }

    // Does the calculations based on item category for two day shipping
    // Takes in a String c for item category and int p for pounds
    public static int twoDay(String c, int p) {
        // Toys
        if (c.equals("T")) {
            return 299 + (p * 99);
        }
        // Electronics
        if (c.equals("E")) {
            return 399 + (p * 89);
        }
        // Books and clothes
        if (c.equals("B") || c.equals("C")) {
            if (p >= 2) {
                return 199 + (p * 75);
            }
            return 299;
        }
        return -1;
    }
    
    // Does the calculations based on item category for standard day shipping
    // Takes in a String c for item category and int p for pounds
    public static int standardDay(String c, int p) {
        // Toys
        if (c.equals("T") || c.equals("E")) {
            return 199 + (p * 80);
        }
        // Books and clothes
        if (c.equals("B") || c.equals("C")) {
            if (p >= 2) {
                return 99 + (p * 70);
            }
            return 199;
        }
        return -1;
    }

    public static void main(String[] args){
        Scanner console = new Scanner(System.in);    // for user input
        
        System.out.println("Welcome to Terrier Shipping!");
        System.out.println();
        int shipType = getShippingType(console);
        
        int totalCents = 0;
        boolean hasMoreItems = true;
        
        /*
         * Process one item at a time until the user enters Q. 
         * We use a do-while loop, because we always need 
         * at least one repetition of the loop.
         */
        do {
            String itemType = getItemType(console);       
            if (itemType.equals("Q")) {
                hasMoreItems = false;
            } else {            
                /*
                 * TO DO: update the right-hand side of the assignment 
                 * statement below to get an integer from the user. 
                 * You MUST use the Scanner object created above 
                 * at the start of main. You may NOT construct an 
                 * additional Scanner object.
                 */
                System.out.print("Weight of item (rounded to nearest pound)? ");
                int weight = console.nextInt();
        
                int itemCharge = 0;
            
                /*
                 * TO DO: Add code here that uses conditional execution to 
                 * call one of your static methods to determine the charge
                 * for the current item and assign it to itemCharge.
                 */
                // One day
                if (shipType == 1) {
                    itemCharge += oneDay(itemType, weight);
                }     
                // Two day
                if (shipType == 2) {
                    itemCharge += twoDay(itemType, weight);
                }           
                // Standard day
                if (shipType == 3) {
                    itemCharge += standardDay(itemType, weight);
                }           
                
                totalCents += itemCharge;
            }
        } while (hasMoreItems == true);
            
        System.out.println();
        
        /*
         * TO DO: add the appropriate expression to the right-hand side 
         * of this assignment statement to convert totalCents to dollars.
         */
        double totalDollars = totalCents / 100.0;      
        
        // We use printf to ensure that the final result always has
        // two digits after the decimal. 
        System.out.printf("The total charge is: $%.2f\n", totalDollars);   

        console.close();
    }
}
