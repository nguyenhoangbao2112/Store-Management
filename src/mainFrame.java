////////////////////////////////////////////////////////////////

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
////////////////////////////////////////////////////////////////
public class mainFrame {
    public static void main(String[] args) throws IOException {
        Table store;
        String name;
        int amount;
        int value;
        double price;
        InfoSupply supply;
        store = mainTable();            // create a new one or load store
        store.displayTable();
        while(true)
         {
         System.out.print("\nEnter exactly what you want of show: ");
         System.out.print("buy, upgrade size, display, set price, sell, remove, search, sort, next day, save, total profit, quit: ");
         String choice = getString().toLowerCase();
         switch(choice)
            { 
                case "buy":
                   System.out.print("Enter supply name: ");
                   name = getString();
                   System.out.print("Enter amount: ");
                   amount = getInt();
                   System.out.print("How much for each supply: ");
                   price = getDouble();
                   store.buy(name.toLowerCase(),amount,price);
                   break;
                case "upgrade size":
                   store.upgradeStore();
                   break;
                case "display":
                   store.displayTable();
                   break;
                case "set price":
                   System.out.print("Enter your supply name that you want to set price: ");
                   name = getString();
                   supply = store.search(name.toLowerCase());
                   if (supply == null) {
                        System.out.println("Don't have that supply in your store");
                        break;
                    }
                   else {
                        System.out.print("Enter how much do you want to set price for each: ");
                        price = getDouble();
                        store.setPrice(supply,price);
                        break;
                    }
                case "sell":
                   System.out.print("Enter your supply name that you want to sell: ");
                   name = getString();
                   supply = store.search(name.toLowerCase());
                   if (supply == null) {
                        System.out.println("Don't have that supply in your store");
                        break;
                    }
                   else {
                        System.out.print("Enter amount of supply you sell: ");
                        amount = getInt();
                        store.sell(supply,amount);
                        break;
                    }
                case "remove":
                    System.out.print("Enter your supply name that you want to sell: ");
                    name = getString();
                    supply = store.search(name.toLowerCase());
                    if (supply == null) {
                        System.out.println("Don't have that supply in your store");
                        break;
                    }
                    else {
                        store.remove(supply);
                        break;
                    }
                case "search":
                   System.out.print("Enter your supply name that you want to look up: ");
                   name = getString();
                   supply = store.search(name.toLowerCase());
                   if (supply == null) {
                        System.out.println("Don't have that supply in your store");
                        break;
                    }
                    else {
                        System.out.println("......................................................................");
                        System.out.format("%-20s %-10s %-10s %-10s\n","NAME","AMOUNT","ISEMPTY","PRICE");
                        System.out.format("----------------------------------------------------------------------\n");
                        supply.display();
                        System.out.println("......................................................................");
                        break;
                    }
                case "sort":
                   System.out.print("Sorted name or Sorted amount or Sorted is empty (1 or 2 or 3)? ");
                   value = getInt();
                   store.TypeSort(value);
                   break;
                case "next day":
                   store.endDay();
                   break;
                case "save":
                   System.out.print("Enter your file name: ");
                   name = getString();
                   String data = name + ".txt";
                   save(store,data);
                   System.out.println("Saved successfullly");
                   break;
                case "total profit":
                   store.AllProfit();
                   break;
                case "quit":
                   return;
                default:
                   System.out.print("Invalid entry\n");
                   break;

            }
         }
    }
// -------------------------------------------------------------
    public static Table mainTable() throws IOException {
        System.out.print("\nEnter first letter, new store, load: ");
        int choice = getChar();
        switch(choice) {
            case 'n':
               System.out.print("Enter your balance: ");
               double balance = getDouble();
               System.out.print("Enter size of store: ");
               int size = getInt();
               Table store = new Table(balance,size);       // create a new store
               return store;
            case 'l':
               System.out.print("Enter your data name: ");
               String name = getString();
               String data = name + ".txt";
               return load(data);     // load file 
            default:
               System.out.print("Invalid entry\n");
               return mainTable();          // return again method if enter wrong
        }
    }
// -------------------------------------------------------------
    public static void save(Table data, String filename) throws IOException {       // method save data
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))) {
            oos.writeObject(data);      
        } 
    }
