package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementTypeTest {

    ElementType elementType1;
    ElementType elementType2;

    @Test
    void testFirevsNormal()
    {
        elementType1 = ElementType.fire;
        elementType2 = ElementType.normal;

        double Modifier = elementType1.getModifier(elementType2);

        assertEquals(2, Modifier);
    }

    @Test
    void testNormalvsFire()
    {
        elementType1 = ElementType.normal;
        elementType2 = ElementType.fire;

        double Modifier = elementType1.getModifier(elementType2);

        assertEquals(0.5, Modifier);
    }

    @Test
    void testFirevsFire()
    {
        elementType1 = ElementType.fire;
        elementType2 = ElementType.fire;

        double Modifier = elementType1.getModifier(elementType2);

        assertEquals(1, Modifier);
    }
}