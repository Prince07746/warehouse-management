package warehouse;
import ColorsStyle.Colors;
import DBSystem.*;



public class Warehouse {


    static Storage storage = new Storage();
    static UserConnection userConnection = new UserConnection();
    static CartSystem cart = new CartSystem();
    static ProductSystem productSystem = new ProductSystem();
    static Cart cartProcess = new Cart();


    // login treasurer
    public static boolean clientLogin(String name,String password){
      return userConnection.loginUser(name,password,"CLIENT");
    }
    // login manager
    public static boolean managerLogin(String name,String password){
      return userConnection.loginUser(name,password,"MANAGER");
    }
    // register client
    public static boolean clientRegister(String name,String password){
        User user = new Client(name, password);
        return userConnection.registerUser(user);
    }
    // register manager
    public static boolean manageRegister(String name,String password){
        User user = new Manager(name, password);
        return userConnection.registerUser(user);
    }
    // register treasurer
    public static boolean treasurerRegister(String name, String password) {
        User user = new Treasurer(name, password);
        return userConnection.registerUser(user);
    }
    // logout user
    public static void logoutUser(String name, String password) {
        userConnection.logoutUser(name, password);
    }
    // get connected users
    public static String[] getConnectedUsers() {
        return UserConnection.getUserConnected();
    }
    // update user balance
    public static boolean updateUserBalance(double newBalance) {
        String userId = getConnectedUsers()[2];
        return userConnection.updateUserAccount(userId, newBalance);
    }
    // update item in warehouse
    public static void addToWarehouseItem() {

        System.out.println("\t ==============================");
        System.out.println("\t ||"+Menu.GREEN+"ENTER DEVICE INFORMATION"+Menu.RESET+"||");
        System.out.println("\t ==============================");
        System.out.println(Menu.RED + "\t Enter ( 0 ) to discard" + Menu.RESET);
        System.out.println("\t..........................");
        System.out.println("\t Enter device");
        System.out.println("\t 1. tablet");
        System.out.println("\t 2. smartphone");
        System.out.println("\t 3. notebook");

        System.out.print("\t Enter type: ");
        String type = InputUtils.getStringInput();
        if (type.equals("0")) {
            Menu.managementMenu();
        } else if (type.equals("1")) {
            type = "tablet";
        } else if (type.equals("2")) {
            type = "smartphone";
        } else if (type.equals("3")) {
            type = "notebook";
        }

        System.out.print("\t Enter manufacturer: ");
        String manufacturer = InputUtils.getStringInput();
        if (manufacturer.equals("0")) {
            Menu.managementMenu();
        }

        System.out.print("\t Enter model: ");
        String model = InputUtils.getStringInput();
        if (model.equals("0")) {
            Menu.managementMenu();
        }

        System.out.print("\t Enter description (optional): ");
        String description = InputUtils.getStringInput();
        if (description.equals("0")) {
            Menu.managementMenu();
        }

        System.out.print("\t Enter display size: ");
        String displaySize = InputUtils.getStringInput();
        if (displaySize.equals("0")) {
            Menu.managementMenu();
        }

        System.out.print("\t Enter storage size: ");
        String storageSize = InputUtils.getStringInput();
        if (storageSize.equals("0")) {
            Menu.managementMenu();
        }

        System.out.print("\t Enter purchase price: ");
        String purchasePrice = InputUtils.getStringInput();
        if (purchasePrice.equals("0")) {
            Menu.managementMenu();
        }

        System.out.print("\t Enter selling price: ");
        String sellingPrice = InputUtils.getStringInput();
        if (sellingPrice.equals("0")) {
            Menu.managementMenu();
        }

        System.out.print("\t Enter quantity: ");
        String quantity = InputUtils.getStringInput();
        if(quantity.equals("0")){
            Menu.managementMenu();
        }

        System.out.print("\t Enter device ID: ");
        String deviceId = InputUtils.getStringInput(); // Consume newline
        if (deviceId.equals("0")) {
            Menu.managementMenu();
        }

        Item newItem = null;
        switch (type){
            case "tablet":
                newItem = new Tablet(purchasePrice,manufacturer,model,description,displaySize,storageSize, sellingPrice,quantity);

                break;
            case "smartphone":
                newItem = new Smartphone(purchasePrice,manufacturer,model,description,displaySize,storageSize, sellingPrice,quantity);
                break;
            case "notebook":
                newItem = new Notebook(purchasePrice,manufacturer,model,description,displaySize,storageSize, sellingPrice,quantity);
                break;
            default:
                break;
        }

        assert newItem != null;
        boolean store = storingItem(newItem);

        if (store) {

            System.out.println(" ");
            System.out.println("\t Device added successfully..............\n");
            System.out.println(newItem.toString());

        } else {

            System.out.println("\n\t some of the required details of the device missing try again !! \n");
            addToWarehouseItem();
        }

    }
    // update item in warehouse
    public static boolean updatingDevice(String deviceId) {
       storage.updateItem(deviceId);
       return true;
    }
    // add item to warehouse
    public static boolean storingItem(Item item){
        storage.addItem(item);
        return true;
    }
    // add item to cart
    public static void addToCartUser(String id){
        System.out.print("\t enter quantity: ");
        int quantity = InputUtils.getIntInput();

        String userName = getConnectedUsers()[0];
        String userPassword = getConnectedUsers()[1];
        User user = new Client(userName, userPassword);
        cartProcess.addItemToCart(id,user,quantity);

    }
    // get all items in the cart
    public static void getUserCartItems(){

        String userName = getConnectedUsers()[0];
        String userPassword = getConnectedUsers()[1];
        User user = new Client(userName, userPassword);
        cart.getCartItems(user);
    }
    // remove item from the cart
    public static boolean removeItemFromUserCart(String id){
        String userName = getConnectedUsers()[0];
        String userPassword = getConnectedUsers()[1];
        User user = new Client(userName, userPassword);
        cart.deleteItemFromCart(user,id);
      return true;
    }
    // finalize purchase
    public static void finalizePurchase(String itemId){
        String userName = getConnectedUsers()[0];
        String userPassword = getConnectedUsers()[1];
        User user = new Client(userName, userPassword);
        double account = Double.parseDouble(getConnectedUsers()[4]);
        cart.finalisePurchase(user, account, itemId,getConnectedUsers()[2]);

    }
    // finalize purchase
    public static void finalizePurchaseAll(){
        String userName = getConnectedUsers()[0];
        String userPassword = getConnectedUsers()[1];
        User user = new Client(userName, userPassword);
        double account = Double.parseDouble(getConnectedUsers()[4]);
        cart.finalisePurchaseAll(user, account, getConnectedUsers()[2]);

    }
    // get all product for the user
    public static void getAllProductUser(){
        storage.displayAllItemsUser();
    }
    // get all product for the manager
    public static void getAllProductManager(){
        storage.displayAllItemsManager();
    }
    // delete a product from the warehouse
    public static void deleteProduct(String id){
        storage.removedItem(id);
    }
    // Method to handle the search options
    public static void handleSearchOption(String searchOption) {
        storage.searchProduct(searchOption);
    }
    // view the average of all purchases
    public static double getAveragePurchase(){
        return cart.getAveragePurchasePrice(getConnectedUsers()[0]);
    }
    // calculate average purchase price,selling price,user total purchase price  in the warehouse
    public static void getAllAverage(){
        double[] averages = cart.getAllAverage();
        System.out.println("\t Warehouse Purchase Price Average: "+Colors.BLUE+averages[0]+Colors.RESET);
        System.out.println("\t Warehouse Selling Price Average: "+Colors.GREEN+averages[1]+Colors.RESET);
        System.out.println("\t Client Purchase Average: "+Colors.YELLOW+averages[2]+Colors.RESET);
    }
    // views sales history
    public static void viewSalesHistory(){
      cart.getAllOrders();
    }
    // view the history about all the orders for the client
    public static void viewHistoryOrder() {
        cart.viewHistoryPurchase(getConnectedUsers()[0]);
    }
    // get infor about the warehouse
    public static void infoWareHouse(){
        userConnection.wareHouseExpense();
    }

}











