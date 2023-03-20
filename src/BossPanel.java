import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BossPanel {
    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);

    public static void start(String loginDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println("-------------------");
            System.out.println("      BartBUD      ");
            System.out.println("-------------------");
            System.out.println("Witaj " + nameDATABASE);
            System.out.println(currentDate);
            System.out.println(" ");
            System.out.println("1. Zarządzaj projektami");
            System.out.println("2. Zarządzaj klientami");
            System.out.println("3. Zarządzaj pracownikami");
            System.out.println("4. Zarządzaj ekipami");
            System.out.println("5. Ustawienia");
            System.out.println("6. Wyloguj");
            System.out.println(" ");

            int choice;
            while (true) {
                System.out.print("Wybierz akcje(1/6): ");
                try {
                    choice = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Wybierz odpowiednią opcję.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1 -> {
                    System.out.println(" ");
                    ProjectManagment.projectManagment(loginDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 2 -> {
                    System.out.println(" ");
                    CustomersManagement.customersManagement(loginDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 3 -> {
                    System.out.println(" ");
                    EmployeeManagment.employeeManagment(loginDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 4 -> {
                    System.out.println(" ");
                    TeamManagment.teamManagment(loginDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 5 -> {
                    System.out.println(" ");
                    Settings.changePassword(loginDATABASE, hasloDATABASE);

                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 6 -> {
                    System.out.println("Wylogowywanie");
                    Login.signIn();
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }
}
