import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProjectManagment {
    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);
    public static void projectManagment(String loginDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println("--------------------");
            System.out.println("      Projekty      ");
            System.out.println("--------------------");
            System.out.println(currentDate);
            System.out.println(" ");
            System.out.println("1. Wyswietl projekty");
            System.out.println("2. Dodaj projekt");
            System.out.println("3. Zarchiwizuj projekt");
            System.out.println("4. Archiwum");
            System.out.println("5. Wróć");
            System.out.println(" ");

            int choice;
            while (true) {
                System.out.print("Wybierz akcje(1/5): ");
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
                    showProjects();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 2 -> {
                    System.out.println(" ");
                    addProject();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 3 -> {
                    System.out.println(" ");
                    archiveProject();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 4 -> {
                    System.out.println(" ");
                    archives();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 5 -> BossPanel.start(loginDATABASE, hasloDATABASE, nameDATABASE);
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    public static void showProjects() {
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `zlecenia` INNER JOIN ekipy ON zlecenia.id_ekipy = ekipy.id_ekipy INNER JOIN klienci ON zlecenia.id_klienta = klienci.id_klienta WHERE status_zlecenia = 'otwarte' ORDER BY `zlecenia`.`id_zlecenia` ASC");

            while (resultSelect.next()) {

                String table0 = resultSelect.getString("id_zlecenia");
                String table1 = resultSelect.getString("opis_zlecenia");
                String table2 = resultSelect.getString("data_przyjecia");
                String table4 = resultSelect.getString("data_realizacji");
                String table5 = resultSelect.getString("dni_pracy");
                String table6 = resultSelect.getString("godziny_prac");
                String table7 = resultSelect.getString("koszt_zlecenia");
                String table8 = resultSelect.getString("nazwa");
                String table9 = resultSelect.getString("imie_klienta");
                String table10 = resultSelect.getString("nazwisko_klienta");
                String table11 = resultSelect.getString("telefon_klienta");
                String table12 = resultSelect.getString("email_klienta");
                String table13 = resultSelect.getString("status_zlecenia");

                System.out.println("------------------");
                System.out.println("Dane zlecenia: ");
                System.out.println("ID: " + table0 + " - Temat: " + table1);
                System.out.println("Data przyjęcia: " + table2 + " - Termin realizacji: " + table4);
                System.out.println("Dni pracy: " + table5 + " W godzinach: " + table6 + " - Ekipa: " + table8);
                System.out.println("Całkowity koszt: " + table7);
                System.out.println("------------------");
                System.out.println("Dane klienta: ");
                System.out.println(table9 + " " + table10 + " tel: " + table11 + " " + table12);
                System.out.println("------------------");
                System.out.println("Status zlecenia: " + table13);
                System.out.println("------------------");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void addProject() {
        scanner.nextLine();

        System.out.print("Temat zlecenia: ");
        String description = scanner.nextLine();

        System.out.print("Start zlecenia (rrrr-mm-dd): ");
        String startDate = scanner.nextLine();
        LocalDate start = null;

        while (start == null) {
            if (startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    start = LocalDate.parse(startDate);
                } catch (Exception e) {
                    System.out.print("Nieprawidłowa data, podaj w formacie rrrr-mm-dd: ");
                    startDate = scanner.nextLine();
                }
            } else {
                System.out.print("Nieprawidłowy format daty, podaj w formacie rrrr-mm-dd: ");
                startDate = scanner.nextLine();
            }
        }
        System.out.print("Koniec zlecenia (rrrr-mm-dd): ");
        String endDate = scanner.nextLine();
        LocalDate end = null;
        while (end == null) {
            if (endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    end = LocalDate.parse(endDate);
                } catch (Exception e) {
                    System.out.print("Nieprawidłowa data, podaj w formacie rrrr-mm-dd: ");
                    endDate = scanner.nextLine();
                }
            } else {
                System.out.print("Nieprawidłowy format daty, podaj w formacie rrrr-mm-dd: ");
                endDate = scanner.nextLine();
            }
        }

        System.out.print("Dni pracy (np. PN-PT): ");
        String workDays = scanner.nextLine();

        System.out.print("Godziny pracy (np. 7:00-17:00): ");
        String hoursOfWork = scanner.nextLine();

        int payments;
        while (true) {
            System.out.print("Koszt zlecenia: ");
            try {
                payments = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        CustomersManagement.showCustomers();
        System.out.println(" ");

        int clientID;
        while (true) {
            System.out.print("ID Klienta: ");
            try {
                clientID = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }
        TeamManagment.showTeam();
        System.out.println(" ");

        int teamID;
        while (true) {
            System.out.print("ID Ekipy: ");
            try {
                teamID = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        QueryExecutor.executeQuery("INSERT INTO `zlecenia`(`opis_zlecenia`, `data_rozpoczecia`, `data_realizacji`, `dni_pracy`, `godziny_prac`, `koszt_zlecenia`, `id_klienta`, `id_ekipy`, `status_zlecenia`) VALUES ('"+description+"','"+startDate+"','"+endDate+"','"+workDays+"','"+hoursOfWork+"','"+payments+"','"+clientID+"','"+teamID+"','otwarte')");
        System.out.println(" ");
        System.out.println("Dodano projekt do bazy");
    }
    private static void archiveProject() {
        showProjects();

        int archive;
        while (true) {
            System.out.print("ID zlecenia do archiwizacji: ");
            try {
                archive = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        QueryExecutor.executeQuery("UPDATE `zlecenia` SET `status_zlecenia`='zamkniete' WHERE zlecenia.id_zlecenia = '"+archive+"' ");
        System.out.println(" ");
        System.out.println("Pomyślnie dodano zlecenie o ID " +archive+ " do archiwum.");
    }
    private static void archives() {

        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `zlecenia` INNER JOIN ekipy ON zlecenia.id_ekipy = ekipy.id_ekipy INNER JOIN klienci ON zlecenia.id_klienta = klienci.id_klienta WHERE status_zlecenia = 'zamkniete' ORDER BY `zlecenia`.`id_zlecenia` ASC");

            while (resultSelect.next()) {

                String table0 = resultSelect.getString("id_zlecenia");
                String table1 = resultSelect.getString("opis_zlecenia");
                String table2 = resultSelect.getString("data_przyjecia");
                String table4 = resultSelect.getString("data_realizacji");
                String table5 = resultSelect.getString("dni_pracy");
                String table6 = resultSelect.getString("godziny_prac");
                String table7 = resultSelect.getString("koszt_zlecenia");
                String table8 = resultSelect.getString("nazwa");
                String table9 = resultSelect.getString("imie_klienta");
                String table10 = resultSelect.getString("nazwisko_klienta");
                String table11 = resultSelect.getString("telefon_klienta");
                String table12 = resultSelect.getString("email_klienta");
                String table13 = resultSelect.getString("status_zlecenia");

                System.out.println("------------------");
                System.out.println("Dane zlecenia: ");
                System.out.println("ID: " + table0 + " - Temat: " + table1);
                System.out.println("Data przyjęcia: " + table2 + " - Termin realizacji: " + table4);
                System.out.println("Dni pracy: " + table5 + " W godzinach: " + table6 + " - Ekipa: " + table8);
                System.out.println("Całkowity koszt: " + table7);
                System.out.println("------------------");
                System.out.println("Dane klienta: ");
                System.out.println(table9 + " " + table10 + " tel: " + table11 + " " + table12);
                System.out.println("------------------");
                System.out.println("Status zlecenia: " + table13);
                System.out.println("------------------");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
