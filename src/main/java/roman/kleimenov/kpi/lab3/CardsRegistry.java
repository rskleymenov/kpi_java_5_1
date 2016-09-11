package roman.kleimenov.kpi.lab3;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.CumulativeCard;
import roman.kleimenov.kpi.lab3.enums.TripNumber;
import roman.kleimenov.kpi.lab3.enums.Validity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardsRegistry {
    private static long lastCardNumber = 0;
    private final static CardsRegistry INSTANCE = new CardsRegistry();
    private static List<Card> issuedCards = new ArrayList<>();

    private CardsRegistry() {

    }

    public static CardsRegistry getInstance() {
        return INSTANCE;
    }

    public boolean isNotFakeCard(Card card) {
        return issuedCards.contains(card);
    }

    public Card issueSchoolCard(Validity validity, TripNumber tripNumber, LocalDateTime creationTime) {
        Card schoolCard = CardFactory.getInstance().getSchoolCard();
        return initCard(validity, tripNumber, creationTime, schoolCard);
    }

    public Card issueStudentCard(Validity validity, TripNumber tripNumber, LocalDateTime creationTime) {
        Card studentCard = CardFactory.getInstance().getStudentCard();
        return initCard(validity, tripNumber, creationTime, studentCard);
    }

    public Card issueDefaultCard(Validity validity, TripNumber tripNumber, LocalDateTime creationTime) {
        Card defaultCard = CardFactory.getInstance().getDefaultCard();
        return initCard(validity, tripNumber, creationTime, defaultCard);
    }

    public Card issueCumulativeCard(BigDecimal amount) {
        Card cumulativeCard = CardFactory.getInstance().getCumulativeCard();
        ((CumulativeCard) cumulativeCard).setBalance(amount);
        return initCard(Validity.UNLIMITED, TripNumber.UNLIMITED, LocalDateTime.now(), cumulativeCard);
    }

    public boolean payCumulativeCard(Card card, BigDecimal bigDecimal) {
        if (isNotFakeCard(card)) {
            CumulativeCard cumulativeCard = (CumulativeCard) card;
            cumulativeCard.setBalance(cumulativeCard.getBalance().add(bigDecimal));
            return true;
        }
        return false;
    }

    public static void clearRegister() {
        lastCardNumber = 0;
        issuedCards.clear();
    }

    private Card initCard(Validity validity, TripNumber tripNumber, LocalDateTime creationTime, Card card) {
        card.setValidity(validity);
        card.setTripNumber(tripNumber);
        card.setCreationTime(creationTime);
        card.setId(lastCardNumber++);
        issuedCards.add(card);
        return card;
    }
}
