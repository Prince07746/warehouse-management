package DBSystem;
import warehouse.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ColorsStyle.Colors;

public class CartSystem {
    public static UserConnection userSystemCart = new UserConnection();
    public static Path CARTFile;
    public static Path ORDERFile;

    static {
        CARTFile = Paths.get("src", "FileDBSystem", "CART.txt").toAbsolutePath();
    }

    static {
        ORDERFile = Paths.get("src", "FileDBSystem", "ORDERS.txt").toAbsolutePath();
    }

    protected static ArrayList<CartItemTemplate> cartList = new ArrayList<>();
    protected static ArrayList<CartItemTemplate> orderList = new ArrayList<>();

    public CartSystem() {
        try (FileReader reader = new FileReader(CARTFile.toString())) {
            Scanner input = new Scanner(reader);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplited = line.split(",");
                // if there is a miss column in the lineSplited the system will exit
                if (lineSplited.length != 11) {
                    System.out.println(Colors.YELLOW + "The system will close in 5 sec because of a miss column in " + CARTFile + Colors.RESET);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println(e.toString());
                    }
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    public void cartLoader() {
        cartList.clear(); // Clear the existing cart list
        try (FileReader reader = new FileReader(CARTFile.toString());
             Scanner input = new Scanner(reader)) {

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplited = line.split(",");

                // Assuming CartItem constructor matches the fields in the file
                cartList.add(new CartItemTemplate(
                        lineSplited[0], // cartId
                        lineSplited[1], // userName
                        lineSplited[2], // quantity
                        lineSplited[3], // type
                        lineSplited[4], // purchasePrice
                        lineSplited[5], // manufacturer
                        lineSplited[6], // model
                        lineSplited[7], // description
                        lineSplited[8], // displaySize
                        lineSplited[9], // storageSize
                        lineSplited[10] // sellingPrice
                ));
            }

            // Check if the cart list is empty
//            if (cartList.isEmpty()) {
//                System.out.println("\t No items in the cart.");
//            }
        } catch (IOException e) {
            System.out.println("\t Error loading cart data: " + e.getMessage());
        }
    }

    public void orderLoader() {
        orderList.clear(); // Clear the existing cart list
        try (FileReader reader = new FileReader(ORDERFile.toString());
             Scanner input = new Scanner(reader)) {

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplited = line.split(",");

                // Assuming CartItem constructor matches the fields in the file orders.txt
                orderList.add(new CartItemTemplate(
                        lineSplited[0], // cartId
                        lineSplited[1], // userName
                        lineSplited[2], // quantity
                        lineSplited[3], // type
                        lineSplited[4], // purchasePrice
                        lineSplited[5], // manufacturer
                        lineSplited[6], // model
                        lineSplited[7], // description
                        lineSplited[8], // displaySize
                        lineSplited[9], // storageSize
                        lineSplited[10] // sellingPrice
                ));
            }

            // Check if the order list is empty
//            if (cartList.isEmpty()) {
//                System.out.println("\t No items in the cart.");
//            }
        } catch (IOException e) {
            System.out.println("\t Error loading cart data: " + e.getMessage());
        }
    }

