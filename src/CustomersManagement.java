import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomersManagement {
    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);
    public static void customersManagement(String loginDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println(currentDate);
            System.out.println(" ");
            System.out.println("1. Wyswietl klientów");
            System.out.println("2. Dodaj klienta");
            System.out.println("3. Usuń klienta");
            System.out.println("4. Wróć");
            System.out.println(" ");

            int choice;
            while (true) {
                System.out.print("Wybierz opcje: ");
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
                    showCustomers();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 2 -> {
                    System.out.println(" ");
                    addCustomer();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 3 -> {
                    System.out.println(" ");
                    removeCustomer();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 4 -> BossPanel.start(loginDATABASE, hasloDATABASE, nameDATABASE);
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    public static void showCustomers() {
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `klienci`");

            while (resultSelect.next()) {

                String table0 = resultSelect.getString("id_klienta");
                String table1 = resultSelect.getString("imie_klienta");
                String table2 = resultSelect.getString("nazwisko_klienta");
                String table3 = resultSelect.getString("adres_klienta");
                String table4 = resultSelect.getString("telefon_klienta");
                String table5 = resultSelect.getString("email_klienta");

                System.out.println("------------------");
                System.out.println("ID: " + table0 + " Dane: " + table1 + " " + table2 + " - Adres: " + table3 + " - Tel: " + table4 + " - Mail: " + table5);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------");
    }
    public static void addCustomer() {
        scanner.nextLine();

        System.out.print("Imie klienta: ");
        String customerName = scanner.nextLine();
        System.out.print("Nazwisko: ");
        String customerSurname = scanner.nextLine();
        System.out.print("Adres zamieszkania: ");
        String customerAdress = scanner.nextLine();
        int customerTelNumber;
        while (true) {
            System.out.print("Nr. tel: ");
            try {
                customerTelNumber = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }
        System.out.print("Mail: ");
        String customerMail = scanner.nextLine();

        QueryExecutor.executeQuery("INSERT INTO `klienci`(`imie_klienta`, `nazwisko_klienta`, `adres_klienta`, `telefon_klienta`, `email_klienta`) VALUES ('"+customerName+"','"+customerSurname+"','"+customerAdress+"','"+customerTelNumber+"','"+customerMail+"')");

        System.out.println(" ");
        System.out.println("Dodano klienta do bazy");
    }
    public static void removeCustomer() {
        showCustomers();
        System.out.println(" ");

        int customerID;
        while (true) {
            System.out.print("ID klienta do usunięcia: ");
            try {
                customerID = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        QueryExecutor.executeQuery("DELETE FROM `klienci` WHERE id_klienta = '"+customerID+"'");

        System.out.println(" ");
        System.out.println("Usunięto klienta z bazy");
    }
}
