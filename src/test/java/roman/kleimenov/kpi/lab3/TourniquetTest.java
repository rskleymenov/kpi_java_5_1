package roman.kleimenov.kpi.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.DefaultCard;
import roman.kleimenov.kpi.lab3.enums.CardType;
import roman.kleimenov.kpi.lab3.validator.impl.CardBalanceValidator;
import roman.kleimenov.kpi.lab3.validator.impl.CardUnusedTripsValidator;
import roman.kleimenov.kpi.lab3.validator.impl.CardValidityValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.*;
import static roman.kleimenov.kpi.lab3.enums.TripNumber.FIVE;
import static roman.kleimenov.kpi.lab3.enums.TripNumber.TEN;
import static roman.kleimenov.kpi.lab3.enums.Validity.MONTH;
import static roman.kleimenov.kpi.lab3.enums.Validity.TEN_DAYS;

public class TourniquetTest {
    private static final String TICKET_PRICE = "3";
    private Tourniquet tourniquet = Tourniquet.getInstance();
    private CardsRegistry cardsRegistry = CardsRegistry.getInstance();

    @Before
    public void init() {
        CardsRegistry.clearRegister();
        cardsRegistry.addCardRule(new CardBalanceValidator(TICKET_PRICE));
        cardsRegistry.addCardRule(new CardUnusedTripsValidator());
        cardsRegistry.addCardRule(new CardValidityValidator());
    }

    @After
    public void destroy() {
        CardsRegistry.clearRegister();
    }

    @Test
    public void shouldReadAllData() {
        Card defaultCard = cardsRegistry.issueDefaultCard(MONTH, TEN, LocalDateTime.now());
        assertTrue(tourniquet.proceed(defaultCard));
    }

    @Test
    public void shouldNotProceedCardDataDueToEmptyValue() {
        Card defaultCard = cardsRegistry.issueDefaultCard(null, TEN, LocalDateTime.now());
        assertFalse(tourniquet.proceed(defaultCard));
    }

    @Test
    public void shouldNotProceedFakeCard() {
        Card fakeCard = new DefaultCard();
        fakeCard.setId(55);
        fakeCard.setCreationTime(LocalDateTime.now());
        fakeCard.setTripNumber(FIVE);
        fakeCard.setValidity(MONTH);
        fakeCard.setUsedTrips(5);
        assertFalse(tourniquet.proceed(fakeCard));
    }

    @Test
    public void shouldProceedDueToRightValidityAndOtherValues() {
        Card card = cardsRegistry.issueDefaultCard(MONTH, FIVE, LocalDateTime.now());
        assertTrue(tourniquet.proceed(card));
    }

    @Test
    public void shouldNotProceedDueToNotRightValidityAndOtherValues() {
        Card card = cardsRegistry.issueDefaultCard(MONTH, FIVE, LocalDateTime.now().minusMonths(2));
        assertFalse(tourniquet.proceed(card));
    }

    @Test
    public void shouldProceedDueToRightValidityAndOtherValues2() {
        Card card = cardsRegistry.issueDefaultCard(TEN_DAYS, FIVE, LocalDateTime.now().minusDays(8));
        assertTrue(tourniquet.proceed(card));
    }

    @Test
    public void shouldNotProceedDueToNotRightValidityAndOtherValues2() {
        Card card = cardsRegistry.issueDefaultCard(TEN_DAYS, FIVE, LocalDateTime.now().minusDays(11));
        assertFalse(tourniquet.proceed(card));
    }

    @Test
    public void shouldProceedDueToRightTripNumberValue() {
        Card card = cardsRegistry.issueDefaultCard(TEN_DAYS, FIVE, LocalDateTime.now());
        assertTrue(tourniquet.proceed(card));
    }

    @Test
    public void shouldNotProceedDueToNotRightTripNumberValue() {
        Card card = cardsRegistry.issueDefaultCard(TEN_DAYS, FIVE, LocalDateTime.now());
        useCard(card, 5);
        assertFalse(tourniquet.proceed(card));
    }

    @Test
    public void shouldProceedDueToRightTripNumberValue2() {
        Card card = cardsRegistry.issueDefaultCard(TEN_DAYS, TEN, LocalDateTime.now());
        assertTrue(tourniquet.proceed(card));
    }

    @Test
    public void shouldNotProceedDueToNotRightTripNumberValue2() {
        Card card = cardsRegistry.issueDefaultCard(TEN_DAYS, TEN, LocalDateTime.now());
        useCard(card, 10);
        assertFalse(tourniquet.proceed(card));
    }

    @Test
    public void shouldProceedCardWithAvailableRightAmount() {
        Card card = cardsRegistry.issueCumulativeCard(new BigDecimal("30"));
        useCard(card, 5);
        assertTrue(tourniquet.proceed(card));
    }

    @Test
    public void shouldNotProceedCardWithBadMoneyAmount() {
        Card card = cardsRegistry.issueCumulativeCard(new BigDecimal("30"));
        useCard(card, 10);
        assertFalse(tourniquet.proceed(card));
    }

    @Test
    public void shouldReturnTotalStatistic() {
        Tourniquet.clearAttemptsStatistic();
        Card schoolCard = cardsRegistry.issueSchoolCard(TEN_DAYS, TEN, LocalDateTime.now());
        useCard(schoolCard, 23); // 10 times can go through tourniquet, 13 - hasn't
        Card cumulativeCard = cardsRegistry.issueCumulativeCard(new BigDecimal("30"));
        useCard(cumulativeCard, 50); // 10 times can go through tourniquet, 40 - hasn't
        assertEquals(20, tourniquet.getTotalSuccessAttempts());
        assertEquals(53, tourniquet.getTotalUnSuccessAttempts());
    }

    @Test
    public void shouldReturnSuccessfulStatisticByCards() {
        Tourniquet.clearAttemptsStatistic();
        Card schoolCard = cardsRegistry.issueSchoolCard(TEN_DAYS, TEN, LocalDateTime.now());
        useCard(schoolCard, 23); // 10 times can go through tourniquet, 13 - hasn't
        Card cumulativeCard = cardsRegistry.issueCumulativeCard(new BigDecimal("30"));
        useCard(cumulativeCard, 50); // 10 times can go through tourniquet, 40 - hasn't
        Map<CardType, Long> successStatistic = tourniquet.getTotalSuccessAttemptsByCardTypes();
        assertEquals(Long.valueOf(10), successStatistic.get(schoolCard.getCardType()));
        assertEquals(Long.valueOf(10), successStatistic.get(cumulativeCard.getCardType()));
    }

    @Test
    public void shouldReturnUnSuccessfulStatisticByCards() {
        Tourniquet.clearAttemptsStatistic();
        Card schoolCard = cardsRegistry.issueSchoolCard(TEN_DAYS, TEN, LocalDateTime.now());
        useCard(schoolCard, 23); // 10 times can go through tourniquet, 13 - hasn't
        Card cumulativeCard = cardsRegistry.issueCumulativeCard(new BigDecimal("30"));
        useCard(cumulativeCard, 50); // 10 times can go through tourniquet, 40 - hasn't
        Map<CardType, Long> successStatistic = tourniquet.getTotalUnSuccessAttemptsByCardTypes();
        assertEquals(Long.valueOf(13), successStatistic.get(schoolCard.getCardType()));
        assertEquals(Long.valueOf(40), successStatistic.get(cumulativeCard.getCardType()));
    }

    private void useCard(Card card, int times) {
        for (int i = 0; i < times; i++) tourniquet.proceed(card);
    }


}