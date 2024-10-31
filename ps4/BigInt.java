/* 
 * BigInt.java
 *
 * A class for objects that represent non-negative integers of 
 * up to 20 digits.
 */

import java.util.Arrays;

public class BigInt  {
    // the maximum number of digits in a BigInt -- and thus the length
    // of the digits array
    private static final int MAX_SIZE = 20;
    
    // the array of digits for this BigInt object
    private int[] digits;
    
    // the number of significant digits in this BigInt object
    private int sigDigits;
    private boolean overflow;

    /*
     * Default, no-argument constructor -- creates a BigInt that 
     * represents the number 0.
     */
    public BigInt() {
        digits = new int[MAX_SIZE];
        sigDigits = 1;  // 0 has one sig. digit--the rightmost 0!
	    overflow = false;
    }
    
    // Constructor from existing array
    public BigInt(int[] arr) {
        // Checks if it's null
        if (arr == null) {
            throw new IllegalArgumentException();
        }
        // Checks if it's greater than max size
        if (arr.length > MAX_SIZE) {
            throw new IllegalArgumentException();
        }
        // Check if it has non valid inputs
        for (int i = 0; i < arr.length; i++) {
            if (!BigInt.isValid(arr[i])) {
                throw new IllegalArgumentException();
            }
        }   

        // Copy everything, and I mean everything, backwards
        int[] newArr = new int[MAX_SIZE];
        int index = MAX_SIZE - 1;
        for (int i = arr.length - 1; i >= 0; i--) {
            newArr[index] = arr[i];
            index -= 1;
        }
        digits = newArr;
        sigDigits = this.findSig();
    }

    public BigInt(int n) {
        // If it's negative we don't care
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        int [] newArr = new int[MAX_SIZE];
        // this is so you can do it backwards
        int index = MAX_SIZE - 1;
        // Progressively cut it off
        for (int i = 0; i < MAX_SIZE; i++) {
            int modTen = n % 10;

            // append to array
            newArr[index] = modTen;
            index -= 1;

            // update n
            n -= modTen;
            n /= 10;
        }

        // Checks if it's greater than max size
        if (newArr.length > MAX_SIZE) {
            throw new IllegalArgumentException();
        }

        digits = newArr;
        sigDigits = this.findSig();
    }

    // Gives num Significant digits
    private int findSig() {
        // default it to 1 cause there is always 1
        for (int i = 0; i < MAX_SIZE; i++) {
            if (digits[i] != 0) {
                return MAX_SIZE - i;
            }
        }
        return 1;
    }

    // Clones the Big Int
    public BigInt clone() {
        return new BigInt(digits);
    }

    // Determines if an integer is between 0 and 9 inclusive
    private static boolean isValid(int n) {
        return (n >= 0 && n < 10);
    }

    // Left shifts n times
    public void leftShift() {
        for (int i = 0; i < (MAX_SIZE - 1); i++) {
            digits[i] = digits[i + 1];
        }
        digits[MAX_SIZE - 1] = 0;
        sigDigits = this.findSig();
    }


    // Returns digit in specified place
    public int getDigit(int n) {
        return digits[n];
    }

    public void setOverFlow(boolean n) {
        this.overflow = n;
    }

    // Getter for num sig digits
    public int getSigDigits() {
        return sigDigits;
    }

    // unwisely returns a reference to digits
    public int[] getDigit() {
        return digits;
    }

    public boolean isOverFlow() {
        return overflow;
    }

    // toString function making usre to go backwards!
    public String toString() {
        String newStr = "";
        for (int i = MAX_SIZE - sigDigits; i < MAX_SIZE; i++) {
            newStr += digits[i];
        }
        return newStr;
    }

