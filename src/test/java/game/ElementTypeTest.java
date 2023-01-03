package game;

import game.card.ElementType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementTypeTest {

    ElementType elementType1;
    ElementType elementType2;

    @Test
    void testFireVsNormal()
    {
        elementType1 = ElementType.fire;
        elementType2 = ElementType.normal;

        double modifier1 = elementType1.getModifier(elementType2);
        double modifier2 = elementType2.getModifier(elementType1);

        assertEquals(2, modifier1);
        assertEquals(0.5, modifier2);
    }

    @Test
    void testFireVsWater()
    {
        elementType1 = ElementType.fire;
        elementType2 = ElementType.water;

        double modifier1 = elementType1.getModifier(elementType2);
        double modifier2 = elementType2.getModifier(elementType1);

        assertEquals(0.5, modifier1);
        assertEquals(2, modifier2);
    }

    @Test
    void testNormalVsWater()
    {
        elementType1 = ElementType.normal;
        elementType2 = ElementType.water;

        double modifier1 = elementType1.getModifier(elementType2);
        double modifier2 = elementType2.getModifier(elementType1);

        assertEquals(2, modifier1);
        assertEquals(0.5, modifier2);
    }

    @Test
    void testFireVsFire()
    {
        elementType1 = ElementType.fire;
        elementType2 = ElementType.fire;

        double modifier = elementType1.getModifier(elementType2);

        assertEquals(1, modifier);
    }

    @Test
    void testNormalVsNormal()
    {
        elementType1 = ElementType.normal;
        elementType2 = ElementType.normal;

        double modifier = elementType1.getModifier(elementType2);

        assertEquals(1, modifier);
    }

    @Test
    void testWaterVsWater()
    {
        elementType1 = ElementType.water;
        elementType2 = ElementType.water;

        double modifier = elementType1.getModifier(elementType2);

        assertEquals(1, modifier);
    }
}