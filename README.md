### Project Overview
This Java-based application provides an interface to manage a warehouse system. It features separate modules for users (clients, managers, and treasurers), product management, inventory updates, cart handling, and file-based data storage.

---

### Modules and Key Files

1. **Main.java**
   - Entry point for the application, invoking the main menu through `Menu.menu()`.

2. **Menu.java**
   - Handles navigation through the app, providing options for clients, managers, and system management.
   - Allows user interactions like viewing products, adding to cart, and managing accounts.

3. **Warehouse.java**
   - Core backend for handling warehouse operations, such as:
     - User authentication (clients, managers, and treasurers).
     - Product and cart management, including adding items, finalizing purchases, and updating inventory.
     - Product search based on various criteria (type, price range, etc.).
   - Interfaces with other components like `CartSystem` and `ProductSystem` to perform inventory and cart operations.

4. **User.java**
   - Defines user roles via an `enum` (`Credential`), and implements base user class with subclasses:
     - **Client**: Can view products, add to cart, make purchases, and check balance.
     - **Manager**: Manages product additions, updates, and warehouse oversight.
     - **Treasurer**: Manages financial transactions, primarily balancing and payouts.

5. **Item.java**
   - Abstract class defining product attributes (`type`, `price`, `manufacturer`, etc.).
   - Extended by `Tablet`, `Smartphone`, and `Notebook` classes for specific item types.

6. **DBSystem Package**
   - **ProductSystem.java**: Handles file-based product management, loading products from a file, adding new items, and retrieving data by search parameters.
   - **CartSystem.java**: Manages shopping cart functions (content currently not available).

7. **InputUtils.java**
   - Provides input utility methods to capture user input and ensure proper data validation.

8. **Types.java**
   - Enum defining the types of products managed in the warehouse (TABLET, SMARTPHONE, NOTEBOOK).

9. **Colors.java**
   - Contains ANSI escape codes for styling console output with colors (e.g., red for errors, green for success).

---

### Example Operations and Features

- **Client Actions**
  - View products, add items to cart, finalize purchases, and check account balance.
  - Product searches can be filtered by type, price, manufacturer, etc.

- **Manager Actions**
  - Add or remove products from the warehouse, update product details, view all products, and track warehouse metrics.

- **File-based Product Management**
  - Data for products is stored in `PRODUCT.txt`, and methods in `ProductSystem` manage reading/writing to this file.
  - Ensures persistence of data across sessions.
