package warehouse;

import DBSystem.UserConnection;

import java.util.ArrayList;
//
//
public class Menu {
    // ANSI escape codes for text colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    private static int countLoginManager = 0;
    private static int countLoginUser = 0;
    private static String nextLine = "";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // HOME TYPE ACCESSOR MENU  WAREHOUSE
    public static void menu() {



        System.out.println();
        System.out.println(CYAN+"\t Menu Warehouse T5"+RESET);
        System.out.println(CYAN+"\t ================="+RESET);

        // this give option to choose for users or management
        System.out.println(YELLOW+"\t 1. Client Menu "+RESET);
        System.out.println(BLUE+"\t 2. Management Menu"+RESET);
        System.out.println(RED+"\t 3. Exit "+RESET);
        // this is the input which will take action in the switch statement
        System.out.print("\t Enter: ");
        String option = InputUtils.getStringInput();
        switch (option) {
            case "1":
                userAccess();
                break;
            case "2":
                managerAccess();
                break;
            case "3":
                System.exit(0);
                InputUtils.closeScanner();
            default:
                System.out.println("\t Please enter the correct number from 1-2 try again");
                menu();
                break;
        }
    }

    // USER ACCESS MENU
    public static void userAccess() {
        System.out.println(YELLOW+"\t ============================================"+RESET);
        System.out.println("\t 1. Login");
        System.out.println("\t 2. Sign up");
        System.out.println(PURPLE+"\t 3. Back"+RESET);
        System.out.println(RED+"\t 4. Exit"+RESET);

        System.out.print("\t Enter: ");

        String optionUser = InputUtils.getStringInput();
        if(optionUser.isEmpty()){
            System.out.println("\t Invalid input (empty) ");
            userAccess();
        }else {

            switch (optionUser) {
                case "1":
                    userMenuLogin();
                    break;
                case "2":
                    userMenuRegistrationManu();
                    break;
                case "3":
                    menu();
                    break;
                case "4":
                    System.exit(0);
                    InputUtils.closeScanner();
                    break;
                default:
                    System.out.println("\t Please enter the correct number from 1-4 try again");
                    userAccess();
                    break;
            }
        }
    }

