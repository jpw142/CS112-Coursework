/* Methods6.java
 * 
 * Code added by: Jack Weber, jrw142@bu.edu
 *
 * Practice with static methods, part I
 */

public class Methods6 {
    /*
     * 0) printVertical - takes a string s and prints the characters of 
     *    the string vertically -- with one character per line.
     */
    public static void printVertical(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.println(c);
        }
    }

    /*
     * 1) printEveryOther - takes a string s and prints every other character of 
     *    the string
     */
    public static void printEveryOther(String s) {
        for (int i = 0; i < s.length(); i += 2) {
            char c = s.charAt(i);
            System.out.print(c);
        }
    }

    /*
     * 2) longerLen - takes a string s and other string o and returns the longer 
     * length between the two
     */
    public static int longerLen(String s, String o) {
        int lenS = s.length();
        int lenO = o.length();
        if (lenS > lenO) {
            return lenS;
        }
        return lenO;
    }

    /*
     * 3) secondIndex - takes a string s and char c and returns the 
     * 2nd occurence of c's position in s, if there is no 2nd occurence of c
     * returns -1
     */
    public static int secondIndex(String s, char c) {
        int counter = 0;
        for (int i = 0; i < s.length(); i ++) {
            if (c == s.charAt(i)) {
                counter += 1;
            }

            if (counter == 2) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        /* Sample test call */
        printVertical("method");        
        printEveryOther("method");
        System.out.println();
        int len = longerLen("bye", "hello");
        System.out.println("the longer len is: " + len);
        int sndIndex = secondIndex("banana", 'a');
        System.out.println("the 2nd occurence is : " + sndIndex);

    }
}