// -------------------------------------------------------------
    public static Table load(String filename) throws IOException {      // method load data
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))) {
            Table store = (Table)ois.readObject();      // try to read data
            System.out.println("Load successfully");
            return store;       
        }
        catch (Exception e) {
            System.out.println("You dont have that saved data");
            return mainTable();     // if dont have data return to create new store
        }
    }
// -------------------------------------------------------------
    public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
// -------------------------------------------------------------
    public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }
//-------------------------------------------------------------
    public static int getInt() throws IOException
      {
      String s = getString();
      try {
        return Integer.parseInt(s);
      } catch (NumberFormatException e) {
        System.out.print("Please enter Integer value: ");
        return getInt();
      }
      }
    public static double getDouble() throws IOException {
        String s = getString();
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.out.print("Please enter Double value: ");
            return getDouble();
        }
    }
// -------------------------------------------------------------
}
////////////////////////////////////////////////////////////////

class InfoOwner implements Serializable {       // Information about owner or user
    private double balance;
    private double totalProfit;
    private double totalAllProfit;
    private double income;
    private double supplyCosts;
    private int countDay;
// -------------------------------------------------------------
    public InfoOwner(double balance, double totalProfit, double totalAllProfit, double income, double supplyCosts, int countDay) {      // constructor
        this.balance = balance;
        this.totalProfit = totalProfit;
        this.totalAllProfit = totalAllProfit;
        this.income = income;
        this.supplyCosts = supplyCosts;
        this.countDay = countDay;
    }
// -------------------------------------------------------------
    public double getBalance() {
        return balance;
    }
// -------------------------------------------------------------
    public double getTotalProfit() {
        return getIncome() - getSupplyCosts();
    }
// -------------------------------------------------------------
    public double getTotalAllProfit() {
        return totalAllProfit;
    }
// -------------------------------------------------------------
    public double getIncome() {
        return income;
    }
// -------------------------------------------------------------
    public double getSupplyCosts() {
        return supplyCosts;
    }
// -------------------------------------------------------------
    public int getCountDay() {
        return countDay;
    }
// -------------------------------------------------------------
    public void setBalance(double balance) {
        this.balance = balance;
    } 
// -------------------------------------------------------------
    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }
// -------------------------------------------------------------
    public void setTotalAllProfit(double totalAllProfit) {
        this.totalAllProfit = totalAllProfit;
    }
    public void setIncome(double income) {
        this.income = income;
    }
// -------------------------------------------------------------
    public void setSupplyCosts(double supplyCosts) {
        this.supplyCosts = supplyCosts;
    }
// -------------------------------------------------------------
    public void setCountDay(int countDay) {
        this.countDay = countDay;
    }
// -------------------------------------------------------------
}
////////////////////////////////////////////////////////////////

class InfoSupply implements Serializable {      // infomation about supply that you add to your store
    private String name;
    private int amount;
    private boolean isEmpty;
    private double price;
// -------------------------------------------------------------
    public InfoSupply(String name, int amount, boolean isEmpty, double price) {     // constructor
        this.name = name;
        this.amount = amount;
        this.isEmpty = isEmpty;
        this.price = price;
    }
// -------------------------------------------------------------
    public String getName() {
        return name;
    }
// -------------------------------------------------------------
    public int getAmount() {
        return amount;
    }
// -------------------------------------------------------------
    public boolean getIsEmpty() {
        return isEmpty;
    }
// -------------------------------------------------------------
    public double getPrice() {
        return price;
    }
// -------------------------------------------------------------
    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
// -------------------------------------------------------------
    public void setAmount(int amount) {
        this.amount = amount;
    }
// -------------------------------------------------------------
    public void setPrice(double price) {
        this.price = price;
    }
// -------------------------------------------------------------
    public void display() {
        System.out.format("%-20s %-10d %-10s %.2f$\n",name,amount,isEmpty ? "Yes" : "No",price);
    }
// -------------------------------------------------------------
}
/////////////////////////////////////////////////////////////////// 

