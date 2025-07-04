public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        if(balance < 0.0) {
            throw new IllegalArgumentException("Customer balance must be greater than or equal 0.");
        }
        this.name = name;
        this.balance = balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        if(balance < 0.0) {
            throw new IllegalArgumentException("Customer balance must be greater than or equal 0.");
        }
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