    public void getCartItems(User user) {
        try (FileReader reader = new FileReader(CARTFile.toString())) {
            boolean checker = false;
            Scanner reading = new Scanner(reader);
            int index = 1;
            double totalCart = 0.0;
            while (reading.hasNextLine()) {
                String line = reading.nextLine();
                String[] splitedLine = line.split(",");
                if (splitedLine[1].equals(user.getName())) {
                    checker = true;
                    System.out.println(Colors.YELLOW + "\t[" + index + "] Cart ID: " + Colors.RED + splitedLine[0] + Colors.RESET +
                            Colors.YELLOW + "| Quantity: " + Colors.RED + splitedLine[2] + Colors.RESET +
                            Colors.YELLOW + "| Type: " + Colors.RED + splitedLine[3] + Colors.RESET +
                            Colors.YELLOW + "| Manufacturer: " + Colors.RED + splitedLine[5] + Colors.RESET +
                            Colors.YELLOW + "| Model: " + Colors.RED + splitedLine[6] + Colors.RESET +
                            Colors.YELLOW + "\n\t| Description: " + Colors.RED + splitedLine[7] + Colors.RESET +
                            Colors.YELLOW + "\n\t| Display Size: " + Colors.RED + splitedLine[8] + Colors.RESET +
                            Colors.YELLOW + "| Storage Size: " + Colors.RED + splitedLine[9] + Colors.RESET +
                            Colors.YELLOW + "| Selling Price: " + Colors.RED + splitedLine[10] + Colors.RESET +
                            Colors.YELLOW + "| Total price Item: " + Colors.RED + (Double.parseDouble(splitedLine[10]) * Integer.parseInt(splitedLine[2])) + Colors.RESET +
                            " \n\t" +
                            "------------------------------------------------------------------------------------" + Colors.RESET);

                    totalCart += Double.parseDouble(splitedLine[10]) * Integer.parseInt(splitedLine[2]);
                    index++;

                }
            }
            System.out.println(Colors.BLUE + "\t Grand Total: " + totalCart + Colors.RESET);
            if (!checker) {
                System.out.println(Colors.RED + "\t No items in the cart." + Colors.RESET);
            }
            reading.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addItemToCart(User user, String itemId, int quantity) {
        StringBuilder formater = new StringBuilder();
        cartLoader(); // Load current cart items
        try (FileReader reader = new FileReader(CARTFile.toString())) {
            boolean checker = false;
            Scanner reading = new Scanner(reader);
            while (reading.hasNextLine()) {
                String line = reading.nextLine();
                String[] splitedLine = line.split(",");

                // Check if this item is already in the user's cart
                if (itemId.equals(splitedLine[0]) && user.getName().equals(splitedLine[1])) {
                    checker = true;
                    System.out.println("\t This item is already in your cart. If you want to modify this item, confirm:");
                    System.out.println("\t 1. YES");
                    System.out.println("\t 2. NO");
                    System.out.print("\t Enter: ");
                    String option = InputUtils.getStringInput();
                    switch (option) {
                        case "1":
                            System.out.println("\t || MODIFYING THE QUANTITY OF THE ITEM || ");
                            System.out.print("\t New Quantity: ");
                            int newQuantity = InputUtils.getIntInput();
                            modifyItemQuantity(user, itemId, newQuantity); // Pass itemId instead of Item object
                            break;
                        case "2":
                            break;
                        default:
                            System.out.println("\t You entered a wrong number");
                            addItemToCart(user, itemId, quantity);
                            break;
                    }
                }
            }
            reading.close();
            if (checker) {
                System.out.println("\n If you want to add an item, check well on its ID index.");
            } else {
                // Write new item to cart
                try (FileWriter writer = new FileWriter(CARTFile.toString())) {
                    formater.delete(0, formater.length());
                    ProductSystem product = new ProductSystem();
                    Product product1 = product.getProductById(itemId);

                    // Append existing cart items to formater
                    for (CartItemTemplate item2 : cartList) {
                        formater.append(item2.getCartId()).append(",")
                                .append(item2.getUserName()).append(",")
                                .append(item2.getQuantity()).append(",")
                                .append(item2.getType()).append(",")
                                .append(item2.getPurchasePrice()).append(",")
                                .append(item2.getManufacturer()).append(",")
                                .append(item2.getModel()).append(",")
                                .append(item2.getDescription()).append(",")
                                .append(item2.getDisplaySize()).append(",")
                                .append(item2.getStorageSize()).append(",")
                                .append(item2.getSellingPrice()).append("\n");
                    }

                    if (product1 != null) {
                        if (quantity <= Integer.parseInt(product1.getQuantityStock())) {
                            formater.append(itemId).append(",")
                                    .append(user.getName()).append(",")
                                    .append(quantity).append(",")
                                    .append(product1.getType()).append(",")
                                    .append(product1.getPurchasePrice()).append(",")
                                    .append(product1.getManufacturer()).append(",")
                                    .append(product1.getModel()).append(",")
                                    .append(product1.getDescription()).append(",")
                                    .append(product1.getDisplaySize()).append(",")
                                    .append(product1.getStorageSize()).append(",")
                                    .append(product1.getSellingPrice()).append("\n");

                            writer.write(formater.toString());
                            System.out.println("\t Item added to the cart for Client [" + user.getName() + "] with item ID [" + itemId + "].");
                            writer.close();
                            formater.delete(0, formater.length());
                            cartList.clear();
                        } else {
                            System.out.println("\t The quantity you are adding exceeds stock available: " + Colors.GREEN + " [ " + product1.getQuantityStock() + " ] or [ 0 ]" + Colors.RESET);
                        }
                    } else {
                        System.out.println("\t This product is not found in the warehouse. It's not from our warehouse. Manager report.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(Colors.YELLOW + "\t Error in the system: problem while reading cart data." + Colors.RESET);
            System.out.print(Colors.GREEN + " [ CartSystem/addItemToCart ]" + Colors.RESET + "\n");
        }
    }

    public void modifyItemQuantity(User user, String itemId, int newQuantity) {
        ProductSystem product = new ProductSystem();
        Product product1 = product.getProductById(itemId);

        StringBuilder updatedCart = new StringBuilder();  // Holds the modified cart data
        boolean itemFound = false;  // Tracks if the item was found

        try (FileReader reader = new FileReader(CARTFile.toString())) {
            Scanner reading = new Scanner(reader);

            while (reading.hasNextLine()) {
                String line = reading.nextLine();
                String[] splitedLine = line.split(",");

                // Check if this is the item to modify based on itemId and userName
                if (itemId.equals(splitedLine[0]) && user.getName().equals(splitedLine[1])) {
                    itemFound = true;

                    // Update only the quantity field
                    if (product1 != null) {
                        if (newQuantity <= Integer.parseInt(product1.getQuantityStock()) && newQuantity > 0) {
                            splitedLine[2] = String.valueOf(newQuantity);
                            System.out.println("\t Quantity for item ID [" + itemId + "] updated to: " + newQuantity);
                        } else {
                            System.out.println("\t The quantity you are adding exceeds stock available: " + Colors.GREEN + " [ " + product1.getQuantityStock() + " ] or [ 0 ]" + Colors.RESET);
                            System.out.println("\t || MODIFYING THE QUANTITY OF THE ITEM || ");
                            System.out.print("\t New Quantity: ");
                            int retryQuantity = InputUtils.getIntInput();
                            while (retryQuantity >= Integer.parseInt(product1.getQuantityStock()) && retryQuantity > 0) {
                                System.out.println("\t The quantity you are adding exceeds stock available: " + Colors.GREEN + " [ " + product1.getQuantityStock() + " ] or [ 0 ]" + Colors.RESET);
                                System.out.println("\t || MODIFYING THE QUANTITY OF THE ITEM || ");
                                System.out.print("\t New Quantity: ");
                                retryQuantity = InputUtils.getIntInput();
                            }
                            splitedLine[2] = String.valueOf(retryQuantity);
                            System.out.println("\t Quantity for item ID [" + itemId + "] updated to: " + retryQuantity);
                        }
                    }
                }

                // Append the (possibly modified) line back to the updatedCart
                updatedCart.append(String.join(",", splitedLine)).append("\n");
            }

            reading.close();
            if (!itemFound) {
                System.out.println("\t Item not found in the cart for client [" + user.getName() + "] with item ID [" + itemId + "].");
            }

            // Write the updated cart data back to the file
            try (FileWriter writer = new FileWriter(CARTFile.toString())) {  // Overwrite the file
                writer.write(updatedCart.toString());
                System.out.println(Colors.GREEN + "\t Item modified successfully." + Colors.RESET);
            }
        } catch (IOException e) {
            System.out.println(Colors.YELLOW + "\t Error in the system: problem while reading/writing cart data." + Colors.RESET);
            System.out.println(e.toString());
        }
    }

    public void deleteItemFromCart(User user, String itemId) {
        StringBuilder updatedCart = new StringBuilder();  // Holds the modified cart data
        boolean itemFound = false;  // Tracks if the item was found

        cartLoader();  // Load existing cart items into memory

        try (FileReader reader = new FileReader(CARTFile.toString())) {
            Scanner reading = new Scanner(reader);
            while (reading.hasNextLine()) {
                String line = reading.nextLine();
                String[] splitedLine = line.split(",");
                // Check if the line corresponds to the item to be deleted based on itemId and userName
                if (itemId.equals(splitedLine[0]) && user.getName().equals(splitedLine[1])) {
                    itemFound = true;
                    System.out.println(Colors.GREEN + "\t Item with ID [" + itemId + "] has been deleted from the cart for client [" + user.getName() + "]." + Colors.RESET);
                    continue;  // Skip this line (item won't be included in the updated cart)
                }
                // Append the non-deleted lines back to the updatedCart
                updatedCart.append(String.join(",", splitedLine)).append("\n");
            }
            reading.close();
            if (!itemFound) {
                System.out.println(Colors.RED + "\t Item not found in the cart for client [" + user.getName() + "] with item ID [" + itemId + "]." + Colors.RESET);
            } else {
                // Write the updated cart data back to the file, excluding the deleted item
                try (FileWriter writer = new FileWriter(CARTFile.toString())) {
                    System.out.println();
                    writer.write(updatedCart.toString());
                    writer.close();
                    System.out.println("\t Cart updated successfully after deletion.");
                    cartList.clear();
                    updatedCart.delete(0, updatedCart.length());
                }
            }
        } catch (IOException e) {
            System.out.println(Colors.YELLOW + "\t Error in the system: problem while reading/writing cart data." + Colors.RESET);
            System.out.println(e.toString());
        }
    }

    public void finalisePurchase(User user, double account, String itemId, String userID) {
        StringBuilder updatedCart = new StringBuilder();  // Holds the modified cart data
        boolean itemFound = false;  // Tracks if the item was found
        boolean balanceChecker = false;
        cartLoader();  // Load existing cart items into memory

        try (FileReader reader = new FileReader(CARTFile.toString())) {
            Scanner reading = new Scanner(reader);
            while (reading.hasNextLine()) {
                String line = reading.nextLine();
                String[] splitedLine = line.split(",");
                // Check if the line corresponds to the item to be deleted based on itemId and userName
                if (itemId.equals(splitedLine[0]) && user.getName().equals(splitedLine[1]) && (account - (Double.parseDouble(splitedLine[10]) * Integer.parseInt(splitedLine[2]))) >= 0) {
                    itemFound = true;
                    balanceChecker = true;
                    System.out.println(Colors.GREEN + "\t Item with ID [" + itemId + "] successfully bought using "+ Colors.RESET);
                    userSystemCart.updateUserAccountAfterPurchase(userID, account - (Double.parseDouble(splitedLine[10]) * Integer.parseInt(splitedLine[2])));
                    // update the income of the warehouse after purchase
                    userSystemCart.wareHouseIncome((Double.parseDouble(splitedLine[10]) * Integer.parseInt(splitedLine[2])));
                    // add items to the orders
                    addToOrders(new CartItemTemplate(itemId, user.getName(), splitedLine[2], splitedLine[3], splitedLine[4], splitedLine[5], splitedLine[6], splitedLine[7], splitedLine[8], splitedLine[9], splitedLine[10]));

                    continue;  // Skip this line (item won't be included in the updated cart)
                }
                // Append the non-deleted lines back to the updatedCart
                updatedCart.append(String.join(",", splitedLine)).append("\n");
            }
            reading.close();
            if (!itemFound) {
                if (balanceChecker == false) {
                    System.out.println(Colors.RED + "\t not enough balance " + Colors.RESET);
                } else {
                    System.out.println(Colors.RED + "\t Item not found in the cart for client [" + user.getName() + "] with item ID [" + itemId + "]." + Colors.RESET);
                }
            } else {
                // Write the updated cart data back to the file, excluding the deleted item
                try (FileWriter writer = new FileWriter(CARTFile.toString())) {
                    System.out.println();
                    writer.write(updatedCart.toString());
                    writer.close();
                    System.out.println("\t Cart updated successfully after purchase.");
                    cartList.clear();
                    updatedCart.delete(0, updatedCart.length());
                }
            }
        } catch (IOException e) {
            System.out.println(Colors.YELLOW + "\t Error in the system: problem while reading/writing cart data " +
                    "purchase area" +
                    "." + Colors.RESET);
            System.out.println(e.toString());
        }
    }


    public void finalisePurchaseAll(User user, double account, String userID) {
        StringBuilder updatedCart = new StringBuilder();  // Holds the modified cart data
        boolean itemFound = false;  // Tracks if the item was found
        boolean balanceChecker = false;
        double totalCart = 0;
        cartLoader();  // Load existing cart items into memory

        List<String[]> cartItemsToAddToOrders = new ArrayList<>(); // Store items to add to orders

        try (FileReader reader = new FileReader(CARTFile.toString())) {
            Scanner reading = new Scanner(reader);
            while (reading.hasNextLine()) {
                String line = reading.nextLine();
                String[] splitedLine = line.split(",");
                // Check if the line corresponds to the item in the cart for this user
                if (user.getName().equals(splitedLine[1])) {
                    itemFound = true;
                    totalCart += Double.parseDouble(splitedLine[10]) * Integer.parseInt(splitedLine[2]);  // Calculate total for all items
                    cartItemsToAddToOrders.add(splitedLine);  // Collect item details for orders
                    continue;  // Skip this line (item won't be included in the updated cart)
                }
                // Append the non-deleted lines back to the updatedCart
                updatedCart.append(String.join(",", splitedLine)).append("\n");
            }
            reading.close();
            balanceChecker = (account - totalCart) >= 0;

            if (!itemFound) {
                // Check if the issue is with the balance or no items in the cart
                if (!balanceChecker) {
                    System.out.println(Colors.RED + "\t Not enough balance " + Colors.RESET);
                } else {
                    System.out.println(Colors.RED + "\t No items found in the cart for client [" + user.getName() + "]." + Colors.RESET);
                }
            } else {
                if (balanceChecker) {
                    // Write the updated cart data back to the file
                    try (FileWriter writer = new FileWriter(CARTFile.toString())) {
                        System.out.println();
                        writer.write(updatedCart.toString());
                        writer.close();
                        userSystemCart.updateUserAccountAfterPurchase(userID, account - totalCart);
                        System.out.println("\t Cart updated successfully after purchase.");
                        // update the income of the warehouse after purchase
                        userSystemCart.wareHouseIncome(totalCart);

                        // Add all the items to orders
                        for (String[] cartItem : cartItemsToAddToOrders) {
                            addToOrders(new CartItemTemplate(cartItem[0], user.getName(), cartItem[2], cartItem[3], cartItem[4],
                                    cartItem[5], cartItem[6], cartItem[7], cartItem[8], cartItem[9], cartItem[10]));
                        }

                        cartList.clear();
                        updatedCart.delete(0, updatedCart.length());
                    }
                } else {
                    System.out.println("\t Not enough balance to purchase all the items in your cart. Grand total: " +
                            Colors.BLUE + " [ " + totalCart + " ] " + Colors.RESET);
                }
            }
        } catch (IOException e) {
            System.out.println(Colors.YELLOW + "\t Error in the system: problem while reading/writing cart data in the purchase area." + Colors.RESET);
            System.out.println(e.toString());
        }
    }



    public void addToOrders(CartItemTemplate item) {
        orderList.clear();
        orderLoader();
        StringBuilder formater = new StringBuilder();
        // Append existing orders items to formater
        for (CartItemTemplate item2 : orderList) {
            formater.append(item2.getCartId()).append(",")
                    .append(item2.getUserName()).append(",")
                    .append(item2.getQuantity()).append(",")
                    .append(item2.getType()).append(",")
                    .append(item2.getPurchasePrice()).append(",")
                    .append(item2.getManufacturer()).append(",")
                    .append(item2.getModel()).append(",")
                    .append(item2.getDescription()).append(",")
                    .append(item2.getDisplaySize()).append(",")
                    .append(item2.getStorageSize()).append(",")
                    .append(item2.getSellingPrice()).append("\n");
        }
        formater.append(item.getCartId()).append(",")
                .append(item.getUserName()).append(",")
                .append(item.getQuantity()).append(",")
                .append(item.getType()).append(",")
                .append(item.getPurchasePrice()).append(",")
                .append(item.getManufacturer()).append(",")
                .append(item.getModel()).append(",")
                .append(item.getDescription()).append(",")
                .append(item.getDisplaySize()).append(",")
                .append(item.getStorageSize()).append(",")
                .append(item.getSellingPrice()).append("\n");

        try (FileWriter writer = new FileWriter(ORDERFile.toString())) {
            writer.write(formater.toString());
            writer.close();
            orderList.clear();
        } catch (IOException e) {
            System.out.println("\t error reading the file " + e.getMessage());
        }
    }

    public double getAveragePurchasePrice(String username) {
        orderList.clear();
        orderLoader();
        double totalPrice = 0;
        double totalItems = 0;
        for (CartItemTemplate item : orderList) {
            if (item.getUserName().equals(username)) {
                totalPrice += Double.parseDouble(item.getSellingPrice())* Integer.parseInt(item.getQuantity());
                totalItems++;
            }
        }
        return totalPrice / totalItems;
    }

    public void viewHistoryPurchase(String username) {
        orderList.clear();
        orderLoader();
        int index = 1;
        double totalPurchase = 0;
        for (CartItemTemplate item : orderList) {
            if (item.getUserName().equals(username)) {
                System.out.println("\t[ " + index + " ]" + item.toString());
                totalPurchase += Integer.parseInt(item.getQuantity()) * Double.parseDouble(item.getSellingPrice());
                index++;
            }
        }
        System.out.println("\t Grand total Order List: " + Colors.BLUE + " [ " + totalPurchase + " ] " + Colors.RESET);
    }


    public double[] getAllAverage(){
        orderList.clear();
        ProductSystem.productList.clear();

        ProductSystem.loadProducts();
        orderLoader();
        double[] averages = new double[3];

        // get the average price of purchase price of the warehouse
        double totalPurchase = 0;
        for(Product product:ProductSystem.productList){
            totalPurchase += Double.parseDouble(product.getPurchasePrice())*Double.parseDouble(product.getQuantityStock());
        }
        double averagePurchaseWarehouse = totalPurchase/ProductSystem.productList.size();
        averages[0] = (double) Math.round(averagePurchaseWarehouse * 100) /100;

        double totalSelling = 0;
        for(Product product:ProductSystem.productList){
            totalSelling += Double.parseDouble(product.getSellingPrice());
        }
        double averageSellingOfTheWarehouse = totalSelling/ProductSystem.productList.size();
        averages[1] = (double) Math.round(averageSellingOfTheWarehouse*100)/100;

        double totalOderPurchase = 0;
        for(CartItemTemplate product:orderList){
            totalOderPurchase += Double.parseDouble(product.getSellingPrice())*Double.parseDouble(product.getQuantity());
        }
        double avereOrderPurchase = totalOderPurchase/orderList.size();
        averages[2] = (double) Math.round(avereOrderPurchase*100)/100;

        orderList.clear();
        ProductSystem.productList.clear();

        return averages;
    }

    public void getAllOrders(){
        orderList.clear();
        orderLoader();
        int index = 1;
        for(CartItemTemplate order:orderList){
            System.out.println("\t ["+index+"]"+order.getInfo());
            index++;
        }
    }

    public void getAveragePerUser(){
       // orderList.clear();
       // orderLoader();
    }

    public ArrayList<CartItemTemplate> getHistoryPurchase(String username) {
        orderList.clear();
        orderLoader();
        return orderList;
    }


}





    class CartItemTemplate {
        private String cartId;
        private String userName;
        private String quantity;
        private String type;
        private String purchasePrice;
        private String manufacturer;
        private String model;
        private String description;
        private String displaySize;
        private String storageSize;
        private String sellingPrice;
        private String quantityStock;

        public CartItemTemplate(String cartId, String userName, String quantity, String type,
                                String purchasePrice, String manufacturer, String model,
                                String description, String displaySize, String storageSize,
                                String sellingPrice) {
            this.cartId = cartId;
            this.userName = userName;
            this.quantity = quantity;
            this.type = type;
            this.purchasePrice = purchasePrice;
            this.manufacturer = manufacturer;
            this.model = model;
            this.description = description;
            this.displaySize = displaySize;
            this.storageSize = storageSize;
            this.sellingPrice = sellingPrice;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(String purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDisplaySize() {
            return displaySize;
        }

        public void setDisplaySize(String displaySize) {
            this.displaySize = displaySize;
        }

        public String getStorageSize() {
            return storageSize;
        }

        public void setStorageSize(String storageSize) {
            this.storageSize = storageSize;
        }

        public String getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(String sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public String getInfo(){
            return
                            Colors.GREEN + " [ UserName: " + Colors.RED + userName + Colors.RESET +
                            Colors.GREEN + "| ID: " + Colors.RED + cartId + Colors.RESET +
                            Colors.GREEN + "| Type: " + Colors.RED + type + Colors.RESET +
                            Colors.GREEN + "| Price: " + Colors.RED + purchasePrice + Colors.RESET +
                            Colors.GREEN + "| Manufacturer: " + Colors.RED + manufacturer + Colors.RESET +
                            Colors.GREEN + "| Model: " + Colors.RED + model + Colors.RESET +
                            Colors.GREEN + "\n\t| Description: " + Colors.RED + description + Colors.RESET +
                            Colors.GREEN + "\n\t| Display Size: " + Colors.RED + displaySize + Colors.RESET +
                            Colors.GREEN + "| Storage Size: " + Colors.RED + storageSize + Colors.RESET +
                            Colors.GREEN + "| Selling Price: " + Colors.RED + sellingPrice + Colors.RESET +
                            Colors.GREEN + "| quantity: " + Colors.RED + quantity + Colors.RESET +
                            " \n\t ============================================================================";
        }

        @Override
        public String toString() {
            return
                            Colors.GREEN + " [ ID: " + Colors.RED + cartId + Colors.RESET +
                            Colors.GREEN + "| Type: " + Colors.RED + type + Colors.RESET +
                            Colors.GREEN + "| Price: " + Colors.RED + purchasePrice + Colors.RESET +
                            Colors.GREEN + "| Manufacturer: " + Colors.RED + manufacturer + Colors.RESET +
                            Colors.GREEN + "| Model: " + Colors.RED + model + Colors.RESET +
                            Colors.GREEN + "\n\t| Description: " + Colors.RED + description + Colors.RESET +
                            Colors.GREEN + "\n\t| Display Size: " + Colors.RED + displaySize + Colors.RESET +
                            Colors.GREEN + "| Storage Size: " + Colors.RED + storageSize + Colors.RESET +
                            Colors.GREEN + "| Selling Price: " + Colors.RED + sellingPrice + Colors.RESET +
                            Colors.GREEN + "| quantity: " + Colors.RED + quantity + Colors.RESET +
                            " \n\t =============================================================================";
        }
    }



