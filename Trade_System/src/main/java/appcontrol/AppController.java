package appcontrol;

import appcontrol.manager.*;
import appcontrol.visual.services.Services;
import appcontrol.visual.printer.Printer;
import database.connection.DataBase;

import java.sql.SQLException;

//Контроллер приложения координирует взаимодействие между пользовательским интерфейсом и базой данных
public class AppController {

    //Принтер для вывода пользовательского интерфейса
    Printer printer = new Printer();

    //Основной метод запуска приложения
    public void openApp() throws SQLException {
        //Соединение с базой данных
        DataBase.getConnection();

        //Флаг, управляющий основным циклом приложения
        boolean isRunning = true;
        //Пока пользователь не вышел из приложения, выводить меню
        while(isRunning) {
            //Вывод основного меню
            printer.printMenu();
            //Получение ввода пользователя
            String choice = Services.getInput();

            //Обработка вывода пользователя
            switch (choice) {
                case "1":
                    //Управление товарами
                    manageProducts();
                    break;
                case "2":
                    //Управление заказами
                    manageOrders();
                    break;
                case "3":
                    //Управление хранилищами
                    manageStorages();
                    break;
                case "4":
                    //Управление сотрудниками
                    manageWorkers();
                    break;
                case "5":
                    //Просмотр бухгалтерского учёта
                    showAccounting();
                    break;
                case "E", "e":
                    //Выход из приложения
                    isRunning = false;
                    break;
                default:
                    //Сообщение о некорректном вводе
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
        //Сообщение о выходе из приложения
        System.out.println("Exiting the program...\n");
    }

    //Управление товарами
    private void manageProducts() throws SQLException {
        //Флаг, управляющий меню управления
        boolean managing = true;

        while (managing) {
            //Вывод меню управления товарами
            printer.printProductManagement();
            //Получение ввода пользователя
            String choice = Services.getInput();

            //Обработка ввода пользователя
            switch (choice) {
                case "1":
                    //Просмотр доступных товаров
                    ProductsManager.printAvailableProducts();
                    break;
                case "2":
                    //Просмотр раскупленных товаров
                    ProductsManager.printSoldProducts();
                    break;
                case "3":
                    //Добавление товара
                    ProductsManager.addProduct();
                    break;
                case "4":
                    //Закупка товара
                    ProductsManager.purchaseProduct();
                    break;
                case "5":
                    //Перемещение товара в пункт продаж
                    ProductsManager.moveToSalePoint();
                    break;
                case "6":
                    //Перемещение товара в склад
                    ProductsManager.moveToWarehouse();
                    break;
                case "7":
                    //Перемещение в другой пункт продаж
                    ProductsManager.moveToOtherSalePoint();
                    break;
                case "B", "b":
                    //Возврат в основное меню
                    managing = false;
                    break;
                default:
                    //Сообщение о некорректном вводе
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    //Управление заказами
    private void manageOrders() throws SQLException {
        //Флаг, управляющий меню управления
        boolean managing = true;

        while (managing) {
            //Вывод меню управления заказами
            printer.printOrderManagement();
            //Получение ввода пользователя
            String choice = Services.getInput();

            //Обработка ввода пользователя
            switch (choice) {
                case "1":
                    //Создание нового заказа
                    OrdersManager.makeOrder();
                    break;
                case "2":
                    //Оформление возврата
                    OrdersManager.makeReturn();
                    break;
                case "3":
                    //Просмотр всех заказов
                    OrdersManager.printAllOrders();
                    break;
                case "4":
                    //Просмотр всех заказов определённого покупателя
                    OrdersManager.printOrdersByBuyer();
                    break;
                case "5":
                    //Просмотр всех заказов определённого товара
                    OrdersManager.printOrdersByProduct();
                    break;
                case "B", "b":
                    //Возврат в основное меню
                    managing = false;
                    break;
                default:
                    //Сообщение о некорректном вводе
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    //Управление хранилищами
    private void manageStorages() throws SQLException {
        //Флаг, управляющий меню управления
        boolean managing = true;

        while (managing) {
            //Вывод меню управления хранилищами
            printer.printStorageManagement();
            //Получение ввода пользователя
            String choice = Services.getInput();

            //Обработка ввода пользователя
            switch (choice) {
                case "1":
                    //Смена ответственного лица пункта продаж
                    StoragesManager.changeAdmin();
                    break;
                case "2":
                    //Открытие нового склада
                    StoragesManager.openNewWarehouse();
                    break;
                case "3":
                    //Открытие нового пункта продаж
                    StoragesManager.openNewSalePoint();
                    break;
                case "4":
                    //Закрытие хранилища
                    StoragesManager.closeStorage();
                    break;
                case "5":
                    //Просмотр всех хранилищ
                    StoragesManager.printAllStorages();
                    break;
                case "6":
                    //Просмотр информации о товарах на складе
                    StoragesManager.printWarehouseProductsInfo();
                    break;
                case "7":
                    //Просмотр информации о товарах в пункте продаж
                    StoragesManager.printSalePointProductsInfo();
                    break;
                case "B", "b":
                    //Возврат в основное меню
                    managing = false;
                    break;
                default:
                    //Сообщение о некорректном вводе
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    //Управление сотрудниками
    private void manageWorkers() throws SQLException {
        //Флаг, управляющим меню управления
        boolean managing = true;

        while (managing) {
            //Вывод меню управления сотрудниками
            printer.printWorkerManagement();
            //Получение ввода пользователя
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    //Добавление сотрудника
                    WorkersManager.addWorker();
                    break;
                case "2":
                    //Увольнение сотрудника
                    WorkersManager.fireWorker();
                    break;
                case "3":
                    //Перевод сотрудника в другой пункт продаж
                    WorkersManager.changeWorkPlace();
                    break;
                case "4":
                    //Просмотр всех действующих сотрудников
                    WorkersManager.printAllWorkers();
                    break;
                case "B", "b":
                    //Возврат в основное меню
                    managing = false;
                    break;
                default:
                    //Сообщение о некорректном вводе
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    //Просмотр бухгалтеркого учёта
    private  void showAccounting() throws SQLException {
        //Флаг, управляющий меню управления
        boolean managing = true;

        while (managing) {
            //Вывод меню бухгалтерии
            printer.printAccounting();
            //Получение ввода пользователя
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    //Просмотр выручки пункта продаж
                    AccountingManager.printSalePointRevenue();
                    break;
                case "2":
                    //Просмотр выручки всей компании
                    AccountingManager.printCompanyRevenue();
                    break;
                case "B", "b":
                    //Возврат в основное меню
                    managing = false;
                    break;
                default:
                    //Сообщение о некорректном вводе
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }
}