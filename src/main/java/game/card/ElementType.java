package game.card;

public enum ElementType {
        fire, water, normal;

        public double getModifier(ElementType elementType) {
                if(this == elementType) {
                        return 1;
                } else if (this == ElementType.fire && elementType == ElementType.normal) {
                        return 2;
                } else if (this == ElementType.water && elementType == ElementType.fire) {
                        return 2;
                } else if (this == ElementType.normal && elementType == ElementType.water) {
                        return 2;
                }
                return 0.5;
        }
}

