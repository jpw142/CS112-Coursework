/* Methods7.java
 * 
 * Code added by: Jack Weber, jrw142@bu.edu
 *
 * Practice with static methods, part II
 */

class Methods7 {
    /*
     * 1) printDiag - takes a string s and prints the characters of 
     *    the string diagnolly -- with one character per line with spaces
     */
    public static void printDiag(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (int j = 0; j < i; j++) {
                System.out.print(" ");            
            }
            System.out.println(c);
        }
    }

    /*
     * 2) lastN - takes a string s and int n and returns a string with last n 
     * chars of string s, if n > length of s then just returns the string s
     */
    public static String lastN(String s, int n) {
        if (n > s.length()) {
            return s;
        }
        String lastChars = "";
        for (int i = s.length() - n; i < s.length(); i++) {
            lastChars += s.charAt(i);
        }
        return lastChars;
    }

    /*
     * 3) remSub - takes a string s and string r and returns a string
     * with the first occurence of r removed from s, if no r found
     * then just returns s
     
     */
    public static String remSub(String s, String r) {
        int firstOcc = s.indexOf(r);
        if (firstOcc == -1) {
            return s;
        }
        String removedString = "";
        int lenRemoved = r.length();
        for (int i = 0; i < firstOcc; i++) {
            removedString += s.charAt(i);
        }
        for (int i = firstOcc + lenRemoved; i < s.length(); i++) {
            removedString += s.charAt(i);
        }
        return removedString;        
    }

    /*
     * 4) interleave - takes a string s1 and string s2 and returns a string
     * with the two strings interleaved and any excess chars appended
     */
    public static String interleave(String s1, String s2) {
       int lenI = s1.length();
       int lenJ = s2.length();
       int i = 0;
       int j = 0;
       String result = ""; 
       while ((i < lenI) && (j < lenJ)) {
           result += s1.charAt(i);
           result += s2.charAt(j);
           i += 1;
           j += 1;
       }
       while (i < lenI) {
           result += s1.charAt(i);
           i += 1;
       }
       while (j < lenJ) {
           result += s2.charAt(j);
           j += 1;
       }
       return result;
    }

    public static void main(String[] args) {
        printDiag("method");
        String s = lastN("programming", 5);
        System.out.println("this is the last 5 of programming: " + s);
        String r = remSub("ding-a-ling", "ing");
        System.out.println("this is removed first ing: " + r);
        String i = interleave("leaving", "NOW");
        System.out.println("interleaved: " + i);
    }
}
