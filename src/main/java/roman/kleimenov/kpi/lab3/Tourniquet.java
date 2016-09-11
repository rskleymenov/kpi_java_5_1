package roman.kleimenov.kpi.lab3;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.CumulativeCard;
import roman.kleimenov.kpi.lab3.enums.CardType;
import roman.kleimenov.kpi.lab3.enums.TripNumber;
import roman.kleimenov.kpi.lab3.enums.Validity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tourniquet {
    private static BigDecimal TICKET_PRICE;
    private final CardsRegistry cardsRegistry = CardsRegistry.getInstance();
    private static List<Card> successfulAttemptsCard;
    private static List<Card> unsuccessfulAttemptsCard;

    private Tourniquet(String ticketPrice) {
        TICKET_PRICE = new BigDecimal(ticketPrice);
        successfulAttemptsCard = new ArrayList<>();
        unsuccessfulAttemptsCard = new ArrayList<>();
    }

    public static Tourniquet getInstance(String ticketPrice) {
        return new Tourniquet(ticketPrice);
    }

    public static void clearAttemptsStatistic() {
        successfulAttemptsCard.clear();
        unsuccessfulAttemptsCard.clear();
    }

    public boolean proceed(Card card) {
        if (isDataPresence(card)) {
            unsuccessfulAttemptsCard.add(card);
            return false;
        }
        if (cardsRegistry.isNotFakeCard(card)) {
            if (!isCardHasRightValidity(card)) {
                unsuccessfulAttemptsCard.add(card);
                return false;
            }

            if (!isCardHasUnusedTrips(card)) {
                unsuccessfulAttemptsCard.add(card);
                return false;
            }

            if (!isCardHasPossibleBalance(card)) {
                unsuccessfulAttemptsCard.add(card);
                return false;
            }
            successfulAttemptsCard.add(card);
            return true;
        }
        unsuccessfulAttemptsCard.add(card);
        return false;
    }

    public Map<CardType, Long> getTotalSuccessAttemptsByCardTypes() {
        return getGroupByCardType(successfulAttemptsCard);
    }

    public Map<CardType, Long> getTotalUnSuccessAttemptsByCardTypes() {
        return getGroupByCardType(unsuccessfulAttemptsCard);
    }

    public int getTotalSuccessAttempts() {
        return successfulAttemptsCard.size();
    }

    public int getTotalUnSuccessAttempts() {
        return unsuccessfulAttemptsCard.size();
    }

    private boolean isDataPresence(Card card) {
        return card.getId() < 0 ||
                card.getId() > Long.MAX_VALUE ||
                card.getCardType() == null ||
                card.getValidity() == null ||
                card.getTripNumber() == null ||
                card.getUsedTrips() < 0 ||
                card.getUsedTrips() > Long.MAX_VALUE ||
                card.getCreationTime() == null;
    }

    private Map<CardType, Long> getGroupByCardType(List<Card> cards) {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::getCardType, Collectors.counting()));
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

    private boolean isCardHasUnusedTrips(Card card) {
        if (!(card instanceof CumulativeCard)) {
            TripNumber tripNumber = card.getTripNumber();
            int usedTrips = card.getUsedTrips();
            switch (tripNumber) {
                case FIVE: {
                    if (usedTrips >= 5) {
                        return false;
                    }
                    break;
                }
                case TEN: {
                    if (usedTrips >= 10) {
                        return false;
                    }
                    break;
                }
            }
            card.setUsedTrips(++usedTrips);
        }
        return true;
    }

    private boolean isCardHasRightValidity(Card card) {
        Validity validity = card.getValidity();
        LocalDateTime creationDate = card.getCreationTime();
        LocalDateTime currentDate = LocalDateTime.now();
        switch (validity) {
            case MONTH: {
                int year = creationDate.getYear();
                Month month = creationDate.getMonth();
                if (year != currentDate.getYear() || !month.equals(currentDate.getMonth())) {
                    return false;
                }
                break;
            }
            case TEN_DAYS: {
                long usedDays = ChronoUnit.DAYS.between(creationDate, currentDate);
                if (usedDays > 10) {
                    return false;
                }
                break;
            }
            case UNLIMITED: {
                return true;
            }
        }
        return true;
    }


}
