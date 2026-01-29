import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InvestmentAccount extends Account {
    private final Map<Fund, BigDecimal> funds;

    public InvestmentAccount() {
        this.funds = new HashMap<>();
        for (Fund fund : Fund.values()) {
            funds.put(fund, BigDecimal.ZERO);
        }
    }

    public void invest(Fund fund, BigDecimal amount) throws InvalidAmountException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        withdraw(amount);
        funds.put(fund, funds.get(fund).add(amount));
    }

    public void withdrawAllInvestments() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Fund, BigDecimal> entry : funds.entrySet()) {
            total = total.add(entry.getValue());
            funds.put(entry.getKey(), BigDecimal.ZERO);
        }
        deposit(total);
    }

    public void applyGrowth() {
        for (Map.Entry<Fund, BigDecimal> entry : funds.entrySet()) {
            BigDecimal growth = entry.getValue().multiply(entry.getKey().getRate());
            funds.put(entry.getKey(), entry.getValue().add(growth));
        }
    }

    public BigDecimal getFundBalance(Fund fund) {
        return funds.get(fund);
    }
}
