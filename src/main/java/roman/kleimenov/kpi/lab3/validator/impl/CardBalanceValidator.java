package roman.kleimenov.kpi.lab3.validator.impl;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.CumulativeCard;
import roman.kleimenov.kpi.lab3.validator.CardValidator;

import java.math.BigDecimal;

public class CardBalanceValidator implements CardValidator {

    private static BigDecimal TICKET_PRICE;

    public CardBalanceValidator(String ticketPrice) {
        TICKET_PRICE = new BigDecimal(ticketPrice);
    }

    @Override
    public boolean isValid(Card card) {
        return isCardHasPossibleBalance(card);
    }

    private boolean isCardHasPossibleBalance(Card card) {
        if (card instanceof CumulativeCard) {
            CumulativeCard cumulativeCard = (CumulativeCard) card;
            BigDecimal balance = cumulativeCard.getBalance();
            BigDecimal remainedValue = balance.subtract(TICKET_PRICE);
            if (remainedValue.signum() >= 0) {
                cumulativeCard.setBalance(remainedValue);
                return true;
            }
            return false;
        }
        return true;
    }
}
