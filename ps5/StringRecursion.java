class StringRecursion {
    // Prints the string reversed
    public static void printReverse(String str) {
        if (str.equals("") || str == null) {
            return;
        }
        printReverse(str.substring(1));
        System.out.print(str.charAt(0));
    }
    // Trims whitespace around string
    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return "";
        }
        String newString = "";
        newString += str;
        if (newString.substring(0, 1).equals(" ")) {
            newString = trim(newString.substring(1));
        }
        if (newString.substring(newString.length() - 1, newString.length()).equals(" ")) {
            newString = trim(newString.substring(0, newString.length() - 1));
        }
        return newString;
    }

    // Finds the index of the char
    public static int find(char ch, String str) {
        if (str.equals("") || str == null) {
            return -1;
        }
        if (str.charAt(0) == ch) {
            return 0;
        }
        int rest = find(ch, str.substring(1));
        if (rest == -1) {
            return -1;
        }
        return rest + 1;
    }

    // Wesves two strings together
    public static String weave(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new IllegalArgumentException();
        }
        if (str1.equals("")) {
            return str2;
        }
        if (str2.equals("")) {
            return str1;
        }
        String rest = weave(str1.substring(1), str2.substring(1));
        String weaved = str1.substring(0, 1) + str2.substring(0, 1) + rest;
        return weaved;
    }

    // Finds index of first occurence of the character
    public static int indexOf(char ch, String str) {
        // I acknowledge that this is the exact same as the above
        if (str.equals("") || str == null) {
            return -1;
        }
        if (str.charAt(0) == ch) {
            return 0;
        }
        int rest = indexOf(ch, str.substring(1));
        if (rest == -1) {
            return -1; 
        }
        return rest + 1;
    }

    public static void main(String[] args) {
        printReverse("Terriers");
        System.out.println("");
        System.out.println(trim("  hello world    ") + "1");
        System.out.println(trim("recursion   ") + "1");
        System.out.println(find('b', "Rabbit"));
        System.out.println(find('P', "Rabbit"));
        System.out.println(weave("aaaa", "bbbb"));
        System.out.println(weave("hello", "world"));
        System.out.println(weave("hello", "worldomgIloveCats"));
        System.out.println(indexOf('b', "Rabbit"));
        System.out.println(indexOf('P', "Rabbit"));
    }
}
