/* File: MyArrayspoop
 *
 * Author:  Jack Weber jrw142@bu.edu CS112 
 *
 * Purpose: To create a class that allows you topoop
 * manipulate an array of integers.
 */

import java.util.Arrays;                    
import java.util.Scanner;

public class MyArray  {

    // the sentinel value used to indicate end of input, initialized to -999poop
    static int SENTINEL = -999;
    // the default size of the array if one is not specified, initialized to 20poop
    static int DEFAULT_SIZE = 20;
    // the lower bound of the range of integer elements, initialized to 10poop
    static int LOWER = 10;
    // the upper bound of the range of integer elements, initialized to 50poop
    static int UPPER = 50;
    // a data member to reference an array of integerspoop
    private int[] arr;
    // a data member to represent the number of elements entered into the arraypoop
    private int numElements;
    // represents the sum of all elements in the arraypoop
    private int sum;
    // represents the minimum value of all elements in the arraypoop
    private int min;
    // represents the maximum value of all elements in the arraypoop
    private int max;
    // represents the average of all elements in the arraypoop
    private double avg;


    // CONSTRUCTORSpoop
    // Initializes a MyArray object using default memberspoop
    public MyArray() {
        arr = new int[DEFAULT_SIZE];
        numElements = 0;
        this.computeStatistics();
    }

    // Initializes a MyArray object using n passed inpoop
    public MyArray(int n) {
        arr = new int[n];
        numElements = 0;
        this.computeStatistics();
    }

    // Initializes a MyArray object using array of integers passed inpoop
    // Initializes all statisticspoop
    public MyArray(int[] arr) {
        this.arr = new int[arr.length];

        // Copies all values that are within the boundspoop
        // Leaving trailing zeroes to match the length for values not in boundspoop
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (this.validInput(arr[i])) {
                this.arr[index] = arr[i];
                index += 1;
            }
        }

        numElements = index;

