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
            System.out.println("--------------------");
            System.out.println("     Pracownicy     ");
            System.out.println("--------------------");
            System.out.println(currentDate);
            System.out.println(" ");
            System.out.println("1. Wyświetl pracowników");
            System.out.println("2. Dodaj pracownika");
            System.out.println("3. Usuń pracownika");
            System.out.println("4. Wróć");
            System.out.println(" ");

            int choice;
            while (true) {
                System.out.print("Wybierz akcje(1/4): ");
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
                    showWorkersAll();
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
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `pracownicy` INNER JOIN ekipy ON pracownicy.id_zespolu = ekipy.id_ekipy WHERE stanowisko = 'pracownik' OR stanowisko = 'kierownik' ORDER BY `pracownicy`.`id_pracownika` ASC;");

            while (resultSelect.next()) {

                String table1 = resultSelect.getString("id_pracownika");
                String table2 = resultSelect.getString("imie_pracownika");
                String table3 = resultSelect.getString("nazwisko_pracownika");
                String table4 = resultSelect.getString("numer_telefonu");
                String table5 = resultSelect.getString("stanowisko");
                String table6 = resultSelect.getString("nazwa");


                System.out.println("ID: " + table1 + " - Dane: " + table2 + " " + table3 + " - Tel: " + table4 + " - Stanowisko: " + table5 + " - Ekipa: " + table6);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void showWorkersAll() {
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `pracownicy` INNER JOIN ekipy ON pracownicy.id_zespolu = ekipy.id_ekipy WHERE stanowisko = 'pracownik' OR stanowisko = 'kierownik' ORDER BY `pracownicy`.`id_pracownika` ASC;");

            while (resultSelect.next()) {

                String table1 = resultSelect.getString("id_pracownika");
                String table8 = resultSelect.getString("login");
                String table9 = resultSelect.getString("haslo");
                String table2 = resultSelect.getString("imie_pracownika");
                String table3 = resultSelect.getString("nazwisko_pracownika");
                String table4 = resultSelect.getString("numer_telefonu");
                String table5 = resultSelect.getString("stanowisko");
                String table6 = resultSelect.getString("nazwa");
                String table7 = resultSelect.getString("adres_email");

                System.out.println("ID: " + table1 + " - Dane: " + table2 + " " + table3 + " - Tel: " + table4 + " - Mail: " + table7 + " - Stanowisko: " + table5 + " - Ekipa: " + table6 + " - Login: " + table8 + " Hasło: " + table9);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void addWorker() {
        scanner.nextLine();
        System.out.println(" ");

        System.out.print("Podaj login: ");
        String login = scanner.nextLine();
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
        System.out.print("Adres e-mail: ");
        String emailAdress = scanner.nextLine();
        TeamManagment.showTeam();
        System.out.println(" ");
        int teamID;
        while (true) {
            System.out.print("ID zespołu: ");
            try {
                teamID = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        QueryExecutor.executeQuery("INSERT INTO `pracownicy`(`login`, `haslo`, `imie_pracownika`, `nazwisko_pracownika`, `adres_email`, `numer_telefonu`, `stanowisko`, `id_zespolu`) VALUES ('"+login+"','"+password+"','"+name+"','"+surname+"','"+emailAdress+"','"+telNumber+"','pracownik','"+teamID+"')");

        System.out.println(" ");
        System.out.println("Dodano pracownika");
    }
    private static void removeWorker() {
        showWorkers();
        System.out.println("--");

        int accountID;
        while (true) {
            System.out.print("Podaj ID konta do usunięcia: ");
            try {
                accountID = scanner.nextInt();

                try {
                    ResultSet resultSelect = QueryExecutor.executeSelect("SELECT `id_pracownika`, `stanowisko` FROM `pracownicy` WHERE `id_pracownika` = '" + accountID + "'");

                    if (resultSelect.next()) {

                        String stanowisko = resultSelect.getString("stanowisko");

                        if (stanowisko.equals("pracownik")) {
                            String zapytanie = "DELETE FROM `pracownicy` WHERE `id_pracownika` = '" + accountID + "' AND `stanowisko` = 'pracownik'";
                            QueryExecutor.executeQuery(zapytanie);

                            System.out.println(" ");
                            System.out.println("Usunięto pracownika");
                        } else {
                            System.out.println("Nie można usunąć konta administratora lub kierownika");
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