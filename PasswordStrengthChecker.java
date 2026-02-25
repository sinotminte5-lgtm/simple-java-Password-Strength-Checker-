import java.util.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;

public class PasswordStrengthChecker {

    // ==============================
    // 🔹 MAIN
    // ==============================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter password to check: ");
        String password = scanner.nextLine();

        try {

            int breachCount = checkPwned(password);
            double entropy = calculateEntropy(password);

            System.out.println("\n===== RESULT =====");
            System.out.println("Entropy: " + String.format("%.2f", entropy) + " bits");

            if (breachCount > 0) {
                System.out.println("❌ Found in breaches " + breachCount + " times!");
            } else {
                System.out.println("✅ Not found in breach database.");
            }

            System.out.println("Strength: " + gradePassword(entropy, breachCount));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    // ==============================
    // 🔹 HIBP API CHECK
    // ==============================
    public static int checkPwned(String password) throws Exception {

        String sha1 = sha1Hash(password);
        String prefix = sha1.substring(0, 5);
        String suffix = sha1.substring(5);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.pwnedpasswords.com/range/" + prefix))
                .GET()
                .header("User-Agent", "JavaPasswordChecker")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String[] lines = response.body().split("\n");

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts[0].equalsIgnoreCase(suffix)) {
                return Integer.parseInt(parts[1].trim());
            }
        }

        return 0; // Not found
    }

    // ==============================
    // 🔹 SHA-1 HASH
    // ==============================
    public static String sha1Hash(String input) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = md.digest(input.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }

    // ==============================
    // 🔹 ENTROPY CALCULATION
    // ==============================
    public static double calculateEntropy(String password) {

        int poolSize = 0;

        if (password.matches(".*[A-Z].*")) poolSize += 26;
        if (password.matches(".*[a-z].*")) poolSize += 26;
        if (password.matches(".*\\d.*")) poolSize += 10;
        if (password.matches(".*[~!@#$%^&*()\\-_=+\\[\\]{}|:;,.<>?].*"))
            poolSize += 32;

        if (poolSize == 0) return 0;

        return password.length() * (Math.log(poolSize) / Math.log(2));
    }

    // ==============================
    // 🔹 FINAL GRADE LOGIC
    // ==============================
    public static String gradePassword(double entropy, int breachCount) {

        if (breachCount > 100000)
            return "VERY WEAK (Extremely common password)";
        if (breachCount > 0)
            return "WEAK (Compromised password)";

        if (entropy < 40)
            return "WEAK";
        else if (entropy < 60)
            return "MEDIUM";
        else if (entropy < 80)
            return "STRONG";
        else
            return "VERY STRONG";
    }
}