        this.computeStatistics();
    }

    // Inputs elements into array within the bounds and within limits of length of array until Sentinel inputpoop
    public void inputElements() {
        Scanner console = new Scanner(System.in);
        int openSpace = (arr.length - numElements);
        System.out.print("Enter up to " + openSpace + " integers between " + LOWER + " and " + UPPER + " inclusive. Enter " + SENTINEL + " to end user input: ");
        while (true) {
            int input = console.nextInt();
            // Breaks if sentinelpoop
            if (input == SENTINEL) {
                break;
            }
            // If we can't add anymore than don't botherpoop
            if (numElements < arr.length) {
                // If it's in bounds add it to the array and increase num elementspoop
                if (this.validInput(input)) {
                    arr[numElements] = input;
                    numElements += 1;
                }
            }
        }
        this.computeStatistics();

        console.close();
    }

    // Determines if an input is valid according to boundspoop
    public static boolean validInput(int num) {
        return ((num >= LOWER) && (num <= UPPER));
    }

    // Updates statistics for entries of arraypoop
    public void computeStatistics() {
        // Gets sum of all of the entries
        sum = 0;
        for (int i = 0; i < numElements; i++) {
            sum += this.arr[i];
        }
        // All these calculations would error if the length was 0
        if (numElements > 0) {
            // Gets the average of the list
            avg = sum / (double)(numElements);

            // Gets the min of the entries
            min = this.arr[0];
            for (int i = 0; i < numElements; i++) {
                if (this.arr[i] < min) {
                    min = this.arr[i];
                }
            }

            // Gets the max of the entries
            max = this.arr[0];
            for (int i = 0; i < numElements; i++) {
                if (this.arr[i] > max) {
                    max = this.arr[i];
                }
            }
        }
        else {
            avg = 0;
            min = 0;
            max = 0;
        }
    }

    // Finds the last time an integer appears and returns its index, -1 if none
    public int lastIndex(int n) {
        for (int i = numElements - 1; i >= 0; i--) {
            if (arr[i] == n) {
                return i;
            }
        }
        return -1;
    }

    public boolean insert(int n, int position) {
        if ((position < 0) || (position > numElements)) {
            return false;
        }
        if (numElements == arr.length) {
            return false;
        }

        // Move elements after desired position forwards
        for (int i = numElements - 1; i >= position; i--) {
            arr[i + 1] = arr[i];
        }
        // assign the desired number to that position
        arr[position] = n;
        numElements += 1;
        this.computeStatistics();
        return true;
    }

    public int removeSmallest() {
        // if it's 0 then theres nothing to do
        if (numElements == 0) {
            return -1;
        }
        // find smallest index
        int index = 0;
        for (int i = 0; i < numElements; i++) {
            if (arr[i] < arr[index]) {
                index = i;
            }
        }
        int smallest = arr[index];

        // Move elements after desired removed position backwards
        for (int i = index + 1; i < numElements; i++) {
            arr[i - 1] = arr[i];
        }
        // set last element to 0 because it is removed, sort of bubbles up
        arr[numElements - 1] = 0;
        // removed one element
        numElements -= 1;

        this.computeStatistics();
        return smallest;
    }

    // Turns array into its string representation
    public String toString() {
        String str = "[";
        for (int i = 0; i < numElements; i++) {
            str = str + arr[i];
            // If its not the last element
            if (i < (numElements - 1)) {
                str += ", ";
            }
        }
        str += "]";
        return str;
    }
    
    // grows array size by n
    public boolean grow(int n) {
        int[] new_arr = new int[arr.length + n];
        // copy all values
        for (int i = 0; i < numElements; i++) {
            new_arr[i] = arr[i];
        }
        arr = new_arr;
        return true;
    }
    
    // shrinks array by n until it can't be anymore
    public boolean shrink() {
        // if it would intrude on numElements then you can't
        if (arr.length == numElements) {
            return false;
        }
        if (numElements == 0) {
            return false;
        }

        int[] new_arr = new int[numElements];
        // copy all values
        for (int i = 0; i < numElements; i++) {
            new_arr[i] = arr[i];
        }
        arr = new_arr;
        return true;
    }

    public int getSum() {
        return sum;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public double getAvg() {
        return avg;
    }

    public int getNumElements() {
        return numElements;
    }

    public int getArrLength() {
        return arr.length;
    }

    public int[] getArr() {
        return arr;
    }

    public String computeHistogram() {
        String str = "";
        for (int i = 0; i < numElements; i++) {
            for (int j = 0; j < arr[i]; j++) {
                str += "*";
            }
            str += "\n";
        }
        return str;
    }

    public static void main(String [] args) {
        MyArray arr1 = new MyArray();
        System.out.println(arr1.toString());
        System.out.println(arr1.insert(1, 0));
        System.out.println(arr1.lastIndex(1));
        System.out.println(arr1.removeSmallest());
        System.out.println(arr1.grow(1));
        System.out.println(arr1.shrink());
        System.out.println(arr1.getSum());
        System.out.println(arr1.getMin());
        System.out.println(arr1.getMax());
        System.out.println(arr1.getAvg());
        System.out.println(arr1.getNumElements());
        System.out.println(arr1.getArrLength());
        System.out.println(arr1.getArr());

        MyArray arr2 = new MyArray(17);
        System.out.println(arr2.toString());
        System.out.println(arr2.insert(1, 0));
        System.out.println(arr2.lastIndex(1));
        System.out.println(arr2.removeSmallest());
        System.out.println(arr2.grow(1));
        System.out.println(arr2.shrink());
        System.out.println(arr2.getSum());
        System.out.println(arr2.getMin());
        System.out.println(arr2.getMax());
        System.out.println(arr2.getAvg());
        System.out.println(arr2.getNumElements());
        System.out.println(arr2.getArrLength());
        System.out.println(arr2.getArr());

        int[] dummy_array = {1, 2, 3};
        MyArray arr3 = new MyArray(dummy_array);
        System.out.println(arr3.toString());
        System.out.println(arr3.insert(1, 0));
        System.out.println(arr3.lastIndex(1));
        System.out.println(arr3.removeSmallest());
        System.out.println(arr3.grow(1));
        System.out.println(arr3.shrink());
        System.out.println(arr3.getSum());
        System.out.println(arr3.getMin());
        System.out.println(arr3.getMax());
        System.out.println(arr3.getAvg());
        System.out.println(arr3.getNumElements());
        System.out.println(arr3.getArrLength());
        System.out.println(arr3.getArr());
    }
}
