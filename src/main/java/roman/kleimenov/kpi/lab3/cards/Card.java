package roman.kleimenov.kpi.lab3.cards;

import roman.kleimenov.kpi.lab3.enums.CardType;
import roman.kleimenov.kpi.lab3.enums.TripNumber;
import roman.kleimenov.kpi.lab3.enums.Validity;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Card {
    private long id;
    private CardType cardType;
    private Validity validity;
    private TripNumber tripNumber;
    private int usedTrips;
    private LocalDateTime creationTime;

    public Card(CardType cardType) {
        this.cardType = cardType;
        creationTime = LocalDateTime.now();
    }

    public Card(CardType cardType, Validity validity, TripNumber tripNumber) {
        this.cardType = cardType;
        this.validity = validity;
        this.creationTime = LocalDateTime.now();
        this.tripNumber = tripNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }

    public TripNumber getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(TripNumber tripNumber) {
        this.tripNumber = tripNumber;
    }

    public int getUsedTrips() {
        return usedTrips;
    }

    public void setUsedTrips(int usedTrips) {
        this.usedTrips = usedTrips;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