class Table implements Serializable { 
    private InfoSupply[] Store;     // data structure arraylist
    private InfoOwner Owner;
    private int nElemns;
    private int sizeStore;
// -------------------------------------------------------------
    public Table(double balance, int size) {        // constructor
        sizeStore = size;
        Store = new InfoSupply[sizeStore];
        Owner = new InfoOwner(balance,0.0,0.0,0.0,0.0,1);
        nElemns = 0;
    }
    public void upgradeStore() {        // method upgrade size for store
        double OwnerBalance = Owner.getBalance();
        if (OwnerBalance - 100 < 0) {
            System.out.println("You don't have enough money to upgrade size of your store");
        }
        else {
            int size = sizeStore + 100;
            InfoSupply[] Store1 = new InfoSupply[size];
            for (int i = 0; i < nElemns; i++) {
                Store1[i] = Store[i];
            }
            Store = Store1;
            sizeStore = size;
            Owner.setBalance(OwnerBalance - 100);
            System.out.println("Upgrade size store successfully");
        }
    }
// -------------------------------------------------------------
    public void buy(String name, int amount, double price) {        // method buy supply to store in Store O(n)
        if (nElemns == sizeStore) {     
            System.out.println("Your Store is full, please upgrade it");
            return;
        }
        if (amount <= 0 || price <= 0) {
            System.out.println("Invalid amount or price. Please try again.");
            return;
        }
        InfoSupply supply;
        supply = new InfoSupply(name,amount,false, price);
        double totalBuy = amount * price;       // amount * price for each amount
        double ownerBalance = Owner.getBalance();
        if (ownerBalance - totalBuy < 0) {
            System.out.println("You dont have enough money");
        }
        else {
            Owner.setBalance(ownerBalance - totalBuy);      // minus balance
            double costs = totalBuy + Owner.getSupplyCosts();   
            Owner.setSupplyCosts(costs);
            for (int i = 0; i < nElemns; i++) {     // find out if there is duplicated or not
                if (Store[i].getName().equals(name)) {
                    int amountStore = Store[i].getAmount();
                    Store[i].setAmount(amount + amountStore);
                    Store[i].setIsEmpty(false);
                    System.out.println("Buy " + amount + " " + name + " successfully with " + (amount * price) + "$");
                    return;
                }
            }
            Store[nElemns] = supply;
            nElemns++;
            System.out.println("Buy " + amount + " " + name + " successfully with " + (amount * price) + "$");
        }
    }
// -------------------------------------------------------------
    public InfoSupply search(String name) {      // method search supply O(n)
        for (int i = 0; i < nElemns; i++) {
            if (Store[i].getName().equals(name))
                return Store[i];
        }
        return null;
    }
// -------------------------------------------------------------
    public void sell(InfoSupply supply, int amount) {     // method sell supply to get income O(1)
        if (amount <= 0) {
            System.out.println("Invalid amount. Please try again.");
            return;
        }
        int supplyAmount = supply.getAmount();
        double supplyPrice = supply.getPrice();
        double ownerIncome = Owner.getIncome();
        double ownerBalance = Owner.getBalance();
        if (supplyAmount < amount) {
            System.out.println("Don't have enough supply to sell");
        }
        else {
            supply.setAmount(supplyAmount - amount);
            Owner.setIncome(ownerIncome + supplyPrice * amount);
            Owner.setBalance(ownerBalance + supplyPrice * amount);
            if (supply.getAmount() == 0) 
                supply.setIsEmpty(true);
            System.out.println("Sell " + amount + " " + supply.getName() + " successfully with " + supplyPrice * amount + "$");
        }
    }
// -------------------------------------------------------------
    public void remove(InfoSupply supply) {       // method delete supply that u add wrong don't have income but u get back balance O(n)
        double supplyPrice = supply.getPrice();
        int supplyAmount = supply.getAmount();
        double ownerBalance = Owner.getBalance();
        Owner.setBalance(ownerBalance + supplyPrice * supplyAmount);
        InfoSupply[] Store1 = new InfoSupply[sizeStore]; 
        for (int i = 0, k = 0; i < nElemns; i++) {
            if (Store[i] == supply) {
                continue;
            }
            Store1[k] = Store[i];
            k++;
        }
        nElemns--;
        Store = Store1;
        System.out.println("Remove " + supply.getName() + " successfully");
    }
// -------------------------------------------------------------
    public void setPrice(InfoSupply supply, double price) {     // set price for that supply
        if (price <= 0) {
            System.out.println("Invalid price. Please try again.");
            return;
        }
        System.out.println(supply.getName() + ": " + supply.getPrice() + " --> " + price);
        supply.setPrice(price);
    }
// -------------------------------------------------------------
    public void displayTable() {        // method display table
        System.out.println("");
        System.out.println("......................................................................");
        System.out.format("Balance: " + Owner.getBalance() + "$\n");
        System.out.format("Day: " + Owner.getCountDay() + "\n");
        System.out.format("%-20s %-10s %-10s %-10s\n","NAME","AMOUNT","ISEMPTY","PRICE");
        System.out.format("----------------------------------------------------------------------\n");
        for (int i = 0; i < nElemns; i++) {
            Store[i].display();
        }
        System.out.println("......................................................................");
    }
// -------------------------------------------------------------
    public void TypeSort(int sortType) {            // method for you to choose what kind of sort you want
        switch(sortType)
         {
         case 1: sort(Store, 0, nElemns-1, sortType);
                 System.out.println("Store sorted by name successfuly");
                 break;
         case 2: sort(Store, 0, nElemns-1, sortType);
                 System.out.println("Store sorted by amount successfuly");
                 break;
         case 3: sort(Store, 0, nElemns-1, sortType);
                 System.out.println("Store sorted by available successfuly");
                 break;
         default: System.out.println("Invalid sort type. Please choose 1, 2, or 3.");
                  break;
         }
    }
// -------------------------------------------------------------
    // Main function that sorts arr[l..r] using
    // merge()
    public void sort(InfoSupply[] arr, int l, int r, int sortType)
    {
        if (l < r) {

            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            sort(arr, l, m, sortType);
            sort(arr, m + 1, r, sortType);

            // Merge the sorted halves
            if (sortType == 1) 
                sortName(arr, l, m, r);
            if (sortType == 2) 
                sortAmount(arr, l, m, r);
            if (sortType == 3)
                sortIsEmpty(arr, l, m, r);
        }
    }
// -------------------------------------------------------------
    public void sortName(InfoSupply[] arr, int l, int m, int r) {        // method sort by name
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        // Create temp arrays
        InfoSupply[] L = new InfoSupply[n1];
        InfoSupply[] R = new InfoSupply[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        // Merge the temp arrays

        // Initial indices of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getName().compareTo(R[j].getName()) < 0) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
// -------------------------------------------------------------
    public void sortAmount(InfoSupply[] arr, int l, int m, int r) {      // method sort by amount
        int n1 = m - l + 1;
        int n2 = r - m;

        InfoSupply[] L = new InfoSupply[n1];
        InfoSupply[] R = new InfoSupply[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i].getAmount() <= R[j].getAmount()) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
// -------------------------------------------------------------
    public void sortIsEmpty(InfoSupply[] arr, int l, int m, int r) {     // method sort by IsEmpty or not
        int n1 = m - l + 1;
        int n2 = r - m;

        InfoSupply[] L = new InfoSupply[n1];
        InfoSupply[] R = new InfoSupply[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i].getIsEmpty()) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
// -------------------------------------------------------------
    public void endDay() {          // method end day to show daily statistics
        System.out.println("\n------------DAILY STATISTICS------------");
        System.out.println("________________________________________");
        System.out.print("\t" + "    Income: " + Owner.getIncome() + "$\n");
        System.out.print("\t" + "    Supply Costs: " + "-" + Owner.getSupplyCosts() + "$\n");
        System.out.print("\t" + "    Total Profit: " + Owner.getTotalProfit() + "$\n");
        System.out.println("________________________________________");
        System.out.print("\t" + "    Balance: " + Owner.getBalance() + "$\n");
        Owner.setCountDay(Owner.getCountDay() + 1);
        Owner.setTotalAllProfit(Owner.getTotalAllProfit() + Owner.getTotalProfit());
        Owner.setIncome(0);
        Owner.setSupplyCosts(0);
    }
    public void AllProfit() {
        System.out.println("\nTotal profit from Day 1 -> Day " + Owner.getCountDay() + ": " + Owner.getTotalAllProfit() + "$");
    }
// -------------------------------------------------------------
}
////////////////////////////////////////////////////////////////
