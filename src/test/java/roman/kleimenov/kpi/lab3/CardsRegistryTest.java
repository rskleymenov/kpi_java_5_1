package roman.kleimenov.kpi.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.CumulativeCard;
import roman.kleimenov.kpi.lab3.cards.impl.DefaultCard;
import roman.kleimenov.kpi.lab3.enums.TripNumber;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static roman.kleimenov.kpi.lab3.enums.CardType.*;
import static roman.kleimenov.kpi.lab3.enums.TripNumber.FIVE;
import static roman.kleimenov.kpi.lab3.enums.TripNumber.TEN;
import static roman.kleimenov.kpi.lab3.enums.Validity.MONTH;
import static roman.kleimenov.kpi.lab3.enums.Validity.TEN_DAYS;
import static roman.kleimenov.kpi.lab3.enums.Validity.UNLIMITED;

public class CardsRegistryTest {
    private CardsRegistry cardsRegistry = CardsRegistry.getInstance();

    @Before
    public void init() {
        CardsRegistry.clearRegister();
    }

    @After
    public void destroy() {
        CardsRegistry.clearRegister();
    }

    @Test
    public void shouldIssueSchoolCardWithSomeParameters() {
        cardsRegistry.issueDefaultCard(MONTH, TEN, LocalDateTime.now());
        Card schoolCard = cardsRegistry.issueSchoolCard(TEN_DAYS, TEN, LocalDateTime.now());
        assertEquals(1L, schoolCard.getId());
        assertEquals(SCHOOL, schoolCard.getCardType());
        assertEquals(TEN_DAYS, schoolCard.getValidity());
        assertEquals(TEN, schoolCard.getTripNumber());
    }

    @Test
    public void shouldIssueStudentCardWithSomeParameters() {
        Card studentCard = cardsRegistry.issueStudentCard(MONTH, FIVE, LocalDateTime.now());
        assertEquals(0L, studentCard.getId());
        assertEquals(STUDENT, studentCard.getCardType());
        assertEquals(MONTH, studentCard.getValidity());
        assertEquals(FIVE, studentCard.getTripNumber());
    }

    @Test
    public void shouldIssueDefaultCardWithSomeParameters() {
        Card studentCard = cardsRegistry.issueDefaultCard(MONTH, FIVE, LocalDateTime.now());
        assertEquals(0L, studentCard.getId());
        assertEquals(DEFAULT, studentCard.getCardType());
        assertEquals(MONTH, studentCard.getValidity());
        assertEquals(FIVE, studentCard.getTripNumber());
    }

    @Test
    public void shouldIssueCumulativeCardWithSomeParameters() {
        BigDecimal amount = new BigDecimal("30");
        Card card = cardsRegistry.issueCumulativeCard(amount);
        CumulativeCard cumulativeCard = (CumulativeCard) card;
        assertEquals(0L, cumulativeCard.getId());
        assertEquals(DEFAULT, cumulativeCard.getCardType());
        assertEquals(UNLIMITED, cumulativeCard.getValidity());
        assertEquals(TripNumber.UNLIMITED, cumulativeCard.getTripNumber());
        assertEquals(amount, cumulativeCard.getBalance());
    }

    @Test
    public void shouldPayIncreaseCardAmonyBy30() {
        BigDecimal defaultAmount = new BigDecimal("30");
        BigDecimal additionalAmount = new BigDecimal("100");
        BigDecimal expectedAmount = defaultAmount.add(additionalAmount);
        CumulativeCard cumulativeCard = (CumulativeCard) cardsRegistry
                .issueCumulativeCard(defaultAmount);
        boolean payed = cardsRegistry.payCumulativeCard(cumulativeCard, additionalAmount);
        assertTrue(payed);
        assertEquals(expectedAmount, cumulativeCard.getBalance());
    }

    @Test
    public void shouldNotPayForFakeCard() {
        assertFalse(cardsRegistry.payCumulativeCard(new CumulativeCard(), new BigDecimal("55")));
    }

    @Test
    public void shouldClearStoredData() {
        cardsRegistry.issueDefaultCard(MONTH, FIVE, LocalDateTime.now());
        Card studentCard = cardsRegistry.issueDefaultCard(MONTH, FIVE, LocalDateTime.now());
        assertEquals(1L, studentCard.getId());
        CardsRegistry.clearRegister();
        studentCard = cardsRegistry.issueDefaultCard(MONTH, FIVE, LocalDateTime.now());
        assertEquals(0L, studentCard.getId());
    }

    @Test
    public void shouldFindFakeCard() {
        LocalDateTime now = LocalDateTime.now();
        cardsRegistry.issueDefaultCard(MONTH, FIVE, now);
        cardsRegistry.issueDefaultCard(MONTH, FIVE, now);
        DefaultCard fakeCard = new DefaultCard();
        fakeCard.setId(3L);
        fakeCard.setValidity(MONTH);
        fakeCard.setTripNumber(FIVE);
        fakeCard.setCreationTime(now);
        assertFalse(cardsRegistry.isNotFakeCard(fakeCard));
    }

}