import java.util.*;
class Problem1 {
    public static void diamond(int n) {
        for (int i = 1; i < n; i++) {
            if (i < n / 2) {
                for (int j = 0; j < i; j++) {
                    System.out.print(i);
                }
            } else {
                for (int j = n - i; j > 0; j--) {
                    System.out.print(n - i);
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        diamond(10);
    }
};
