import java.util.InputMismatchException;
import java.util.Scanner;

public class Settings {
    static Scanner scanner = new Scanner(System.in);

    public static void changePassword(String loginDATABASE, String hasloDATABASE) {
        System.out.println(" ");
        System.out.println("-----------------");
        System.out.println("   Zmień hasło   ");
        System.out.println("-----------------");
        System.out.println(" ");

        System.out.print("Podaj obecne hasło: ");
        String stareHaslo = scanner.nextLine();

        if (stareHaslo.equals(hasloDATABASE)) {
            System.out.println("(Hasła powinny być różne)");
            System.out.print("Podaj nowe hasło: ");
            String noweHaslo = scanner.nextLine();

            if (!stareHaslo.equals(noweHaslo)) {
                String zapytanie = "UPDATE `pracownicy` SET `haslo`='"+noweHaslo+"' WHERE login ='"+loginDATABASE+"'";
                QueryExecutor.executeQuery(zapytanie);

                System.out.println(" ");
                System.out.println("Zmieniono hasło");
                System.out.println("Zostaniesz wylogowany...");
                Login.signIn();
                System.out.println(" ");
            } else {
                System.out.println("Hasła nie mogą być takie same");
                changePassword(loginDATABASE, hasloDATABASE);
            }

        } else {
            System.out.println("Błędne hasło, podaj nowe");
            changePassword(loginDATABASE, hasloDATABASE);
        }

    }

    public static void back() {
        while (true) {
            System.out.print("Wybierz opcje: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Nierozpoznana akcja");
                scanner.next();
            }
        }
    }
}
