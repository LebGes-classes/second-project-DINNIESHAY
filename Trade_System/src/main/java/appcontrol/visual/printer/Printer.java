package appcontrol.visual.printer;

import appcontrol.visual.services.Services;

import java.io.File;

//Принтер для вывода пользовательского интерфейса
public class Printer {

    //Вывод основного меню
    public void printMenu() {
        Services.printFile(new File("Trade_System/src/main/java/appcontrol/visual/printer/textfiles/menu.txt"));
    }

    //Вывод текстового файла с меню управления товарами
    public void printProductManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appcontrol/visual/printer/textfiles/product_management.txt"));
    }

    //Вывод текстового файла с меню управления хранилищами
    public void printStorageManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appcontrol/visual/printer/textfiles/storage_management.txt"));
    }

    //Вывод текстового файла с меню управления заказами
    public void printOrderManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appcontrol/visual/printer/textfiles/order_management.txt"));
    }

    //Вывод текстового файла с меню управления сотрудниками
    public void printWorkerManagement() {
        Services.printFile(new File("Trade_System/src/main/java/appcontrol/visual/printer/textfiles/worker_management.txt"));
    }

    //Вывод текстового файла с меню бухгалтерии
    public void printAccounting() {
        Services.printFile(new File("Trade_System/src/main/java/appcontrol/visual/printer/textfiles/accounting.txt"));
    }
}
