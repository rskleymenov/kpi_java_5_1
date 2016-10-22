package roman.kleimenov.kpi.lab3.validator.impl;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.enums.Validity;
import roman.kleimenov.kpi.lab3.validator.CardValidator;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class CardValidityValidator implements CardValidator{

    @Override
    public boolean isValid(Card card) {
        return isCardHasRightValidity(card);
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
