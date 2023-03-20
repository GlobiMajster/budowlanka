import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TeamManagment {
    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);
    public static void teamManagment(String loginDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println("-----------------");
            System.out.println("      Ekipy      ");
            System.out.println("-----------------");
            System.out.println(currentDate);
            System.out.println(" ");
            System.out.println("1. Wyswietl ekipy");
            System.out.println("2. Wyswietl członków ekipy");
            System.out.println("3. Dodaj ekipę");
            System.out.println("4. Usuń ekipę");
            System.out.println("5. Zmień ekipe pracownika");
            System.out.println("6. Wróć");
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
                    showTeam();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 2 -> {
                    System.out.println(" ");
                    showTeamMades();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 3 -> {
                    System.out.println(" ");
                    addTeam();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 4 -> {
                    System.out.println(" ");
                    removeTeam();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 5 -> {
                    System.out.println(" ");
                    changeTeam();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 6 -> BossPanel.start(loginDATABASE, hasloDATABASE, nameDATABASE);
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }
    public static void showTeam() {
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `ekipy`");

            while (resultSelect.next()) {

                String table1 = resultSelect.getString("id_ekipy");
                String table2 = resultSelect.getString("nazwa");


                System.out.println("ID: " + table1 + " - Nazwa: " + table2);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void showTeamMades() {
        scanner.nextLine();
        showTeam();

        System.out.println(" ");
        int checkTeamID;
        while (true) {
            System.out.print("Podaj ID ekipy do sprawdzenia: ");
            System.out.println(" ");
            try {
                checkTeamID = scanner.nextInt();
                scanner.nextLine();

                try {
                    ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `pracownicy` INNER JOIN ekipy ON pracownicy.id_zespolu = ekipy.id_ekipy WHERE ekipy.id_ekipy = '"+checkTeamID+"';");

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

                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

    }
    private static void addTeam() {
        scanner.nextLine();
        System.out.println(" ");

        System.out.print("Podaj nazwę ekipy: ");
        String teamName = scanner.nextLine();

        QueryExecutor.executeQuery("INSERT INTO `ekipy`(`nazwa`) VALUES ('"+teamName+"')");

        System.out.println(" ");
        System.out.println("Dodano ekipę remontową do bazy.");
    }
    private static void removeTeam() {
        scanner.nextLine();
        System.out.println(" ");
        showTeam();
        System.out.println(" ");

        int removeTeamID;
        while (true) {
            System.out.print("Podaj ID ekipy do usunięcia: ");
            try {
                removeTeamID = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        QueryExecutor.executeQuery("DELETE FROM `ekipy` WHERE id_ekipy = '"+removeTeamID+"'");
        System.out.println(" ");
        System.out.println("Usunięto ekipę z bazy");
    }
    private static void changeTeam() {
        scanner.nextLine();
        System.out.println(" ");
        EmployeeManagment.showWorkers();
        System.out.println(" ");

        int moveWorker;
        while (true) {
            System.out.print("ID pracownika do przeniesienia: ");
            try {
                moveWorker = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        showTeam();
        System.out.println(" ");

        int moveWorkerTeamID;
        while (true) {
            System.out.print("ID nowej ekipy: ");
            try {
                moveWorkerTeamID = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę");
                scanner.next();
            }
        }

        QueryExecutor.executeQuery("UPDATE pracownicy SET id_zespolu = '"+moveWorkerTeamID+"' WHERE id_pracownika = '"+moveWorker+"';");

        System.out.println(" ");
        System.out.println("Pomyślnie zmieniono przydział");

    }
}
