import java.math.BigDecimal;

public abstract class Account {
    private BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) throws InvalidAmountException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InvalidAmountException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }
}
