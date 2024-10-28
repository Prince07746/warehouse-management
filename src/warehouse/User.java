package warehouse;
import java.util.Objects;
import java.util.UUID;

enum Credential{
    MANAGER,CLIENT,TREASURER
}

// THIS IS AN INTERFACE FOR ALL USERS ( CLIENT,MANAGER,TREASURER)

abstract public class User{
    String userId;
    String name;
    String password;
    Credential credential;

    public User(String name,String password,Credential credential){
        this.name = name;
        this.password = password;
        this.userId = UUID.randomUUID().toString();
        this.credential = credential;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getUserId() {
        return userId;
    }
    public Credential getCredential() {
        return credential;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(password, user.password) && credential == user.credential;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, credential);
    }
}




//CLIENT WHO CAN BUY USE THE CART AND FINALISE PURCHASE AND CAN INCREASE HIS ACCOUNT BALANCE

class Client extends User{

    double account;
    public Client(String name,String password){
        super(name,password,Credential.CLIENT);
        account = updateAccount();
    }
    public double getAccount() {
        return account;
    }
    public double updateAccount(){
        return 1.0;
    }
    public void setAccount(double account) {
        this.account = getAccount()+account;
    }


    @Override
    public String toString() {
        return
                "\n\tuserId     =" + userId+
                "\n\tname       =" + name+
                "\n\tpassword   =" + password+
                "\n\tcredential =" + credential+
                "\n\taccount    =" + account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return super.equals(o) || Objects.equals(userId,client.userId) && Objects.equals(password,
                client.getPassword()) && Objects.equals(credential,client.credential);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId,password,credential,account);
    }
}



// MANAGER CAN REMOVE CLIENT CAN BLOCK CLIENT AND CAN MONITOR THE ACTIVITIES DONE IN THE WAREHOUSE CAN PAID AS EMPLOYEE
class Manager extends User{
    double salary;
    double account;
    public Manager(String name,String password){
        super(name,password,Credential.MANAGER);
        this.salary = updateSalary();
        this.account = updateAccount();
    }


    public double updateSalary(){
        return 0.0;
    }
    public double updateAccount(){
        return 0.0;
    }
    public double getSalary() {
        return salary;
    }
    public double getAccount() {
        return account;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setAccount(double account) {
        this.account = account;
    }
    @Override
    public String toString() {
        return
                "\n\tuserId     =" + userId+
                "\n\tname       =" + name+
                "\n\tpassword   =" + password+
                "\n\tcredential =" + credential+
                "\n\taccount    =" + account+
                "\n\tsalary     =" +salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return super.equals(o) || Objects.equals(userId,manager.userId) && Objects.equals(password,
                manager.password) && Objects.equals(credential,manager.credential);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId,password,credential,account,salary);
    }
}



// TREASURER CLASS (WHO CAN HAVE ACCESS TO MONEY AND CAN WITHDRAW

class Treasurer extends User{
    double salary;
    double account;
    public Treasurer(String name,String password){
        super(name,password,Credential.TREASURER);
        salary = updateSalary();
        account = updateAccount();
    }

    public double updateSalary(){
        return 300.0;
    }
    public double updateAccount(){
        return 300.00;
    }

    public double getAccount() {
        return account;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setAccount(double account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return
                "\n\tuserId     =" + userId+
                "\n\tname       =" + name+
                "\n\tpassword   =" + password+
                "\n\tcredential =" + credential+
                "\n\taccount    =" + account+
                "\n\tsalary     =" + salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Treasurer treasurer = (Treasurer) o;
        return super.equals(o) || Objects.equals(userId,treasurer.userId) && Objects.equals(password,
                treasurer.password) && Objects.equals(credential,treasurer.credential);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salary, account,userId,password,credential);
    }
}