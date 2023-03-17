import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeManagment {
    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);
    public static void employeeManagment(String loginDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println(currentDate);
            System.out.println(" ");
            System.out.println("1. Wyświetl pracowników");
            System.out.println("2. Dodaj pracownika");
            System.out.println("3. Usuń pracownika");
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
                    showWorkers();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 2 -> {
                    System.out.println(" ");
                    addWorker();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 3 -> {
                    System.out.println(" ");
                    removeWorker();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 4 -> BossPanel.start(loginDATABASE, hasloDATABASE, nameDATABASE);
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }
    public static void showWorkers() {
        Settings.clearScreen();
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT `id_konta`, `imie`, `nazwisko`, `nr_tel` FROM `konta` WHERE `stanowisko` = 'pracownik'");

            while (resultSelect.next()) {

                String table1 = resultSelect.getString("id_konta");
                String table2 = resultSelect.getString("imie");
                String table3 = resultSelect.getString("nazwisko");
                String table4 = resultSelect.getString("nr_tel");

                System.out.println("ID: " + table1 + " || Dane: " + table2 + " " + table3 + " || Tel: " + table4);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showWorkersAll() {
        Settings.clearScreen();
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `konta`");

            while (resultSelect.next()) {

                String table1 = resultSelect.getString("id_konta");
                String table2 = resultSelect.getString("identyfikator");
                String table3 = resultSelect.getString("imie");
                String table4 = resultSelect.getString("nazwisko");
                String table5 = resultSelect.getString("nr_tel");
                String table6 = resultSelect.getString("adres");
                String table7 = resultSelect.getString("mail");
                String table8 = resultSelect.getString("stanowisko");

                System.out.println("Id_konta: " + table1 + " || ID: " + table2 + " || Dane: " + table3 + " " + table4 + " || Tel: " + table5 + " || Adres: " + table6 + " || Mail: " + table7 + " || Stanowisko: " + table8);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addWorker() {
        Settings.clearScreen();
        int identyfikator;
        while (true) {
            System.out.print("Podaj identyfikator (10 cyfr): ");
            try {
                identyfikator = scanner.nextInt();
                if (String.valueOf(identyfikator).length() == 10) {

                    try {
                        ResultSet resultSelect = QueryExecutor.executeSelect("SELECT `identyfikator` FROM `konta` WHERE `identyfikator` = '" + identyfikator + "'");

                        if (resultSelect.next()) {

                            System.out.println("Identyfikator: " + identyfikator + " jest już zajęty");
                            addWorker();

                        } else {
                            scanner.nextLine();
                            System.out.print("Podaj hasło: ");
                            String password = scanner.nextLine();
                            System.out.print("Podaj imię: ");
                            String name = scanner.nextLine();
                            System.out.print("Podaj nazwisko: ");
                            String surname = scanner.nextLine();
                            int telNumber;
                            while (true) {
                                System.out.print("Podaj numer telefonu (9 cyfr): ");
                                try {
                                    telNumber = scanner.nextInt();
                                    if (String.valueOf(telNumber).length() == 9) {
                                        break;
                                    } else {
                                        System.out.println("Numer telefonu musi się składać z 9 cyfr, spróbuj jeszcze raz.");
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Wprowadzono nieprawidłowe dane, spróbuj jeszcze raz.");
                                    scanner.next();
                                }
                            }
                            scanner.nextLine();
                            System.out.print("Adres zamieszkania: ");
                            String homeAdress = scanner.nextLine();
                            System.out.print("Adres e-mail: ");
                            String emailAdress = scanner.nextLine();

                            QueryExecutor.executeQuery("INSERT INTO `konta`(`identyfikator`, `haslo`, `imie`, `nazwisko`, `nr_tel`, `adres`, `mail`, `stanowisko`) VALUES ('" + identyfikator + "','" + password + "','" + name + "','" + surname + "','" + telNumber + "','" + homeAdress + "','" + emailAdress + "','pracownik')");

                            System.out.println(" ");
                            System.out.println("Dodano pracownika");
                            System.out.println(" ");
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                } else {
                    System.out.println("Identyfikator powinien składać się z 10 cyfr, spróbuj jeszcze raz.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono nieprawidłowe dane, spróbuj jeszcze raz.");
                scanner.next();
            }
        }
    }

    private static void removeWorker() {
        Settings.clearScreen();
        showWorkers();
        System.out.println("--");

        int accountID;
        while (true) {
            System.out.print("Podaj ID konta do usunięcia: ");
            try {
                accountID = scanner.nextInt();

                try {
                    ResultSet resultSelect = QueryExecutor.executeSelect("SELECT `id_konta`, `stanowisko` FROM `konta` WHERE `id_konta` = '" + accountID + "'");

                    if (resultSelect.next()) {

                        String stanowisko = resultSelect.getString("stanowisko");

                        if (stanowisko.equals("pracownik")) {
                            String zapytanie = "DELETE FROM `konta` WHERE `id_konta` = '" + accountID + "' AND `stanowisko` = 'pracownik'";
                            QueryExecutor.executeQuery(zapytanie);

                            System.out.println(" ");
                            System.out.println("Usunięto pracownika");
                            System.out.println(" ");
                        } else {
                            System.out.println("Nie można usunąć konta administratora");
                            System.out.println(" ");
                            removeWorker();
                        }

                    } else {
                        System.out.println("Nie ma osoby o takim id konta.");
                        System.out.println(" ");
                        removeWorker();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono nieprawidłowe dane, spróbuj jeszcze raz.");
                scanner.next();
            }
        }

    }
}
