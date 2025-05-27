package appcontrol.manager;

import appcontrol.visual.services.Services;
import company.storage.salepoint.SalePoint;
import database.access.SalePointsAccess;

import java.sql.SQLException;
import java.util.ArrayList;

//Класс для получения информации о бухгалтерии
public class AccountingManager {

    //Доступ данных о пунктах продаж
    static SalePointsAccess salePointsAccess = new SalePointsAccess();

    //Вывод выручки конкретного пункта продаж
    public static void printSalePointRevenue() throws SQLException {
        System.out.println("Choose sale point:\n");
        //Получение списка всех точек продаж
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        //При отсутсвии данных о пунктах продаж выводится сообщение об отсутствии пунктов продаж
        if (salePoints.isEmpty()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            //Вывод списка пунктов продаж
            for (SalePoint salePoint : salePoints) {
                System.out.print(salePoint);
            }
        }
        //Получение ввода пользователя
        int salePointId = Integer.parseInt(Services.getInput());
        SalePoint salePoint = salePointsAccess.getById(salePointId);

        //Вывод выручки пункта продаж с его адресом
        System.out.println("\nRevenue of sale point with address " + salePoint.street + ": " + salePoint.revenue);
        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    //Вывод выручки компании
    public static void printCompanyRevenue() throws SQLException {
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        double totalRevenue = 0;

        //Вывод выручки во всех пунктах продаж
        for (SalePoint salePoint : salePoints) {
            System.out.println("Revenue of sale point " + salePoint.id + ": " + salePoint.revenue);
            totalRevenue += salePoint.revenue;
        }
        //Вывод общей выручки
        System.out.println("--------------");
        System.out.println("Total revenue of company: " + totalRevenue);
        System.out.println("\nPress any key to go back");
        Services.getInput();
    }
}
