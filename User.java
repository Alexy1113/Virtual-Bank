import java.math.BigDecimal;

public class User {
    private final String name;
    private BigDecimal cash;
    private final SavingsAccount savingsAccount;
    private final InvestmentAccount investmentAccount;

    public User(String name) {
        this.name = name;
        this.cash = new BigDecimal("1000");
        this.savingsAccount = new SavingsAccount();
        this.investmentAccount = new InvestmentAccount();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public InvestmentAccount getInvestmentAccount() {
        return investmentAccount;
    }
}
