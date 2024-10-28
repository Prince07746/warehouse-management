package DBSystem;

import ColorsStyle.Colors;
import warehouse.InputUtils;
import warehouse.Item;
import warehouse.Warehouse;
import warehouse.*;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ProductSystem {
    // ANSI escape codes for text colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    private static Path PRODUCT_FILE = Paths.get("src", "FileDBSystem", "PRODUCT.txt").toAbsolutePath();
    public static List<Product> productList = new ArrayList<>();
    private StringBuilder formatter = new StringBuilder();
    public static HashMap<Integer,String> productsID = new HashMap<>();

    public ProductSystem(){
        try (FileReader reader = new FileReader(PRODUCT_FILE.toString())) {
            Scanner input = new Scanner(reader);
            boolean cheker = true;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplited = line.split(",");
                // if there is a miss column in the lineSplited the system will exit
                if (lineSplited.length != 10) {
                    System.out.println(Colors.YELLOW + "The system will close in 5 sec because of a miss column in " + PRODUCT_FILE + Colors.RESET);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println(e.toString());
                    }
                    System.exit(0);
                }
            }

        }catch (IOException e){
            System.out.println("\n"+e.getMessage());
        }
    }

    // Load products from file
    public static void loadProducts() {
        productList.clear();
        productsID.clear();
        try (FileReader reader = new FileReader(PRODUCT_FILE.toString())) {
            Scanner input = new Scanner(reader);
            int key = 1;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplit = line.split(",");
                productList.add(new Product(
                        lineSplit[0], // productId
                        lineSplit[1], // type
                        lineSplit[2], // purchasePrice
                        lineSplit[3], // manufacturer
                        lineSplit[4], // model
                        lineSplit[5], // description
                        lineSplit[6], // displaySize
                        lineSplit[7], // storageSize
                        lineSplit[8], // sellingPrice
                        lineSplit[9] // quantityStock
                ));

                    productsID.put(key,lineSplit[0]);
                    key++;
            }
            input.close();
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public void getAllProductManager(){
        productList.clear();
        try (FileReader reader = new FileReader(PRODUCT_FILE.toString())) {
            Scanner input = new Scanner(reader);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplit = line.split(",");
                productList.add(new Product(
                        lineSplit[0], // productId
                        lineSplit[1], // type
                        lineSplit[2], // purchasePrice
                        lineSplit[3], // manufacturer
                        lineSplit[4], // model
                        lineSplit[5], // description
                        lineSplit[6], // displaySize
                        lineSplit[7], // storageSize
                        lineSplit[8], // sellingPrice
                        lineSplit[9] // quantityStock
                ));
            }
            input.close();
            int indexLine = 0;
            System.out.println("\n\ttotal number product: ["+productList.size()+"]");
            for(Product product:productList){
                indexLine++;
                System.out.println("\t["+indexLine+"]"+product.toStringManager());
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }


    }
    public void getAllProductUser(){
        productList.clear();
        try (FileReader reader = new FileReader(PRODUCT_FILE.toString())) {
            Scanner input = new Scanner(reader);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplit = line.split(",");
                productList.add(new Product(
                        lineSplit[0], // productId
                        lineSplit[1], // type
                        lineSplit[2], // purchasePrice
                        lineSplit[3], // manufacturer
                        lineSplit[4], // model
                        lineSplit[5], // description
                        lineSplit[6], // displaySize
                        lineSplit[7], // storageSize
                        lineSplit[8], // sellingPrice
                        "" // quantityStock
                ));
            }
            input.close();
            int indexLine = 0;
            System.out.println("\n\t total number product: ["+productList.size()+"]");
            for(Product product:productList){
                indexLine++;
                System.out.println("\t["+indexLine+"]"+product.toString());
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }


    }

    public Product getProductById(String productId) {
        try (FileReader reader = new FileReader(PRODUCT_FILE.toString())) {
            Scanner input = new Scanner(reader);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplit = line.split(",");

                // Check if the productId matches
                if (lineSplit[0].equals(productId)) {
                    // Return the product when a match is found
                    return new Product(
                            lineSplit[0], // productId
                            lineSplit[1], // type
                            lineSplit[2], // purchasePrice
                            lineSplit[3], // manufacturer
                            lineSplit[4], // model
                            lineSplit[5], // description
                            lineSplit[6], // displaySize
                            lineSplit[7], // storageSize
                            lineSplit[8], // sellingPrice
                            lineSplit[9]  // quantityStock
                    );
                }
            }

            input.close();
        } catch (IOException e) {
            System.out.println("\t Error loading products: " + e.getMessage());
        }
        System.out.println("\t did not find a product with that id ["+productId+" ]");
        // Return null if no product with the given ID is found
        return null;
    }
    public Boolean getProduct(String productId) {
        try (FileReader reader = new FileReader(PRODUCT_FILE.toString())) {
            Scanner input = new Scanner(reader);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplit = line.split(",");

                // Check if the productId matches
                if (lineSplit[0].equals(productId)) {
                    // Return the product when a match is found
                    return true;
                }
            }

            input.close();
        } catch (IOException e) {
            System.out.println("\t Error loading products: " + e.getMessage());
        }
        System.out.println("\t did not find a product with that id ["+productId+" ]");
        // Return null if no product with the given ID is found
        return false;
    }

    // Save products to file
    public void saveProducts(Item newProduct) {
        productList.clear();
        formatter.delete(0,formatter.length());
        loadProducts();
        try (FileWriter writer = new FileWriter(PRODUCT_FILE.toString())) {
            for (Product product : productList) {
                formatter.append(product.getProductId()).append(",")
                        .append(product.getType()).append(",")
                        .append(product.getPurchasePrice()).append(",")
                        .append(product.getManufacturer()).append(",")
                        .append(product.getModel()).append(",")
                        .append(product.getDescription()).append(",")
                        .append(product.getDisplaySize()).append(",")
                        .append(product.getStorageSize()).append(",")
                        .append(product.getSellingPrice()).append(",")
                        .append(product.getQuantityStock()).append("\n");
            }
            formatter.append(newProduct.getId()).append(",")
                    .append(newProduct.getType()).append(",")
                    .append(newProduct.getPurchasePrice()).append(",")
                    .append(newProduct.getManufacturer()).append(",")
                    .append(newProduct.getModel()).append(",")
                    .append(newProduct.getDescription()).append(",")
                    .append(newProduct.getDisplaySize()).append(",")
                    .append(newProduct.getStorageSize()).append(",")
                    .append(newProduct.getSellingPrice()).append(",")
                    .append(newProduct.getQuantityStock()).append("\n");
            writer.write(formatter.toString());
            formatter.setLength(0); // Clear the formatter
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    public void deleteProductById(String productId) {
        // Clear the product list
        productList.clear();
        // Load all existing products from the file
        loadProducts();
        // Filter out the product with the specified ID
        productList.removeIf(product -> product.getProductId().equals(productId));
        // Prepare to write to the file
        try (FileWriter writer = new FileWriter(PRODUCT_FILE.toString())) {
            formatter.setLength(0); // Clear the formatter before using it
            for (Product product : productList) {
                formatter.append(product.getProductId()).append(",")
                        .append(product.getType()).append(",")
                        .append(product.getPurchasePrice()).append(",")
                        .append(product.getManufacturer()).append(",")
                        .append(product.getModel()).append(",")
                        .append(product.getDescription()).append(",")
                        .append(product.getDisplaySize()).append(",")
                        .append(product.getStorageSize()).append(",")
                        .append(product.getSellingPrice()).append(",")
                        .append(product.getQuantityStock()).append("\n");
            }

            // Write to the file
            writer.write(formatter.toString());
            productList.clear();
            System.out.println(GREEN+"\tproduct: "+productId+"  has been deleted successfully"+RESET);
        } catch (IOException e) {
            System.out.println(RED+"\t Error deleting product: " + e.getMessage()+RESET);
        }
    }

    public void modifyProductById(String productId) {
        // Clear the product list and load products from the file
        productList.clear();
        loadProducts();
        Product productToModify = null;
        // Find the product by ID
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productToModify = product;
                break;
            }
        }
        if (productToModify == null) {
            System.out.println("Product with ID " + productId + " not found.");
            return; // Exit if product is not found
        }
        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);
        // Display the old values and prompt for new values
        System.out.println("\n\t Modify Product - Old Values:");
        System.out.println("\t Product ID: " + productToModify.getProductId());
        System.out.println("\t Type: " + productToModify.getType());
        System.out.println("\t Purchase Price: " + productToModify.getPurchasePrice());
        System.out.println("\t Manufacturer: " + productToModify.getManufacturer());
        System.out.println("\t Model: " + productToModify.getModel());
        System.out.println("\t Description: " + productToModify.getDescription());
        System.out.println("\t Display Size: " + productToModify.getDisplaySize());
        System.out.println("\t Storage Size: " + productToModify.getStorageSize());
        System.out.println("\t Selling Price: " + productToModify.getSellingPrice());
        System.out.println("\t Quantity Stock: " + productToModify.getQuantityStock());
        // Use ANSI colors for input prompts
        System.out.print("\t Enter new type (leave blank to keep old): ");
        String newType = scanner.nextLine();
        if (!newType.isEmpty()){
            productToModify.setType(newType);
        }
        System.out.print("\t Enter new purchase price (leave blank to keep old): ");
        String newPurchasePrice = scanner.nextLine();
        if (!newPurchasePrice.isEmpty()){
            productToModify.setPurchasePrice(newPurchasePrice);
        }

        System.out.print("\t Enter new manufacturer (leave blank to keep old): ");
        String newManufacturer = scanner.nextLine();
        if (!newManufacturer.isEmpty()){
            productToModify.setManufacturer(newManufacturer);
        }

        System.out.print("\t Enter new model (leave blank to keep old): ");
        String newModel = scanner.nextLine();
        if (!newModel.isEmpty()){
            productToModify.setModel(newModel);
        }

        System.out.print("\t Enter new description (leave blank to keep old): ");
        String newDescription = scanner.nextLine();
        if (!newDescription.isEmpty()){
            productToModify.setDescription(newDescription);
        }

        System.out.print("\t Enter new display size (leave blank to keep old): ");
        String newDisplaySize = scanner.nextLine();
        if (!newDisplaySize.isEmpty()){
            productToModify.setDisplaySize(newDisplaySize);
        }

        System.out.print("\t Enter new storage size (leave blank to keep old): ");
        String newStorageSize = scanner.nextLine();
        if (!newStorageSize.isEmpty()){
            productToModify.setStorageSize(newStorageSize);
        }

        System.out.print("\t Enter new selling price (leave blank to keep old): ");
        String newSellingPrice = scanner.nextLine();
        if (!newSellingPrice.isEmpty()){
            productToModify.setSellingPrice(newSellingPrice);
        }

        System.out.print("\t Enter new quantity stock (leave blank to keep old): ");
        String newQuantityStock = scanner.nextLine();
        if (!newQuantityStock.isEmpty()){
            productToModify.setQuantityStock(newQuantityStock);
        }


        // Write the updated product list back to the file
        try (FileWriter writer = new FileWriter(PRODUCT_FILE.toString())) {
            formatter.setLength(0); // Clear the formatter before using it
            // Write each product back to the file
            for (Product product : productList) {
                formatter.append(product.getProductId()).append(",")
                        .append(product.getType()).append(",")
                        .append(product.getPurchasePrice()).append(",")
                        .append(product.getManufacturer()).append(",")
                        .append(product.getModel()).append(",")
                        .append(product.getDescription()).append(",")
                        .append(product.getDisplaySize()).append(",")
                        .append(product.getStorageSize()).append(",")
                        .append(product.getSellingPrice()).append(",")
                        .append(product.getQuantityStock()).append("\n");
            }
            // Write to the file
            writer.write(formatter.toString());
        } catch (IOException e) {
            System.out.println("\t Error modifying product: " + e.getMessage());
        } finally {
            // Clear the product list after saving
            productList.clear();
            System.out.println(GREEN+"\t"+productId+" updated successfully"+RESET);
        }
    }

    public void searchProduct(ProductValues what){
        productList.clear();
        loadProducts();
        switch (what) {
            case ProductValues.TYPE:
                System.out.println("\t..........................");
                System.out.println("\t Enter device");
                System.out.println("\t 1. tablet");
                System.out.println("\t 2. smartphone");
                System.out.println("\t 3. notebook");
                System.out.print("\t Enter type: ");
                String typeEntry = InputUtils.getStringInput();
                String type = switch (typeEntry) {
                    case "1" -> "tablet";
                    case "2" -> "smartphone";
                    case "3" -> "notebook";
                    default -> "";
                };
                for (Product product : productList) {
                    if (product.getType().equalsIgnoreCase(type)) {
                        String status = "";
                        for(ConnectionStatus connected:UserConnection.userConnected){
                            if(connected.credential.equals("CLIENT")){
                                status = "CLIENT";
                                break;
                            }
                            else if(connected.credential.equals("MANAGER")){
                                status = "MANAGER";
                                break;
                            }
                        }
                        if(status.equals("CLIENT")){
                            System.out.println(product.toString());
                        }
                        else if(status.equals("MANAGER")){
                            System.out.println(product.toStringManager());
                        }
                    }
                }

            break;
            case ProductValues.MANUFACTURER:
                System.out.println("\t..........................");
                System.out.println("\t Enter manufacturer");

                System.out.print("\t Enter manufacturer: ");
                String manufacturer = InputUtils.getStringInput();
                for (Product product : productList) {
                    if (product.getManufacturer().equalsIgnoreCase(manufacturer)) {
                        System.out.println(product.toString());
                    }
                }
            break;
            case ProductValues.MODEL:
                System.out.println("\t..........................");
                System.out.println("\t Enter Model");
                System.out.print("\t Enter model: ");
                String model = InputUtils.getStringInput();
                for (Product product : productList) {
                    if (product.getModel().equalsIgnoreCase(model)) {
                        System.out.println(product.toString());
                    }
                }
            break;
            case ProductValues.SELLING_PRICE:
                System.out.println("\t..........................");
                System.out.println("\t Enter SellingPrice");
                System.out.print("\t Enter SellingPrice: ");
                String SellingPrice = InputUtils.getStringInput();
                for (Product product : productList) {
                    if (product.getSellingPrice().equalsIgnoreCase(SellingPrice)) {
                        System.out.println(product.toString());
                    }
                }
            break;
            case ProductValues.PURCHASE_PRICE:
                System.out.println("\t..........................");
                System.out.println("\t Enter purchasePrice");
                System.out.print("\t Enter purchasePrice: ");
                String purchasePrice = InputUtils.getStringInput();
                for (Product product : productList) {
                    if (product.getPurchasePrice().equalsIgnoreCase(purchasePrice)) {
                        System.out.println(product.toString());
                    }
                }
            break;
            case ProductValues.RANGE_PRICE:
                System.out.println("\t..........................");
                System.out.println("\t Enter min and max price");
                System.out.print("\t Enter min price: ");
                double min = InputUtils.getDoubleInput();
                System.out.print("\t Enter max price: ");
                double max = InputUtils.getDoubleInput();
                for (Product product : productList) {
                    if (Double.parseDouble(product.getSellingPrice()) >= min && Double.parseDouble(product.getSellingPrice()) <= max) {
                        System.out.println(product.toString());
                    }
                }
            break;
            default:
                System.out.println("\t there is no any search corresponding to ["+what+" ]");
                break;
        }
        productList.clear();
    }


}






