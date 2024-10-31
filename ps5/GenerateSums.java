class GenerateSums {
    public static String generateSums(int n) {
        if (n == 0) {
            return "";
        }
        String sumList = "1";
        String lineSum = "1";
        for (int i = 1; i <= n; i++) {
            // Deal with first case
            if (i == 1) {
                sumList += "\n";
                continue;
            }
            lineSum += " + " + i;
            // 1 + 2 + ... = sum\n
            sumList += lineSum + " = " + ((i * (i + 1))/2) + "\n";
        }
        return sumList;
    }
    public static void main(String[] args) {
        System.out.println(generateSums(6));
    }
}
