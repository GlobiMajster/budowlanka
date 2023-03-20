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

            System.out.print("Login: ");
            String loginAPP = scanner.nextLine();
            System.out.print("Hasło: ");
            String hasloAPP = scanner.nextLine();

            ResultSet result = QueryExecutor.executeSelect("SELECT pracownicy.login, pracownicy.haslo, pracownicy.imie_pracownika, pracownicy.stanowisko, ekipy.nazwa FROM `pracownicy` INNER JOIN `ekipy` ON pracownicy.id_zespolu = ekipy.id_ekipy WHERE pracownicy.login ='" + loginAPP + "'");

            if (result.next()) {
                String loginDATABASE = result.getString("login");
                String hasloDATABASE = result.getString("haslo");
                String nameDATABASE = result.getString("imie_pracownika");
                String positionDATABASE = result.getString("stanowisko");
                String teamNameDATABASE = result.getString("nazwa");

                if (loginAPP.equals(loginDATABASE) && hasloAPP.equals(hasloDATABASE)) {
                    switch (positionDATABASE) {
                        case "szef" -> BossPanel.start(loginDATABASE, hasloDATABASE,nameDATABASE);

                        case "kierownik" -> ManagerPanel.start(loginDATABASE, hasloDATABASE, nameDATABASE, teamNameDATABASE);

                        case "pracownik" -> WorkerPanel.start(loginDATABASE, hasloDATABASE, nameDATABASE, teamNameDATABASE);
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
