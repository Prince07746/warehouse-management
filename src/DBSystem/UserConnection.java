package DBSystem;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import com.sun.jdi.ClassObjectReference;
import warehouse.InputUtils;
import warehouse.User;
import ColorsStyle.Colors;

enum Credential{
    MANAGER,CLIENT,TREASURER
}
enum STATUS{
    CONNECTED,DISCONNECTED
}



public class UserConnection {
    public static Path USERFile;
    static {
        USERFile = Paths.get("src","FileDBSystem","USER.txt").toAbsolutePath();
    }
    protected static ArrayList<UserTemplate> usersList = new ArrayList<>();
    protected StringBuilder formater = new StringBuilder();
    protected static List<ConnectionStatus> userConnected = new ArrayList<>();

    public UserConnection() {
        try (FileReader reader = new FileReader(USERFile.toString())) {
            Scanner input = new Scanner(reader);
            boolean cheker = true;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineSplited = line.split(",");
                // if there is a miss column in the lineSplited the system will exit
                if (lineSplited.length != 6) {
                    System.out.println(Colors.YELLOW + "The system will close in 5 sec because of a miss column in " + USERFile + Colors.RESET);
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



    public void userLoader(){
            usersList.clear();
            try (
             FileReader reader = new FileReader(USERFile.toString());
            ) {
                Scanner input = new Scanner(reader);

                while (input.hasNextLine()){
                    String line = input.nextLine();
                    String[] lineSplited = line.split(",");

                      usersList.add(new UserTemplate(lineSplited[0],lineSplited[1],lineSplited[2],lineSplited[3], Double.parseDouble(lineSplited[4]), Double.parseDouble(lineSplited[5])));
                }
                input.close();
                reader.close();

                if(usersList.isEmpty()){
                    System.out.println("\t no Client in the database");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            };

    }
    public boolean registerUser(User user){
      usersList.clear();
      formater.delete(0,formater.length());
      boolean isRegistered = true;
      try(FileReader reader = new FileReader(USERFile.toString())){
          Scanner input = new Scanner(reader);
          boolean cheker = true;
          while (input.hasNextLine()){
              String line = input.nextLine();
                  String[] lineSplited = line.split(",");
                  if(user.getName().equals(lineSplited[0])){
                      cheker = false;
                      break;
                  }else{
                      usersList.add(new UserTemplate(lineSplited[0],lineSplited[1],lineSplited[2],lineSplited[3], Double.parseDouble(lineSplited[4]), Double.parseDouble(lineSplited[5])));
                  }
          }

          if(cheker){

              usersList.add(new UserTemplate(user.getName(),user.getPassword(), user.getUserId(),
                      ""+user.getCredential(),0,0));
              for(UserTemplate userIn:usersList){
                  formater.append(userIn.getName()).append(",").append(userIn.getPassword()).append(",").append(userIn.getId()).append(",").append(userIn.getCredential()).append(",").append(userIn.getAccount()).append(",").append(userIn.getSalary()).append("\n");
              }


              try(FileWriter writer = new FileWriter(USERFile.toString())) {
                    writer.write(formater.toString());
                    writer.close();
                    System.out.println(Colors.GREEN+"\t "+user.getCredential()+" registered"+Colors.RESET);
                    formater.delete(0,formater.length());
                    usersList.clear();

              } catch (IOException e){
                  System.out.println("\t "+e.getMessage());
                  System.out.println(Colors.RED+"\t error when writing file"+Colors.RESET);
              }


          }else{
              isRegistered = false;
              System.out.println("\t something might be wrong your "+Colors.RED+"[ name is already used ]"+Colors.RESET);
          }

      } catch (IOException e){
          System.out.println(e.getMessage());
      }
     return isRegistered;
    }


    public boolean loginUser(String name, String password,String credential) {
        boolean isLoggedIn = false;
        usersList.clear();
        userConnected.clear();
        userLoader(); // Load the users from file

        for (UserTemplate userIn : usersList) {
            // Check if the name and password match
            if (userIn.getName().equals(name) && userIn.getPassword().equals(password) && userIn.getCredential().equals(credential)) {
                // Login success, update the status
                ConnectionStatus userStatus = new ConnectionStatus(userIn.getName(), userIn.getId(),
                        userIn.getPassword(), STATUS.CONNECTED, userIn.getCredential(),
                        String.valueOf(userIn.getAccount()),
                        String.valueOf(userIn.getSalary()));
                       userConnected.add(userStatus); // Add to connected users list

                    System.out.println("\n\t "+credential+" "+ userIn.getName() + "  logged in" +
                            " " +
                            "successfully.");

                isLoggedIn = true;
                break;
            }
        }

        if (!isLoggedIn) {
            // If login fails
            System.out.println("\n\t Invalid username or password. Please try again.");
        }

        return isLoggedIn; // Return whether the login was successful
    }
    public boolean deleteUser(String userId){
        boolean isDeleted = false;
        usersList.clear();
        userLoader();
        boolean checker = false;
        for(UserTemplate userIn:usersList){
            if(!userIn.getId().equals(userId)){
                formater.append(userIn.getName()).append(",").append(userIn.getPassword()).append(",").append(userIn.getId()).append(",").append(userIn.getCredential()).append(",").append(userIn.getAccount()).append(",").append(userIn.getSalary()).append("\n");
            }else{
                checker = true;
            }
        }
        if(checker){
            try( FileWriter writer = new FileWriter(USERFile.toString())) {
                writer.write(formater.toString());
                writer.close();
                formater.delete(0, formater.length());
                usersList.clear();
                System.out.println("\n\t name: " + userId + " has been deleted successfully");
                isDeleted = true;
            } catch (IOException e){
                System.out.println(e.getMessage());
                System.out.println("\t an error when trying to delete client error [FW-US-D]");
            }
        }else {
            System.out.println("\t Client "+userId+" not in the Database");
            formater.delete(0,formater.length());
            usersList.clear();
        }
        return isDeleted;
    }
    public void getAllUsers(){
        usersList.clear();
        try (
                FileReader reader = new FileReader(USERFile.toString());
        ) {
            Scanner input = new Scanner(reader);

            while (input.hasNextLine()){
                String line = input.nextLine();
                String[] lineSplited = line.split(",");
                usersList.add(new UserTemplate(lineSplited[0],lineSplited[1],lineSplited[2],lineSplited[3], Double.parseDouble(lineSplited[4]), Double.parseDouble(lineSplited[5])));
            }
            input.close();
            reader.close();

            int indexRow = 0;
            for(UserTemplate userIn:usersList){
                indexRow++;
                System.out.println("\t["+indexRow+"]"+userIn.toString());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        };
    }

    // Logout method
    public boolean logoutUser(String name, String password) {
        boolean isLoggedOut = false;

        for (ConnectionStatus userStatus : userConnected) {
            if (userStatus.getName().equals(name) && userStatus.getPassword().equals(password)) {
                // Remove the user from connected users list and set status to DISCONNECTED
                userConnected.remove(userStatus);
                userStatus.setStatus(STATUS.DISCONNECTED);
                System.out.println("\n\t Client " + userStatus.getName() + " (" + userStatus.getId() + ") has logged out.");
                isLoggedOut = true;
                break;
            }
        }

        if (!isLoggedOut) {
            System.out.println("\n\t Logout failed. Client not found or incorrect credentials.");
        }

        return isLoggedOut;
    }

    // Method to get all connected users
    public void getAllConnectedUsers() {
        if (userConnected.isEmpty()) {
            System.out.println("\n\t No clients are currently connected.");
        } else {
            System.out.println("\n\t List of all connected clients:");
            for (ConnectionStatus userStatus : userConnected) {
                System.out.println(userStatus);
            }
        }
    }
    // method to get the actual user connected
    public static String[] getUserConnected(){
        String[] userConnectedList = new String[6];
        userConnectedList[0] = userConnected.getFirst().getName();
        userConnectedList[1] = userConnected.getFirst().getPassword();
        userConnectedList[2] = userConnected.getFirst().getId();
        userConnectedList[3] = userConnected.getFirst().getCredential();
        userConnectedList[4] = String.valueOf(userConnected.getFirst().getAccount());
        userConnectedList[5] = String.valueOf(userConnected.getFirst().getSalary());
        return userConnectedList;
    }
    // method to update a user connection
    public void updateConnectedUser(String name,String password){
        usersList.clear();
        userConnected.clear();
        userLoader(); // Load the users from file

        for (UserTemplate userIn : usersList) {
            // Check if the name and password match
            if (userIn.getName().equals(name) && userIn.getPassword().equals(password)) {
                // Login success, update the status
                ConnectionStatus userStatus = new ConnectionStatus(userIn.getName(), userIn.getId(),
                        userIn.getPassword(), STATUS.CONNECTED, userIn.getCredential(),
                        String.valueOf(userIn.getAccount()),
                        String.valueOf(userIn.getSalary()));
                userConnected.add(userStatus); // Add to connected users list
                break;
            }
        }
        usersList.clear();
    }

        // Method to update the name of a user
    public boolean updateUserName(String userId, String newName) {
            return updateUserField(userId, newName, null, -1, -1);
        }

        // Method to update the password of a user
    public boolean updateUserPassword(String userId, String newPassword) {
            return updateUserField(userId, null, newPassword, -1, -1);
        }

        // Method to update the account of a user
    public boolean updateUserAccount(String userId, double newAccount) {
            return updateUserField(userId, null, null, newAccount, -1);
        }
    public boolean updateUserAccountAfterPurchase(String userId, double newAccount) {
            return updateUserFieldAfterPurchase(userId, null, null, newAccount, -1);
        }

        // Method to update the salary of a user
    public boolean updateUserSalary(String userId, double newSalary) {
            return updateUserField(userId, null, null, -1, newSalary);
        }

        // Generic method to update user details based on the parameters
     private boolean updateUserField(String userId, String newName, String newPassword, double newAccount, double newSalary) {
            boolean isUpdated = false;
            usersList.clear();
            userLoader(); // Load the users from file
            boolean checker = false;

            formater.delete(0, formater.length()); // Clear the formatter

            for (UserTemplate userIn : usersList) {
                if (userIn.getId().equals(userId)) {
                    // Update user details
                    if (newName != null) {
                        userIn.setName(newName);
                    }
                    if (newPassword != null) {
                        userIn.password = newPassword;
                    }
                    if (newAccount >= 0) {
                        userIn.account += newAccount;
                    }
                    if (newSalary >= 0) {
                        Credential userCredential = Credential.valueOf(userIn.getCredential());
                        if(userCredential == Credential.MANAGER ||userCredential == Credential.TREASURER){
                            userIn.salary += userIn.salary;
                        }else{
                            userIn.salary = 0;
                        }
                    }
                    userConnected.clear();
                    ConnectionStatus userStatus = new ConnectionStatus(userIn.getName(), userIn.getId(),
                            userIn.getPassword(), STATUS.CONNECTED, userIn.getCredential(),
                            String.valueOf(userIn.getAccount()),
                            String.valueOf(userIn.getSalary()));
                    userConnected.add(userStatus);

                    checker = true; // Mark that the user was found and updated
                }
                // Append updated or unchanged users to the formatter
                formater.append(userIn.getName()).append(",")
                        .append(userIn.getPassword()).append(",")
                        .append(userIn.getId()).append(",")
                        .append(userIn.getCredential()).append(",")
                        .append(userIn.getAccount()).append(",")
                        .append(userIn.getSalary()).append("\n");
            }

            if (checker) { // If user was found and updated
                try (FileWriter writer = new FileWriter(USERFile.toString())) {
                    writer.write(formater.toString()); // Write updated data to file
                    formater.delete(0, formater.length()); // Clear the formatter
                    usersList.clear(); // Clear the list after saving
                    System.out.println("\n\t Client " + userId + " has been updated successfully");
                    isUpdated = true;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("\t Error when trying to update client error [FW-US-U]");
                }
            } else {
                System.out.println("\t Client " + userId + " not in the Database");
                formater.delete(0, formater.length()); // Clear the formatter
                usersList.clear();
            }
            return isUpdated; // Return whether the update was successful
        }
     private boolean updateUserFieldAfterPurchase(String userId, String newName, String newPassword, double newAccount,
                                      double newSalary) {
            boolean isUpdated = false;
            usersList.clear();
            userLoader(); // Load the users from file
            boolean checker = false;

            formater.delete(0, formater.length()); // Clear the formatter

            for (UserTemplate userIn : usersList) {
                if (userIn.getId().equals(userId)) {
                    // Update user details
                    if (newName != null) {
                        userIn.setName(newName);
                    }
                    if (newPassword != null) {
                        userIn.password = newPassword;
                    }
                    if (newAccount >= 0) {
                        userIn.account = newAccount;
                    }
                    if (newSalary >= 0) {
                        Credential userCredential = Credential.valueOf(userIn.getCredential());
                        if(userCredential == Credential.MANAGER ||userCredential == Credential.TREASURER){
                            userIn.salary += userIn.salary;
                        }else{
                            userIn.salary = 0;
                        }
                    }
                    userConnected.clear();
                    ConnectionStatus userStatus = new ConnectionStatus(userIn.getName(), userIn.getId(),
                            userIn.getPassword(), STATUS.CONNECTED, userIn.getCredential(),
                            String.valueOf(userIn.getAccount()),
                            String.valueOf(userIn.getSalary()));
                    userConnected.add(userStatus);

                    checker = true; // Mark that the user was found and updated
                }
                // Append updated or unchanged users to the formatter
                formater.append(userIn.getName()).append(",")
                        .append(userIn.getPassword()).append(",")
                        .append(userIn.getId()).append(",")
                        .append(userIn.getCredential()).append(",")
                        .append(userIn.getAccount()).append(",")
                        .append(userIn.getSalary()).append("\n");
            }

            if (checker) { // If user was found and updated
                try (FileWriter writer = new FileWriter(USERFile.toString())) {
                    writer.write(formater.toString()); // Write updated data to file
                    formater.delete(0, formater.length()); // Clear the formatter
                    usersList.clear(); // Clear the list after saving
                    System.out.println("\n\t Client " + userId + " has been updated successfully");
                    isUpdated = true;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("\t Error when trying to update client error [FW-US-U]");
                }
            } else {
                System.out.println("\t Client " + userId + " not in the Database");
                formater.delete(0, formater.length()); // Clear the formatter
                usersList.clear();
            }
            return isUpdated; // Return whether the update was successful
        }

      // this method will update all the
     public void wareHouseIncome(double amount){
        usersList.clear();
        userLoader();
        StringBuilder builder = new StringBuilder();
        for(UserTemplate  user: usersList){
            if(user.getCredential().equals("TREASURER")){
                user.setAccount(amount);
            }
            builder.append(user.getUserTxtFormat()).append("\n");
        }
         try(FileWriter writer = new FileWriter(USERFile.toString(), false)) {
             writer.write(builder.toString());
             writer.close();
             usersList.clear();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }


    public void wareHouseExpense() {
        usersList.clear();
        userLoader();
        ProductSystem.productList.clear();
        ProductSystem.loadProducts();

        double warehouseAmount = 0;
        for(UserTemplate user:usersList){
            if(user.getCredential().equals("TREASURER")){
                warehouseAmount += user.getAccount();
            }
        }

        double purchase = 0;
        for(Product item:ProductSystem.productList){
            purchase +=  Double.parseDouble(item.getPurchasePrice())*Integer.parseInt(item.getQuantityStock());
        }

        System.out.println(Colors.BLUE+"\t ---WAREHOUSE ACCOUNT---"+Colors.RESET);
        System.out.println(Colors.BLUE+"\t    ================="+Colors.RESET);
        System.out.println("\t Total amount wareHouse sold: +"+Colors.GREEN+warehouseAmount+Colors.RESET);
        System.out.println("\t Total amount wareHouse purchased: -"+ Colors.RED+purchase+Colors.RESET);


    }

}






class ConnectionStatus{
    String name;
    String id;
    String password;
    String credential;
    String account;
    String salary;
    STATUS status;
    public ConnectionStatus(String name,String id,String password,STATUS status, String credential, String account, String salary) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.status = status;
        this.credential = credential;
        this.account = account;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public STATUS getStatus() {
        return status;
    }

    public String getCredential() {
        return credential;
    }

    public String getAccount() {
        return account;
    }

    public String getSalary() {
        return salary;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                        "\n\t|| userId     =" + id+
                        "\n\t|| name       =" + name+
                        "\n\t|| password   =" + password+
                        "\n\t|| Status     =" + status+
                        "\n\t============================\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionStatus that = (ConnectionStatus) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

class UserTemplate{
    String name;
    String password;
    String id;
    double account;
    double salary;
    String credential;

    public UserTemplate(String name, String password, String id,String credential, double account, double salary) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.account = account;
        this.salary = salary;
        this.credential = credential;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public double getAccount() {
        return account;
    }

    public double getSalary() {
        return salary;
    }

    public String getCredential() {
        return credential;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAccount(double amount){
        this.account += amount;
    }
    @Override
    public String toString() {
        return
              "\n\t|| clientId     = " + id+
              "\n\t|| name       = " + name+
              "\n\t|| password   = " + password+
              "\n\t|| credential = " + credential+
              "\n\t|| account    = " + account+
              "\n\t|| salary     = " + salary+
              "\n\t=======================";
    }
    public String getUserTxtFormat(){
        return name+","+password+","+id+","+credential+ "," + account + "," + salary;
    }



}