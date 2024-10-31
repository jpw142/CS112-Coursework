/*
 * Circular.java
 * 
 * CS 112 Course Staff (cs112-staff@cs.bu.edu)
 * 
 * completed by: Jack Weber jrw142@bu.edu
 * A program that calcualtes and displays various dimensional values of a circle
 */

import java.util.*;

public class Circular {   
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Diameter of the piece of land: ");
        int d = scan.nextInt();

        double r = d / 2.0;

        double area = Math.PI * r * r;

        // rounds area
        int a = (int)Math.round(area);

        // square yards and leftover sqaure feet
        int yds = a / 9;
        int yds_f = a - (yds * 9);

        System.out.println("The area of the circle is approximately:");
        System.out.println(a + " square feet");
        System.out.println(yds + " square yards plus " + yds_f + " square feet");

        scan.close();
    }
}
