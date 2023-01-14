package game.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    private Card monsterCardDragon;
    private Card monsterCardGoblin;
    private Card spellCard;
    private Card monsterCardOrk;
    private Card enemySpellCard;
    private Card monsterCardWizard;
    private Card monsterCardKnight;
    private Card waterSpellCard;
    private Card monsterCardKraken;
    private Card monsterCardFireElf;

    @BeforeEach
    void init() {
        monsterCardDragon = new Card("Dragon", 10.0, ElementType.fire, "fd5fb3ca-3ee2-4314-87ba-17c4545b2253", CardType.monster);
        monsterCardGoblin = new Card("Goblin", 10.0, ElementType.fire, "fd5fb3ca-3ee2-4314-87ba-17c4545b2253", CardType.monster);
        monsterCardOrk = new Card("Ork", 10.0, ElementType.normal, "263198b3-b9fb-46de-9a38-d4fc686aa2de", CardType.monster);
        monsterCardWizard = new Card("Wizard", 10.0, ElementType.normal, "263198b3-b9fb-46de-9a38-d4fc686aa2de", CardType.monster);
        monsterCardKnight = new Card("Knight", 10.0, ElementType.normal, "263198b3-b9fb-46de-9a38-d4fc686aa2de", CardType.monster);
        monsterCardKraken = new Card("Kraken", 10.0, ElementType.normal, "263198b3-b9fb-46de-9a38-d4fc686aa2de", CardType.monster);
        monsterCardFireElf = new Card("FireElf", 10.0, ElementType.fire, "263198b3-b9fb-46de-9a38-d4fc686aa2de", CardType.monster);
        spellCard = new Card("Spell", 10.0, ElementType.fire, "fd5fb3ca-3ee2-4314-87ba-17c4545b2253", CardType.spell);
        enemySpellCard = new Card("Spell1", 10.0, ElementType.normal, "9e21a940-6812-48ec-abc7-2029095ec637", CardType.spell);
        waterSpellCard = new Card("WaterSpell", 10.0, ElementType.water, "fd5fb3ca-3ee2-4314-87ba-17c4545b2253", CardType.spell);
    }

    @Test
    void testCalculatedDamageWithTwoMonsterCards() {
        double calculatedDamageCard1 = monsterCardDragon.getCalculatedDamage(monsterCardOrk);
        double calculatedDamageCard2 = monsterCardOrk.getCalculatedDamage(monsterCardDragon);

        assertEquals(10.0, calculatedDamageCard1);
        assertEquals(10.0, calculatedDamageCard2);
    }

    @Test
    void testCalculatedDamageWithSpellCardAndMonsterCard() {
        double calculatedDamageCard1 = monsterCardDragon.getCalculatedDamage(enemySpellCard);
        double calculatedDamageCard2 = enemySpellCard.getCalculatedDamage(monsterCardDragon);

        assertEquals(20.0, calculatedDamageCard1);
        assertEquals(5.0, calculatedDamageCard2);

    }

    @Test
    void testCalculatedDamageWithTwoSpellCards() {
        double calculatedDamageCard1 = spellCard.getCalculatedDamage(enemySpellCard);
        double calculatedDamageCard2 = enemySpellCard.getCalculatedDamage(spellCard);

        assertEquals(20.0, calculatedDamageCard1);
        assertEquals(5.0, calculatedDamageCard2);
    }

    @Test
    void testCalculatedDamageWithSpecialRuleDragonGoblin() {
        double calculatedDamageCard1 = monsterCardGoblin.getCalculatedDamage(monsterCardDragon);
        double calculatedDamageCard2 = monsterCardDragon.getCalculatedDamage(monsterCardGoblin);

        assertEquals(0, calculatedDamageCard1);
        assertEquals(10.0, calculatedDamageCard2);
    }

    @Test
    void testCalculatedDamageWithSpecialRuleWizardOrks() {
        double calculatedDamageCard1 = monsterCardOrk.getCalculatedDamage(monsterCardWizard);
        double calculatedDamageCard2 = monsterCardWizard.getCalculatedDamage(monsterCardOrk);

        assertEquals(0, calculatedDamageCard1);
        assertEquals(10.0, calculatedDamageCard2);
    }

    @Test
    void testCalculatedDamageWithSpecialRuleKnightWaterSpell() {
        double calculatedDamageCard1 = monsterCardKnight.getCalculatedDamage(waterSpellCard);
        double calculatedDamageCard2 = waterSpellCard.getCalculatedDamage(monsterCardKnight);

        assertEquals(0, calculatedDamageCard1);
        assertEquals(5.0, calculatedDamageCard2);
    }

    @Test
    void testCalculatedDamageWithSpecialRuleKrakenSpell() {
        double calculatedDamageCard1 = monsterCardKraken.getCalculatedDamage(spellCard);
        double calculatedDamageCard2 = spellCard.getCalculatedDamage(monsterCardKraken);

        assertEquals(5.0, calculatedDamageCard1);
        assertEquals(0, calculatedDamageCard2);
    }

    @Test
    void testCalculatedDamageWithSpecialRuleFireElfDragon() {
        double calculatedDamageCard1 = monsterCardDragon.getCalculatedDamage(monsterCardFireElf);
        double calculatedDamageCard2 = monsterCardFireElf.getCalculatedDamage(monsterCardDragon);

        assertEquals(0, calculatedDamageCard1);
        assertEquals(10.0, calculatedDamageCard2);
    }
}