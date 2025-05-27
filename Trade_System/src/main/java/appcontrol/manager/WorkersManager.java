package appcontrol.manager;

import appcontrol.visual.services.Services;
import company.storage.salepoint.SalePoint;
import company.user.worker.Worker;
import database.access.SalePointsAccess;
import database.access.WorkersAccess;

import java.sql.SQLException;
import java.util.ArrayList;

//Класс для управление сотрудниками компании
public class WorkersManager {

    //Доступ к данным о сотрудниках
    static WorkersAccess workersAccess = new WorkersAccess();
    static SalePointsAccess salePointsAccess = new SalePointsAccess();

    //Добавление нового сотрудника
    public static void addWorker() throws SQLException {
        Worker worker = new Worker();

        //Ввод данных о сотруднике
        System.out.println("Enter worker's first name: ");
        worker.setFirstName(Services.getInput());

        System.out.println("Enter worker's last name: ");
        worker.setLastName(Services.getInput());

        System.out.println("Enter worker's phone number:" );
        worker.setPhoneNumber(Services.getInput());

        //Выбор места работы сотрудника
        System.out.println("Choose worker's work place:\n");
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        if (salePoints.isEmpty()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (SalePoint salePoint : salePoints) {
                System.out.print(salePoint);
            }
        }
        worker.setWorkPlaceId(Integer.parseInt(Services.getInput()));
        worker.setStatus("Staff");

        //Добавление сотрудника в базу данных
        workersAccess.add(worker);
    }

    //Увольнение сотрудника
    public static void fireWorker() throws SQLException {
        String condition = "Status = 'Staff' OR Status = 'Admin'";
        //Выбор сотрудника, которого необходимо уволить
        System.out.println("Choose worker to fire:\n");
        if(noWorkers(condition)) {
            System.out.println("No workers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printWorkers(condition);
        Worker workerToFire = workersAccess.getById(Integer.parseInt(Services.getInput()));
        workerToFire.setStatus("Fired");

        //Если сотрудник был администратором пункта продаж, обновляем данные об этом пункте продаж
        SalePoint salePoint = salePointsAccess.getById(salePointsAccess.getId("Admin_id = " + workerToFire.id));
        if (salePoint != null) {
            salePoint.setAdminId(0);
            salePointsAccess.update(salePoint);
        }

        //Обновление информации о сотруднике в базе данных
        workersAccess.update(workerToFire);
    }

    //Изменение места работы сотрудника
    public static void changeWorkPlace() throws SQLException {
        String condition = "Status = 'Staff'";

        //Выбор сотрудника (из не администраторов)
        System.out.println("Choose worker to change his work place:\n");
        if (noWorkers(condition)) {
            System.out.println("No workers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printWorkers(condition);
        Worker worker = workersAccess.getById(Integer.parseInt(Services.getInput()));

        //Выбор его нового места работы
        System.out.println("Choose his new work place:\n");
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        if (salePoints.isEmpty()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (SalePoint salePoint : salePoints) {
                System.out.print(salePoint);
            }
        }
        worker.setWorkPlaceId(Integer.parseInt(Services.getInput()));

        //Обновление информации об этом сотруднике в базе данных
        workersAccess.update(worker);
    }

    //Вывод всех сотрудников
    public static void printAllWorkers() throws SQLException {
        String condition = "Status = 'Staff' OR Status = 'Admin'";
        if (noWorkers(condition)) {
            System.out.println("No workers");
        }
        printWorkers(condition);

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    //Вывод сотрудников, удовлетворяющих некоторому условию
    public static void printWorkers(String condition) throws SQLException {
        ArrayList<Worker> workers = workersAccess.getAll(condition);
        for (Worker worker : workers) {
            System.out.print(worker);
        }
    }

    //Проверка на отсутствие сотрудников
    public static boolean noWorkers(String condition) throws SQLException {
        ArrayList<Worker> workers = workersAccess.getAll(condition);
        boolean noWorkers = workers.isEmpty();

        return noWorkers;
    }
}