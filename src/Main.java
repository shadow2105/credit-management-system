import services.Menu;

public class Main {
    public static void main(String[] args) {
        do {
            Menu.loginMenu();
        } while (!Menu.isExit());
    }
}