class Product {

    // ANSI escape codes for text colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";


    private String productId;
    private String type;
    private String purchasePrice;
    private String manufacturer;
    private String model;
    private String description;
    private String displaySize;
    private String storageSize;
    private String sellingPrice;
    private String quantityStock;

    public Product(String productId, String type, String purchasePrice, String manufacturer,
                   String model, String description, String displaySize, String storageSize,
                   String sellingPrice, String quantityStock) {
        this.productId = productId != null ? productId : UUID.randomUUID().toString();
        this.type = type;
        this.purchasePrice = String.valueOf(purchasePrice);
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
        this.displaySize = String.valueOf(displaySize);
        this.storageSize = String.valueOf(storageSize);
        this.sellingPrice = String.valueOf(sellingPrice);
        this.quantityStock = String.valueOf(quantityStock);
    }

    public String getProductId() {
        return productId;
    }


    public String getType() {
        return type;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplaySize() {
        return displaySize;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public String getQuantityStock() {
        return quantityStock;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setQuantityStock(String quantityStock) {
        this.quantityStock = quantityStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, type, purchasePrice, manufacturer, model, description,
                displaySize, storageSize, sellingPrice, quantityStock);
    }

    @Override
    public String toString() {
        return
                GREEN + "\t[ ID: " + RED + productId + RESET +
                GREEN + "| Type: " + RED + type + RESET +
                GREEN + "| Price: " + RED + purchasePrice + RESET +
                GREEN + "| Manufacturer: " + RED + manufacturer + RESET +
                GREEN + "| Model: " + RED + model + RESET +
                GREEN + "\n\t| Description: " + RED + description + RESET +
                GREEN + "\n\t| Display Size: " + RED + displaySize + RESET +
                GREEN + "| Storage Size: " + RED + storageSize + RESET +
                GREEN + "| Selling Price: " + RED + sellingPrice + RESET +
                " \n\t =====================================================";
    }
    public String toStringManager() {
        return
                GREEN + "\t[ ID: " + RED + productId + RESET +
                GREEN + "| Type: " + RED + type + RESET +
                GREEN + "| Price: " + RED + purchasePrice + RESET +
                GREEN + "| Manufacturer: " + RED + manufacturer + RESET +
                GREEN + "| Model: " + RED + model + RESET +
                GREEN + "\n\t| Description: " + RED + description + RESET +
                GREEN + "\n\t| Display Size: " + RED + displaySize + RESET +
                GREEN + "| Storage Size: " + RED + storageSize + RESET +
                GREEN + "| Selling Price: " + RED + sellingPrice + RESET +
                GREEN + "| Quantity in stock: " + RED + quantityStock + RESET +
                " \n\t =====================================================";
    }

}