    // Compares two bigInts and returns 1 if caller is bigger, -1 is caller is smaller, and 0 if equal
    public int compareTo(BigInt other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < MAX_SIZE; i++) {
            if (digits[i] > other.getDigit(i)) {
                return 1;
            }
            if (digits[i] < other.getDigit(i)) {
                return -1;
            }
        }
        return 0;
    }

    // Adds two big Ints and sets overflow to true if it overflows
    public BigInt add(BigInt other) {
        if (other == null) {
           throw new IllegalArgumentException(); 
        }
        
        // overflow value to go into next
        int overValue = 0;
        int[] newArr = new int[MAX_SIZE];
        boolean shouldIOverFlow = false;
        // Backwards looop!!!
        for (int i = MAX_SIZE - 1; i >= 0; i--) {
            newArr[i] = other.getDigit(i) + digits[i] + overValue;
            // Anything thats above 10 goes to overValue and is divided by 10 because it's going up
            overValue = (newArr[i] - (newArr[i] % 10)) / 10;
            newArr[i] %= 10;

            if (overValue != 0 && i == 0) {
                shouldIOverFlow = true;
            }
        }
        BigInt answer = new BigInt(newArr);
        if (other.isOverFlow() == true || overflow == true || shouldIOverFlow) {
            answer.setOverFlow(true);
        }

        return answer;
    }

    // Multiplies two big ints
    public BigInt mul(BigInt other) {
        if (other == null) {
           throw new IllegalArgumentException(); 
        }

        // useful constants
        BigInt zero = new BigInt(0);
        BigInt one = new BigInt(1);

        // Checks if self or other is 0/1 and does according actions
        int compareSelf = this.compareTo(one);
        int compareOther = other.compareTo(one);
        // If it's less than 1 it must be 0 and therefor multiply is 0 no matter what
        if (compareSelf == -1 || compareOther == -1) {
            return zero;
        }
        // If it is 1 then just return the other number
        if (compareSelf == 0) {
            return other.clone();
        }
        if (compareOther == 0) {
            return this.clone();
        }

        // If you can't even shift enough then don't even bother
        // Just return a random overflowed number because behavior is undefined
        // I proved in my own time a number with n digits * m digits has to be at least n + m - 1 digits
        if (((other.getSigDigits() + this.getSigDigits()) - 1) > MAX_SIZE) {
            BigInt answer = zero.clone();
            answer.setOverFlow(true);
            return answer;

        }
        // gotta clone it cause we are gonna do mean things to it and dont wanna hurt the original feelings
        BigInt mult = other.clone();
        BigInt sum = zero.clone();
        // Shifting loop
        for (int i = 0; i < this.getSigDigits(); i++) {
            // Adding multiple times loop
            // Will go through backwards and multiply number of times from desired number
            // Just do gradeschool multiplication algorithm and it's literally this
            for (int j = 0; j < digits[MAX_SIZE - i - 1]; j++) {
                sum = sum.add(mult); 
            }
            mult.leftShift();
        }
    
        return sum;
    }

    // returns true if it's greater than or equal to other
    public boolean greater_than_or_equal(BigInt other) {
        int compare = this.compareTo(other);
        if (compare == 0 || compare == 1) {
            return true;
        }
        return false;
    }

    // returns true if it's greater than or equal to other
    public boolean smaller_than_or_equal(BigInt other) {
        int compare = this.compareTo(other);
        if (compare == 0 || compare == -1) {
            return true;
        }
        return false;
    }

    public static void main(String [] args) {
        System.out.println("Unit tests for the BigInt class.");
        System.out.println();

        /* 
         * You should uncomment and run each test--one at a time--
         * after you build the corresponding methods of the class.
         */

        System.out.println("Test 1: result should be 7");
        int[] a1 = { 1,2,3,4,5,6,7 };
        BigInt b1 = new BigInt(a1);
        System.out.println(b1.getSigDigits());
        System.out.println();

        System.out.println("Test 2: result should be 1234567");
        b1 = new BigInt(a1);
        System.out.println(b1);
        System.out.println();
        
        System.out.println("Test 3: result should be 0");
        int[] a2 = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
        BigInt b2 = new BigInt(a2);
        System.out.println(b2);
        System.out.println();
        
        System.out.println("Test 4: should throw an IllegalArgumentException");
        try {
            int[] a3 = { 0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
            BigInt b3 = new BigInt(a3);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 5: result should be 1234567");
        b1 = new BigInt(1234567);
        System.out.println(b1);
        System.out.println();

        System.out.println("Test 6: result should be 0");
        b2 = new BigInt(0);
        System.out.println(b2);
        System.out.println();

        System.out.println("Test 7: should throw an IllegalArgumentException");
        try {
            BigInt b3 = new BigInt(-4);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 8: result should be 0");
        b1 = new BigInt(12375);
        b2 = new BigInt(12375);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 9: result should be -1");
        b2 = new BigInt(12378);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 10: result should be 1");
        System.out.println(b2.compareTo(b1));
        System.out.println();

        System.out.println("Test 11: result should be 0");
        b1 = new BigInt(0);
        b2 = new BigInt(0);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 12: result should be\n123456789123456789");
        int[] a4 = { 3,6,1,8,2,7,3,6,0,3,6,1,8,2,7,3,6 };
        int[] a5 = { 8,7,2,7,4,0,5,3,0,8,7,2,7,4,0,5,3 };
        BigInt b4 = new BigInt(a4);
        BigInt b5 = new BigInt(a5);
        BigInt sum = b4.add(b5);
        System.out.println(sum);
        System.out.println();

        System.out.println("Test 13: result should be\n123456789123456789");
        System.out.println(b5.add(b4));
        System.out.println();

        System.out.println("Test 14: result should be\n3141592653598");
        b1 = new BigInt(0);
        int[] a6 = { 3,1,4,1,5,9,2,6,5,3,5,9,8 };
        b2 = new BigInt(a6);
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 15: result should be\n10000000000000000000");
        int[] a19 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };    // 19 nines!
        b1 = new BigInt(a19);
        b2 = new BigInt(1);
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 15.5: result should be\ntrue");
        int[] a20 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9};    // 20 nines!
        b1 = new BigInt(a20);
        b2 = new BigInt(1);
        BigInt b3 = b1.add(b2);
        System.out.println(b3.isOverFlow());
        System.out.println();

        System.out.println("Test 15.75: result should be\ntrue");
        b1 = new BigInt(a20);
        b2 = new BigInt(9);
        b3 = b1.mul(b2);
        System.out.println(b3.isOverFlow());
        System.out.println();

        System.out.println("Test 16: result should be \n0");
        b1 = new BigInt(0);
        b2 = new BigInt(10);
        System.out.println(b1.mul(b2).add(b2.mul(b1)));
        System.out.println();

        System.out.println("Test 17: result should be \n100");
        b1 = new BigInt(10);
        b2 = new BigInt(10);
        System.out.println(b1.mul(b2));
        System.out.println();

        System.out.println("Test 18: result should be \n222");
        b1 = new BigInt(111);
        b2 = new BigInt(1);
        System.out.println(b1.mul(b2).add(b2.mul(b1)));
        System.out.println();


        System.out.println("Test 18: result should be \n12345678987654321");
        b1 = new BigInt(111111111);
        b2 = new BigInt(111111111);
        System.out.println(b1.mul(b2));
        System.out.println();


        System.out.println("Test 18: result should be \ntrue");
        b1 = new BigInt(new int[] {1,1,1,1,1,1,1,1,1,1,1});
        b3 = b1.mul(b1);
        System.out.println(b3.isOverFlow());
        System.out.println();


        System.out.println("Test 18: result should be \nfalse");
        b1 = new BigInt(new int[] {1,1,1,1,1,1,1,1,1});
        b3 = b1.mul(b1);
        System.out.println(b3.isOverFlow());
        System.out.println();
    }
}
