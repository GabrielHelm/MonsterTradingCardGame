package Game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementTypeTest {

    @Test
    void testFirevsNormal()
    {
        ElementType elementType1 = ElementType.fire;
        ElementType elementType2 = ElementType.normal;

        double Modifier = elementType1.getModifier(elementType2);

        assertEquals(2, Modifier);
    }

    @Test
    void testNormalvsFire()
    {
        ElementType elementType1 = ElementType.normal;
        ElementType elementType2 = ElementType.fire;

        double Modifier = elementType1.getModifier(elementType2);

        assertEquals(0.5, Modifier);
    }

    @Test
    void testFirevsFire()
    {
        ElementType elementType1 = ElementType.fire;
        ElementType elementType2 = ElementType.fire;

        double Modifier = elementType1.getModifier(elementType2);

        assertEquals(1, Modifier);
    }
}