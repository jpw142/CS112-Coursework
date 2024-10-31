class ArrayRecursion {
    // Searches in element through array, put start = 0 for all cases
    public static boolean search(Object item, Object[] arr, int start) {
        if (arr == null) {
            throw new IllegalArgumentException();
        }
        if (arr.length == start) {
            return false;
        }
        if (arr[start].equals(item)) {
            return true;
        }
        return search(item, arr, start + 1);
    }    
    // Reverses an array and makes it into strings
    public static String reverseArrayToString(Object[] arr, int index) {
        if (arr == null) {
            return "";
        }
        if (arr.length == index) {
            return "[";
        }
        String rest = reverseArrayToString(arr, index + 1);
        String reversed = arr[index].toString();
        if (index != arr.length - 1) {
            reversed = ", " + reversed;
        }
        if (index == 0) {
            reversed = reversed + "]";
        }
        return rest + reversed;
    }
    public static void main(String[] args) {
        String[] arr = new String[] {"hello", "world", "my", "name", "is", "jack"};
        System.out.println(search("hello", arr, 0));
        System.out.println(search("hello", arr, 1));
        System.out.println(search("world", arr, 0));
        System.out.println(search("world", arr, 1));
        System.out.println(search("world", arr, 2));
        System.out.println(reverseArrayToString(arr, 0));

    }
}
