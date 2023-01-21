package game;

import game.card.ElementType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementTypeTest {

    public static Stream<Arguments> providedParametersForTestElementVsElement() {
        return Stream.of(
                Arguments.of(ElementType.fire, ElementType.normal, 2, 0.5),
                Arguments.of(ElementType.fire, ElementType.water, 0.5, 2),
                Arguments.of(ElementType.normal, ElementType.water, 2, 0.5),
                Arguments.of(ElementType.fire, ElementType.fire, 1, 1),
                Arguments.of(ElementType.water, ElementType.water, 1, 1),
                Arguments.of(ElementType.normal, ElementType.normal, 1, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("providedParametersForTestElementVsElement")
    void testElementVSElement(ElementType elementType1, ElementType elementType2, double expectedModifier1, double expectedModifier2)
    {
        double modifier1 = elementType1.getModifier(elementType2);
        double modifier2 = elementType2.getModifier(elementType1);

        assertEquals(expectedModifier1, modifier1);
        assertEquals(expectedModifier2, modifier2);
    }
}