    // USER MENU
    // *****************************************************
    public static void userMenu() {
        System.out.println();
        System.out.println("\t --- WAREHOUSE CLIENT MENU ---");
        System.out.println("\t =========================== ");
        System.out.println(YELLOW+"\t client [ "+Warehouse.getConnectedUsers()[0]+" ] pass [ "+ Warehouse.getConnectedUsers()[1] + " ]"+ " balance [ " + Warehouse.getConnectedUsers()[4] + " ]"+RESET);
        System.out.println("\t Hello!!! Wish you the best, welcome!\n");
        System.out.println("\t 1. View All Products in Warehouse");
        System.out.println("\t 2. Search Products");
        System.out.println("\t 3. Add Product to Cart");
        System.out.println("\t 4. View Cart and Calculate Total Price");
        System.out.println("\t 5. Remove Product from Cart by ID");
        System.out.println("\t 6. Finalize Purchase");
        System.out.println("\t 7. Calculate Average Purchase Price in Warehouse");
        System.out.println("\t 8. Add cash to account");
        System.out.println("\t 9. Check balance");
        System.out.println("\t 10. View Purchase History");
        System.out.println(PURPLE+"\t 11. Back"+RESET);
        System.out.println(RED+"\t 12. Exit"+RESET);

        System.out.print("\t Enter: ");
        String optionUser = InputUtils.getStringInput();

        switch (optionUser) {
            case "1":
                System.out.println(YELLOW+"\t --WareHouse items-- "+RESET);
                System.out.println(YELLOW+"\t =================== "+RESET);
                Warehouse.getAllProductUser();
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                userMenu();
                break;
            case "2":
                System.out.println(YELLOW+"\t -- SEARCH AREA --"+RESET);
                System.out.println(YELLOW+"\t =================="+RESET);
                System.out.println("\t Choose Search Option:");
                System.out.println("\t     1. By Type");
                System.out.println("\t     2. By Manufacturer");
                System.out.println("\t     3. By Model");
                System.out.println("\t     4. By Selling Price");
                System.out.println("\t     5. By Price Range");
                System.out.println(PURPLE+"\t     6. Back"+RESET);

                System.out.print("\t Enter: ");
                String searchOption = InputUtils.getStringInput();

                if (searchOption.equals("6")) {
                    userMenu();
                }
                Warehouse.handleSearchOption(searchOption);
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                userMenu();
                break;
            case "3":
                System.out.println(YELLOW+"\t -- ADD PRODUCT TO CART --"+RESET);
                System.out.println(YELLOW+"\t ========================="+RESET);
                System.out.println("\t Enter your item here down (add to cart )   ID,Quantity ");
                System.out.print("\t Enter ID: ");
                String id = InputUtils.getStringInput();
                Warehouse.addToCartUser(id);
                userMenu();
                break;
            case "4":
                System.out.println(YELLOW+"\t --CLIENT CART-- "+RESET);
                System.out.println(YELLOW+"\t =================== "+RESET);
                Warehouse.getUserCartItems();
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                userMenu();
                break;
            case "5":
                System.out.println(YELLOW+"\t DELETE ITEM BY ID"+RESET);
                System.out.println(YELLOW+"\t ================="+RESET);
                System.out.print("\t Enter ID: ");
                String idItem = InputUtils.getStringInput();
                boolean removing = Warehouse.removeItemFromUserCart(idItem);
                if(removing){
                    System.out.println(GREEN+"\t item ["+idItem+"] has been removed from the Cart"+RESET);
                    userMenu();
                }else{
                    System.out.println(RED+"\t item ["+idItem+"] does not exist in the Cart"+RESET);
                    userMenu();
                }

                break;
            case "6":
                System.out.println(YELLOW+"\t -- FINALIZE PURCHASE --"+RESET);
                System.out.println(YELLOW+"\t ======================="+RESET);
                System.out.println("\t 1. custom item in the cart");
                System.out.println("\t 2. all the cart");
                System.out.println(PURPLE+"\t 3. back"+RESET);
                System.out.print("\t Enter: ");
                String option = InputUtils.getStringInput();
                if (option.equals("3")) {
                    userMenu();
                }
                else if (option.equals("1")) {
                    System.out.println("\t Are you sure you want to finalize your purchase? (y/n)");
                    System.out.print("\t Enter: ");
                    String confirmPurchase = InputUtils.getStringInput();
                    if (confirmPurchase.equalsIgnoreCase("y")) {
                        System.out.print("\t Enter item ID: ");
                        String idItemToFinalise = InputUtils.getStringInput();
                        Warehouse.finalizePurchase(idItemToFinalise);
                        userMenu();
                    } else {
                        System.out.println("\t Purchase canceled.");
                        userMenu();
                    }
                }
                else if(option.equals("2")){
                   Warehouse.finalizePurchaseAll();
                   userMenu();
                }

                break;
            case "7":
                System.out.println(YELLOW+"\t -- AVERAGE PURCHASE PRICE --"+RESET);
                System.out.println(YELLOW+"\t ============================"+RESET);
                System.out.println("\t " + YELLOW + Warehouse.getConnectedUsers()[0] + RESET + " account");
                System.out.println("\t APP: "+ BLUE + Warehouse.getAveragePurchase() + RESET );
                userMenu();
                break;
            case "8":
                System.out.println("\n");
                System.out.println("\t "+YELLOW+Warehouse.getConnectedUsers()[0]+RESET+" account");

                System.out.println("\t==============================================");
                System.out.print("\t Enter the amount: ");
                double amount = InputUtils.getDoubleInput();
                boolean deposit = Warehouse.updateUserBalance(amount);

                if(deposit){
                    System.out.println("\t Thank you for using this service");
                    userMenu();
                }else{
                    System.out.println("\t Try again please with new recommendations");
                    userMenu();
                }

                break;
            case "9":
                // check cash
                System.out.println(YELLOW+"\t -- CHECK BALANCE --"+RESET);
                System.out.println(YELLOW+"\t ==================="+RESET);
                System.out.println("\t "+YELLOW+Warehouse.getConnectedUsers()[0]+RESET+" account");
                System.out.println("\t ==================");
                System.out.println("\t Client Balance: " +YELLOW+Warehouse.getConnectedUsers()[4]+RESET);
                userMenu();
                break;
            case "10":
                System.out.println(YELLOW+"\t -- CLIENT PURCHASE HISTORY  --"+RESET);
                System.out.println(YELLOW+"\t ============================"+RESET);
                Warehouse.viewHistoryOrder();
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                userMenu();
                break;
            case "11":
                userAccess();
                break;
            case "12":
                System.exit(0);
                InputUtils.closeScanner();
                break;
            default:
                System.out.println("\t Please enter a valid option (1-9). Try again.");
                userMenu();
                break;
        }
    }

