package appcontrol.visual.cleaner;

public class Cleaner {

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
