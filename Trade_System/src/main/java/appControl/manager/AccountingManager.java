package appControl.manager;

import appControl.visual.services.Services;
import company.storage.salepoint.SalePoint;
import database.access.SalePointsAccess;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountingManager {

    static SalePointsAccess salePointsAccess = new SalePointsAccess();

    public static void printSalePointRevenue() throws SQLException {
        System.out.println("Choose sale point:\n");
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
        int salePointId = Integer.parseInt(Services.getInput());
        SalePoint salePoint = salePointsAccess.getById(salePointId);

        System.out.println("\nRevenue of sale point with address " + salePoint.street + ": " + salePoint.revenue);
        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    public static void printCompanyRevenue() throws SQLException {
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        double totalRevenue = 0;

        for (SalePoint salePoint : salePoints) {
            System.out.println("Revenue of sale point " + salePoint.id + ": " + salePoint.revenue);
            totalRevenue += salePoint.revenue;
        }
        System.out.println("--------------");
        System.out.println("Total revenue of company: " + totalRevenue);
        System.out.println("\nPress any key to go back");
        Services.getInput();
    }
}