    // MANAGEMENT ACCESS MENU
    public static void managerAccess() {
        System.out.println(BLUE+"\t ========================================="+RESET);
        System.out.println("\t 1. Login");
        System.out.println("\t 2. Sign up");
        System.out.println(PURPLE+"\t 3. Back"+RESET);
        System.out.println(RED+"\t 4. Exit"+RESET);

        System.out.print("\t Enter: ");
        String optionManager = InputUtils.getStringInput();
        if(optionManager.isEmpty()){
            managerAccess();
        }else {

            switch (optionManager) {
                case "1":
                    managerLoginMenu();
                    break;
                case "2":
                    managerRegistrationMenu();
                    break;
                case "3":
                    menu();
                    break;
                case "4":
                    System.exit(0);
                    InputUtils.closeScanner();
                    break;
                default:
                    System.out.println("\t Please enter the correct number from 1-4 try again");
                    break;
            }
        }

    }
    // *********************************************************************

    // MANAGEMENT MENU
    public static void managementMenu() {
        System.out.println();
        System.out.println("\t  --- MANAGEMENT MENU ---");
        System.out.println("\t ======================== ");
        System.out.println(BLUE+"\t Manager [ "+Warehouse.getConnectedUsers()[0]+ " ] pass [ "+ Warehouse.getConnectedUsers()[1] + " ]"+RESET);
        System.out.println("\t Hello admin what's fun today !\n");
        System.out.println("\t 1. Add New Product to Warehouse");
        System.out.println("\t 2. Update Product to Warehouse");
        System.out.println("\t 3. Remove Product from Warehouse");
        System.out.println("\t 4. View All Products in Warehouse");
        System.out.println("\t 5. Search Products in Warehouse");
        System.out.println("\t 6. Calculate Average Purchase Price / selling / client");
        System.out.println("\t 7. Check balance");
        System.out.println("\t 8. View Sales History");
        System.out.println("\t 9. wareHouse income / purchase / profit ");
        System.out.println(PURPLE+"\t 10. Back"+RESET);
        System.out.println(RED+"\t 11. Exit"+RESET);

        System.out.print("\t Enter: ");
        String optionUser = InputUtils.getStringInput();

        switch (optionUser) {
            case "1":
                System.out.println(BLUE+"\t -- ADD PRODUCT TO WAREHOUSE --"+RESET);
                System.out.println(BLUE+"\t =============================="+RESET);
                Warehouse.addToWarehouseItem();
                managementMenu();
                break;
            case "2":
                System.out.println(BLUE+"\t Enter the ID of Device to Modify"+RESET);
                System.out.println(BLUE+"\t ================================"+RESET);
                System.out.print("\t Enter ID: ");
                String id = InputUtils.getStringInput();
                boolean confirm = Warehouse.updatingDevice(id);
                if(confirm){
                    System.out.println("\t ....done ");
                }
                managementMenu();
                break;

            case "3":
                System.out.println(BLUE+"\t Enter the ID of Device to Delete"+RESET);
                System.out.println(BLUE+"\t ================================"+RESET);
                System.out.print("\t Enter ID: ");
                String idToRemove = InputUtils.getStringInput();
                Warehouse.deleteProduct(idToRemove);
                managementMenu();
                break;
            case "4":
                System.out.println(BLUE+"\t -- WAREHOUSE PRODUCT --"+RESET);
                System.out.println(BLUE+"\t ======================="+RESET);
                 Warehouse.getAllProductManager();
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                 managementMenu();
                break;
            case "5":
                System.out.println(BLUE+"\t  -- SEARCH AREA --"+RESET);
                System.out.println(BLUE+"\t ==================="+RESET);
                System.out.println("\t Choose Search Option:");
                System.out.println("\t     1. By Type");
                System.out.println("\t     2. By Manufacturer");
                System.out.println("\t     3. By Model");
                System.out.println("\t     4. By Selling Price");
                System.out.println("\t     5. By Price Range");
                System.out.println(PURPLE+"\t     6. Back"+RESET);

                System.out.print("\t Enter: ");
                String searchOption = InputUtils.getStringInput();
                if (searchOption.equals("6")) {
                    managementMenu();
                }
                Warehouse.handleSearchOption(searchOption);
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                managementMenu();
                break;
            case "6":
                System.out.println(BLUE+"\t --   AVERAGES --"+RESET);
                System.out.println(BLUE+"\t ==================="+RESET);
                Warehouse.getAllAverage();
                managementMenu();
                break;
            case "7":
                System.out.println("\t ===============================");
                System.out.println("\t Manager Balance: " +Menu.RED+Warehouse.getConnectedUsers()[4]+Menu.RESET);
                managementMenu();
                break;
            case "8":
                System.out.println(BLUE+"\t -- ORDER HISTORY --"+RESET);
                System.out.println(BLUE+"\t ==================="+RESET);
                Warehouse.viewSalesHistory();
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                managementMenu();
                break;
            case "9":
                Warehouse.infoWareHouse();
                System.out.print("\t Enter any key to continue: ");
                InputUtils.getStringInput();
                managementMenu();
                break;
            case "10":
                managerAccess();
                break;
            case "11":
                System.exit(0);
                InputUtils.closeScanner();
                break;
            default:
                System.out.println("\t Please enter the correct number from 1-8 try again");
                managementMenu();
                break;
        }
    }
    // *************************************************************


