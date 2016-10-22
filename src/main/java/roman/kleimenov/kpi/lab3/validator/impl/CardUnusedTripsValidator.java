package roman.kleimenov.kpi.lab3.validator.impl;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.CumulativeCard;
import roman.kleimenov.kpi.lab3.enums.TripNumber;
import roman.kleimenov.kpi.lab3.validator.CardValidator;


public class CardUnusedTripsValidator implements CardValidator {

    @Override
    public boolean isValid(Card card) {
        return isCardHasUnusedTrips(card);
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
}
