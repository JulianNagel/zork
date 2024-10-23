import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readCommand() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void close() {
        scanner.close();
    }
}