    // Management Registration menu
    public static void managerRegistrationMenu() {
        System.out.println();
        System.out.println("\t  MANAGEMENT MENU REGISTRATION");
        System.out.println("\t ============================= ");
        System.out.println(RED + "\t Enter ( 0 ) to discard" + RESET);
        System.out.print("\t Enter name: ");
        String managerName = InputUtils.getStringInput();
        if (managerName.equals("0")) {
            managerAccess();
        }
        System.out.print("\t Enter password: ");
        String managerPassword = InputUtils.getStringInput();
        if (managerPassword.equals("0")) {
            managerAccess();
        }
        ArrayList<String> managerReg = new ArrayList<>();
        managerReg.add(managerName);
        managerReg.add(managerPassword);

        System.out.println("\t registering..............");
        managerRegistration(managerReg); // go for registration
    }

    public static void managerRegistration(ArrayList<String> managerData) {

        boolean checker = Warehouse.manageRegister(managerData.get(0), managerData.get(1));
        if (checker) {
            managerAccess();
            countLoginManager = 0;
        } else {
            countLoginManager++;
            System.out.println("\t trial " + countLoginManager);

            if (countLoginManager >= 3) {
                System.out.println("\t 3 trials done program out");
                System.exit(0);
                InputUtils.closeScanner();
            } else {
                managerRegistrationMenu();
            }
        }

    }
    //**************************************************

