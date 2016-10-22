package roman.kleimenov.kpi.lab3;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.enums.CardType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tourniquet {
    private final CardsRegistry cardsRegistry = CardsRegistry.getInstance();
    private static List<Card> successfulAttemptsCard;
    private static List<Card> unsuccessfulAttemptsCard;

    private Tourniquet() {
        successfulAttemptsCard = new ArrayList<>();
        unsuccessfulAttemptsCard = new ArrayList<>();
    }

    public static Tourniquet getInstance() {
        return new Tourniquet();
    }

    public static void clearAttemptsStatistic() {
        successfulAttemptsCard.clear();
        unsuccessfulAttemptsCard.clear();
    }

    public boolean proceed(Card card) {
        if (cardsRegistry.isDataPresence(card)) {
            registerUnsuccessfulAttempt(card);
            return false;
        }
        if (cardsRegistry.isNotFakeCard(card)) {
            if (isCardInvalid(card)) {
                registerUnsuccessfulAttempt(card);
                return false;
            }
            registerSuccessfulAttempt(card);
            return true;
        }
        registerUnsuccessfulAttempt(card);
        return false;
    }

    private boolean isCardInvalid(Card card) {
        return cardsRegistry.getCardRules()
                .stream()
                .anyMatch(validator -> !validator.isValid(card));
    }

    private void registerSuccessfulAttempt(Card card) {
        successfulAttemptsCard.add(card);
    }

    private void registerUnsuccessfulAttempt(Card card) {
        unsuccessfulAttemptsCard.add(card);
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

    private Map<CardType, Long> getGroupByCardType(List<Card> cards) {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::getCardType, Collectors.counting()));
    }
}
