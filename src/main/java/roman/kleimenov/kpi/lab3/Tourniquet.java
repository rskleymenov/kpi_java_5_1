package roman.kleimenov.kpi.lab3;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.CumulativeCard;
import roman.kleimenov.kpi.lab3.enums.TripNumber;
import roman.kleimenov.kpi.lab3.enums.Validity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

// TODO: 9/11/2016 IMPLEMENT STATISTIC
public class Tourniquet {
    private static Tourniquet INSTANCE;
    private static BigDecimal TICKET_PRICE;
    private final CardsRegistry cardsRegistry = CardsRegistry.getInstance();

    private Tourniquet(String ticketPrice) {
        TICKET_PRICE = new BigDecimal(ticketPrice);
        INSTANCE = new Tourniquet(ticketPrice);
    }

    public static Tourniquet getInstance(String ticketPrice) {
        return INSTANCE;
    }

    public boolean proceed(Card card) {
        if (cardsRegistry.isNotFakeCard(card)) {
            if (!isCardHasRightValidity(card)) {
                return false;
            }

            if (!isCardHasUnusedTrips(card)) {
                return false;
            }

            if (!isCardHasPossibleBalance(card)) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isCardHasPossibleBalance(Card card) {
        if (card instanceof CumulativeCard) {
            CumulativeCard cumulativeCard = (CumulativeCard) card;
            BigDecimal balance = cumulativeCard.getBalance();
            BigDecimal remainedValue = balance.subtract(TICKET_PRICE);
            if (remainedValue.signum() > 0) {
                cumulativeCard.setBalance(remainedValue);
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean isCardHasUnusedTrips(Card card) {
        TripNumber tripNumber = card.getTripNumber();
        int usedTrips = card.getUsedTrips();
        switch (tripNumber) {
            case FIVE: {
                if (usedTrips > 5) {
                    return false;
                }
                break;
            }
            case TEN: {
                if (usedTrips > 10) {
                    return false;
                }
                break;
            }
            case UNLIMITED: {
                return true;
            }
        }
        card.setUsedTrips(++usedTrips);
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