    // MANAGER LOGIN

    public static void managerLoginMenu() {

        System.out.println("\t MANAGEMENT LOGIN ");
        System.out.println("\t ================ ");
        System.out.println(RED + "\t Enter ( 0 ) to discard" + RESET);
        System.out.print("\t Enter name: ");
        String managerName = InputUtils.getStringInput();
        if (managerName.equals("0")) {
            managerAccess();
        }
        System.out.print("\t Enter password: ");
        String managerPassword = InputUtils.getStringInput();
        if (managerPassword.equals("0")) {
            managerAccess();
        }
        managerLogin(managerName, managerPassword);
    }

    public static void managerLogin(String name, String password) {
        boolean checker = Warehouse.managerLogin(name, password);
        if (checker) {
            managementMenu();
            countLoginManager = 0;
        } else {
            countLoginManager++;
            System.out.println("\t trial " + countLoginManager);

            if (countLoginManager >= 3) {
                System.out.println(GREEN+"\t 3 trials done create another account"+RESET);
                managerAccess();
                countLoginManager = 0;
            } else {
                managerLoginMenu();
            }
        }
    }

    // **************************************************************


    // user Registration menu

    public static void userMenuRegistrationManu() {
        System.out.println();
        System.out.println("\t  CLIENT MENU REGISTRATION");
        System.out.println("\t =========================");
        System.out.println(RED + "\t Enter ( 0 ) to discard" + RESET);
        System.out.print("\t Enter name: ");
        String userName = InputUtils.getStringInput();
        if (userName.equals("0")) {
            userAccess();
        }
        System.out.print("\t Enter password: ");
        String userPassword = InputUtils.getStringInput();
        if (userPassword.equals("0")) {
            userAccess();
        }
        ArrayList<String> userReg = new ArrayList<>();
        userReg.add(userName);
        userReg.add(userPassword);
        System.out.println("\t registering..............");
        userRegistration(userReg); // go for registration
    }

    public static void userRegistration(ArrayList<String> userData) {

        boolean regUser = Warehouse.clientRegister(userData.getFirst(), userData.get(1));
        if (regUser) {

            System.out.println("\t ======= ");
            userAccess();
            countLoginUser = 0;
        } else {
            countLoginUser++;
            System.out.println("\t trial " + countLoginUser);

            if (countLoginUser >= 3) {
                System.out.println("\t 3 trials done system exit");
                System.exit(0);
                InputUtils.closeScanner();
            } else {
                userMenuRegistrationManu();
            }
        }

    }
    //**************************************************


    // USER LOGIN MENU
    public static void userMenuLogin() {

        System.out.println("\t CLIENT LOGIN ");
        System.out.println("\t ========== ");
        System.out.println(RED + "\t Enter ( 0 ) to discard" + RESET);
        System.out.print("\t Enter name: ");
        String userName = InputUtils.getStringInput();
        if (userName.equals("0")) {
            userAccess();
        }
        System.out.print("\t Enter password: ");
        String userPassword = InputUtils.getStringInput();
        if (userPassword.equals("0")) {
            userAccess();
        }
        userLogin(userName, userPassword);

    }

    public static void userLogin(String name, String password) {

        boolean isUser = Warehouse.clientLogin(name, password);
        if (isUser) {
            System.out.println("\t ======= ");
            userMenu();
            countLoginUser = 0;
        } else {
            countLoginUser++;
            System.out.println("\t trial " + countLoginUser);
            if (countLoginUser >= 3) {
                System.out.println("\t 3 trials done create another account");
                System.exit(0);
                InputUtils.closeScanner();
            } else {
                userMenuLogin();
            }
        }

    }
}
//// **************************************************
//
//
//
//
//
