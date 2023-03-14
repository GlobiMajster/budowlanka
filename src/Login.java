import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    static Scanner scanner = new Scanner(System.in);
    public static void signIn() {
        try {
            System.out.println(" ");
            System.out.println("-------------------");
            System.out.println("      BartBUD      ");
            System.out.println("-------------------");
            System.out.println(" ");

            System.out.print("Login: ");
            String loginAPP = scanner.nextLine();
            System.out.print("Hasło: ");
            String hasloAPP = scanner.nextLine();

            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM `pracownicy` WHERE login='" + loginAPP + "'");

            if (result.next()) {
                String loginDATABASE = result.getString("login");
                String hasloDATABASE = result.getString("haslo");
                String nameDATABASE = result.getString("imie_pracownika");
                String positionDATABASE = result.getString("stanowisko");

                if (loginAPP.equals(loginDATABASE) && hasloAPP.equals(hasloDATABASE)) {
                    switch (positionDATABASE) {
//                        case "administrator" -> BossPanel.home(loginDATABASE, hasloDATABASE,nameDATABASE);
//
//                        case "kierownik" -> ManagerPanel.home(loginDATABASE, hasloDATABASE, nameDATABASE);
//
//                        case "pracownik" -> WorkerPanel.home(loginDATABASE, hasloDATABASE, nameDATABASE);
                    }
                } else {
                    System.out.println("Niepoprawne hasło");
                    signIn();
                }
            } else {
                System.out.println("Niepoprawny login lub hasło");
                signIn();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