class Cart{
    static CartSystem cartSystem = new CartSystem();
    public Cart(){
    }
    // add item to cart
    public void addItemToCart(String itemId,User user,int quantity){
          cartSystem.addItemToCart(user,itemId,quantity);
    }
}


class Storage{
    static ProductSystem store = new ProductSystem();

    public Storage(){

    }
    // get all items in the cart
    public void addItem(Item item){
        if(store.getProduct(item.getId())){
            System.out.println("\t There is an item already with this ID: ");
            System.out.println("\t "+Menu.RED+item.getId()+Menu.RESET);
            System.out.println("\t =======================================");
        }else{
            store.saveProducts(item);
        }
    }
    // search for item in the cart
    public boolean updateItem(String id){
            store.modifyProductById(id);
            return true;
    }
    // remove item from the cart
    public void removedItem(String id){
        if(store.getProduct(id)){
            System.out.println(Colors.BLUE+"\t Are you sure to delete this product"+Colors.RESET);
            System.out.println("\t 1. YES");
            System.out.println("\t 2. NO");
            System.out.print("\t Enter: ");
            String option = InputUtils.getStringInput();
            if(option.equals("1")){
                store.deleteProductById(id);
            }
            else if(option.equals("2")){
                System.out.println("\t Process aborted !");
            }

        }else{
            System.out.println(Colors.RED+"\t This ID is not matching any item in the warehouse"+Colors.RESET);
        }
    }
    // get all items in the cart
    public void displayAllItemsManager() {
        System.out.println("\n\t All Items in the Store:");
        store.getAllProductManager();
    }
    // get all items in the cart
    public void displayAllItemsUser() {
        store.getAllProductUser();

    }
    // search for item in the cart
    public static void searchProduct(String searchOption) {
        if (searchOption.equals("1")) {
            store.searchProduct(ProductValues.TYPE);
        } else if (searchOption.equals("2")) {
            store.searchProduct(ProductValues.MANUFACTURER);
        } else if (searchOption.equals("3")) {
            store.searchProduct(ProductValues.MODEL);
        } else if (searchOption.equals("4")) {
            store.searchProduct(ProductValues.SELLING_PRICE);
        } else if (searchOption.equals("5")) {
            store.searchProduct(ProductValues.RANGE_PRICE);
        }
    }

    @Override
    public String toString() {
        return "\t Storage" +
                "\n\t ========\n" +"\n"+store;
    }
}
