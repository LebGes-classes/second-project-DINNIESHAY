package appcontrol.manager;

import appcontrol.visual.services.Services;
import company.storage.salepoint.SalePoint;
import company.user.worker.Worker;
import database.access.SalePointsAccess;
import database.access.WorkersAccess;

import java.sql.SQLException;
import java.util.ArrayList;

public class WorkersManager {

    static WorkersAccess workersAccess = new WorkersAccess();
    static SalePointsAccess salePointsAccess = new SalePointsAccess();
    
    public static void addWorker() throws SQLException {
        Worker worker = new Worker();

        System.out.println("Enter worker's first name: ");
        worker.setFirstName(Services.getInput());

        System.out.println("Enter worker's last name: ");
        worker.setLastName(Services.getInput());

        System.out.println("Enter worker's phone number:" );
        worker.setPhoneNumber(Services.getInput());

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

        workersAccess.add(worker);
    }

    public static void fireWorker() throws SQLException {
        String condition = "Status = 'Staff' OR Status = 'Admin'";

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

        SalePoint salePoint = salePointsAccess.getById(salePointsAccess.getId("Admin_id = " + workerToFire.id));
        if (salePoint != null) {
            salePoint.setAdminId(0);
            salePointsAccess.update(salePoint);
        }

        workersAccess.update(workerToFire);
    }

    public static void changeWorkPlace() throws SQLException {
        String condition = "Status = 'Staff'";

        System.out.println("Choose worker to change his work place:\n");
        if (noWorkers(condition)) {
            System.out.println("No workers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printWorkers(condition);
        Worker worker = workersAccess.getById(Integer.parseInt(Services.getInput()));

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

        workersAccess.update(worker);
    }

    public static void printAllWorkers() throws SQLException {
        String condition = "Status = 'Staff' OR Status = 'Admin'";
        if (noWorkers(condition)) {
            System.out.println("No workers");
        }
        printWorkers(condition);

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    public static void printWorkers(String condition) throws SQLException {
        ArrayList<Worker> workers = workersAccess.getAll(condition);
        for (Worker worker : workers) {
            System.out.print(worker);
        }
    }

    public static boolean noWorkers(String condition) throws SQLException {
        ArrayList<Worker> workers = workersAccess.getAll(condition);
        boolean noWorkers = workers.isEmpty();

        return noWorkers;
    }
}