package roman.kleimenov.kpi.lab3.cards.impl;

import roman.kleimenov.kpi.lab3.cards.Card;

import java.math.BigDecimal;
import java.util.Objects;

import static roman.kleimenov.kpi.lab3.enums.CardType.DEFAULT;
import static roman.kleimenov.kpi.lab3.enums.Validity.UNLIMITED;

public class CumulativeCard extends Card {
    private BigDecimal balance = BigDecimal.ZERO;

    public CumulativeCard() {
        super(DEFAULT, UNLIMITED);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CumulativeCard that = (CumulativeCard) o;
        return Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), balance);
    }
}
