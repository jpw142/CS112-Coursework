import java.util.Arrays;
class Problem5 {
    public static int[] union(int[] a1, int[] a2) {
        int[] newArray = new int[a1.length + a2.length]; 
        Sort.quickSort(a1);
        Sort.quickSort(a2);
        int newIndex = 0;
        int a1Index = 0;
        int a2Index = 0;
        while (a1Index < a1.length && a2Index < a2.length) {
            System.out.println(a1.length);
            System.out.println(a1Index);
            
            System.out.println("");
            System.out.println(a2.length);
            System.out.println(a2Index);
            // If its equal skip
            if (a1[a1Index] == newArray[newIndex] && a1[a1Index] != 0) {
                a1Index += 1;
                continue;
            }
            if (a2[a2Index] == newArray[newIndex] && a2[a2Index] != 0) {
                a2Index += 1;
                continue;
            }
            // If first Array is less than the second array vice versa
            if (a1[a1Index] < a2[a2Index]) {
                newArray[newIndex] = a1[a1Index];
                newIndex += 1;
                a1Index += 1;
                continue;
            }

            if (a2[a2Index] < a1[a1Index]) {
                newArray[newIndex] = a2[a2Index];
                newIndex += 1;
                a2Index += 1;
                continue;
            }
        }
        return newArray;
    }

    public static void main(String[] args) {
        int[] a1 = {10, 5, 7, 5, 9, 4};
        int[] a2 = {7, 5, 15, 7, 7, 9, 10};
        int[] result1 = union(a1, a2);
        System.out.println("result1: " + Arrays.toString(result1));

        int[] a3 = {0, 2, -4, 6, 10, 8};
        int[] a4 = {12, 0, -4, 8};
        int[] result2 = union(a3, a4);
        System.out.println("result2: " + Arrays.toString(result2));
    }
}
