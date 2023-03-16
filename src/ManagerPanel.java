import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagerPanel {

    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);

    public static void start(String loginDATABASE, String hasloDATABASE, String nameDATABASE, String teamNameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println("Witaj " + nameDATABASE);
            System.out.println(currentDate);
            System.out.println("Twój zespół: " + teamNameDATABASE);
            System.out.println(" ");
            System.out.println("1. Wyświetl projekty");
            System.out.println("2. Wyświetl ekipe");
            System.out.println("3. Ustawienia");
            System.out.println("4. Wyloguj");
            System.out.println(" ");

            int choice;
            while (true) {
                System.out.print("Wybierz akcje(1/3): ");
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
                    System.out.println("-----------------");
                    System.out.println("     Projekty    ");
                    System.out.println("-----------------");
                    System.out.println(" ");
                    try {

                        ResultSet resultSelect = QueryExecutor.executeSelect("SELECT zlecenia.opis_zlecenia, zlecenia.data_rozpoczecia, zlecenia.data_realizacji, zlecenia.dni_pracy, zlecenia.godziny_prac FROM `pracownicy` INNER JOIN `ekipy` ON pracownicy.id_zespolu = ekipy.id_ekipy INNER JOIN `zlecenia` ON ekipy.id_ekipy = zlecenia.id_ekipy WHERE pracownicy.login = '"+loginDATABASE+"' AND pracownicy.stanowisko = 'kierownik' AND zlecenia.status_zlecenia = 'otwarte'");

                        while (resultSelect.next()) {

                            String table1 = resultSelect.getString("opis_zlecenia");
                            String table2 = resultSelect.getString("data_rozpoczecia");
                            String table3 = resultSelect.getString("data_realizacji");
                            String table4 = resultSelect.getString("dni_pracy");
                            String table5 = resultSelect.getString("godziny_prac");

                            System.out.println("Cel: " + table1 + "  -  Rozpoczęcie prac: " + table2 + "  -  Data oddania: " + table3 + " - Dni pracujące: " + table4 + " - Godziny pracy: " + table5);

                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();

                }
                case 2 -> {
                    System.out.println(" ");
                    System.out.println("----------------");
                    System.out.println("      Ekipa     ");
                    System.out.println("----------------");
                    System.out.println(" ");
                    try {

                        ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM pracownicy WHERE id_zespolu = ( SELECT id_zespolu FROM pracownicy WHERE login = '"+loginDATABASE+"' AND stanowisko = 'kierownik' );");

                        while (resultSelect.next()) {

                            String table1 = resultSelect.getString("imie_pracownika");
                            String table2 = resultSelect.getString("nazwisko_pracownika");
                            String table3 = resultSelect.getString("numer_telefonu");
                            String table4 = resultSelect.getString("stanowisko");

                            System.out.println("Dane: " + table1 + " " + table2 + " - Tel: " + table3 + " - Stanowisko: " + table4);

                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 3 -> {
                    System.out.println("||----------------||");
                    System.out.println("||   Ustawienia   ||");
                    System.out.println("||----------------||");

                    Settings.changePassword(loginDATABASE, hasloDATABASE);

                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.back();
                }
                case 4 -> {
                    System.out.println(" ");
                    System.out.println("Wylogowywanie");
                    System.out.println(" ");
                    Login.signIn();
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }
}
