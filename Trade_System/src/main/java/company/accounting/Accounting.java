package company.accounting;

public class Accounting {

    public static double revenue; //выручка
    public static double profit; //прибыль
    public static double expences; //расходы

    public static void increaseRevenue(double money) {
        revenue += money;
    }

    public static void reduceRevenue(double money) {
        revenue -= money;
    }

    public static void increaseExpences(double money) {
        expences += money;
    }

    public static double getProfit() {
        return profit;
    }

    public static double getRevenue() {
        return revenue;
    }

    public static double getExpences() {
        return expences;
    }
}
