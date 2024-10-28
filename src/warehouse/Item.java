package warehouse;

import java.util.Objects;
import java.util.UUID;


abstract public class Item{

    // ANSI escape codes for text colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";




    private String id;
    private Types type;
    private String purchasePrice;
    private String manufacturer;
    private String model;
    private String description;
    private String displaySize;
    private String storageSize;
    private String sellingPrice;
    private String quantityStock;

    public Item( Types type, String purchasePrice, String manufacturer, String model, String description, String displaySize,
                 String storageSize, String sellingPrice, String quantityStock){
        String[] shortID = UUID.randomUUID().toString().split("-");
        this.id = shortID[0];
        this.purchasePrice = purchasePrice;
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
        this.displaySize = displaySize;
        this.storageSize = storageSize;
        this.sellingPrice = sellingPrice;
        this.quantityStock =quantityStock;
    }

    public String getId() {
        return id;
    }
    public String getQuantityStock(){
        return quantityStock;
    }
    public  Types getType() {
        return type;
    }
    public String getDescription() {
        return description;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getSellingPrice() {
        return sellingPrice;
    }
    public String getStorageSize() {
        return storageSize;
    }
    public String getModel() {
        return model;
    }
    public String getPurchasePrice() {
        return purchasePrice;
    }
    public String getDisplaySize() {
        return displaySize;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPurchasePrice(String price) {
        this.purchasePrice = price;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setType(Types type) {
        this.type = type;
    }
    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }
    public void setQuantityStock(String quantityStock) {
        this.quantityStock = quantityStock;
    }
    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    @Override
    public String toString() {
        return  "\n\t Item Details:" +
                "\n\t ===================" +
                GREEN + "\n\t [ ID: " + RED + "internal ( "+id+" )" + RESET +
                GREEN + "| Type: " + RED + type + RESET +
                GREEN + "| Price: " + RED + purchasePrice + RESET +
                GREEN + "| Manufacturer: " + RED + manufacturer + RESET +
                GREEN + "| Model: " + RED + model + RESET +
                GREEN + "\n\t| Description: " + RED + description + RESET +
                GREEN + "\n\t| Display Size: " + RED + displaySize + RESET +
                GREEN + "| Storage Size: " + RED + storageSize + RESET +
                GREEN + "| Selling Price: " + RED + sellingPrice + RESET +
                GREEN + "| Quantity in stock: " + RED + quantityStock + RESET +
                " ]\n\t ===================";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = ( Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, purchasePrice, manufacturer, model, description, displaySize, storageSize, sellingPrice, quantityStock);
    }
}



class Tablet extends  Item {
    public Tablet(String purchasePrice, String manufacturer, String model, String description, String displaySize, String storageSize, String sellingPrice, String quantityStock) {
        super( Types.TABLET, purchasePrice, manufacturer, model, description, displaySize,
                storageSize, sellingPrice, quantityStock);
    }

    @Override
    public String toString() {
        return "\n\t Tablet Details:" + super.toString();
    }
}

class Smartphone extends  Item {
    public Smartphone(String purchasePrice, String manufacturer, String model, String description, String displaySize, String storageSize, String sellingPrice, String quantityStock) {
        super( Types.SMARTPHONE, purchasePrice, manufacturer, model, description, displaySize,
                storageSize, sellingPrice, quantityStock);
    }

    @Override
    public String toString() {
        return "\n\t Smartphone Details:" + super.toString();
    }
}

class Notebook extends  Item {
    public Notebook(String purchasePrice, String manufacturer, String model, String description, String displaySize, String storageSize, String sellingPrice, String quantityStock) {
        super( Types.NOTEBOOK, purchasePrice, manufacturer, model, description, displaySize, storageSize, sellingPrice,
                quantityStock);
    }

    @Override
    public String toString() {
        return "\n\t Notebook Details:" + super.toString();
    }
}


