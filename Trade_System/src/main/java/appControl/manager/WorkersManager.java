package appControl.manager;

import appControl.visual.services.Services;
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

        workersAccess.add(worker);
    }

    public static void fireWorker() throws SQLException {
        System.out.println("Choose worker to fire:\n");
        ArrayList<Worker> workers = workersAccess.getAll("Work_place_id > 0");
        if (workers.isEmpty()) {
            System.out.println("No workers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Worker worker : workers) {
                System.out.print(worker);
            }
        }
        Worker workerToFire = workersAccess.getById(Integer.parseInt(Services.getInput()));
        workerToFire.setWorkPlaceId(0);

        workersAccess.update(workerToFire);
    }

    public static void changeWorkPlace() throws SQLException {
        System.out.println("Choose worker to change his work place:\n");
        ArrayList<Worker> workers = workersAccess.getAll("Work_place_id > 0");
        if (workers.isEmpty()) {
            System.out.println("No workers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Worker worker : workers) {
                System.out.print(worker);
            }
        }
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
        ArrayList<Worker> workers = workersAccess.getAll("Work_place_id > 0");
        if (workers.isEmpty()) {
            System.out.println("No workers");
        } else {
            for (Worker worker : workers) {
                System.out.print(worker);
            }
        }

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }
}