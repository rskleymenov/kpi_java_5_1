package roman.kleimenov.kpi.lab3;

import roman.kleimenov.kpi.lab3.cards.Card;
import roman.kleimenov.kpi.lab3.cards.impl.CumulativeCard;
import roman.kleimenov.kpi.lab3.cards.impl.DefaultCard;
import roman.kleimenov.kpi.lab3.cards.impl.SchoolCard;
import roman.kleimenov.kpi.lab3.cards.impl.StudentCard;

public class CardFactory {
    private static final CardFactory INSTANCE = new CardFactory();

    private CardFactory() {
    }

    public static CardFactory getInstance() {
        return INSTANCE;
    }

    public Card getDefaultCard() {
        return new DefaultCard();
    }

    public Card getSchoolCard() {
        return new SchoolCard();
    }

    public Card getStudentCard() {
        return new StudentCard();
    }

    public Card getCumulativeCard() {
        return new CumulativeCard();
    }

}
