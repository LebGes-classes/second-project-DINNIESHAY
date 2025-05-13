package appControl.visual.printer;

import appControl.visual.services.Services;

import java.io.File;

public class Printer {

    public void printMenu() {
        Services.printFile(new File("Trade_System/src/main/java/appControl/visual/printer/textfiles/menu.txt"));
    }

    public void printProductManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appControl/visual/printer/textfiles/product_management.txt"));
    }

    public void printStorageManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appControl/visual/printer/textfiles/storage_management.txt"));
    }

    public void printOrderManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appControl/visual/printer/textfiles/order_management.txt"));
    }

    public void printWorkerManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appControl/visual/printer/textfiles/worker_management.txt"));
    }
}
