package products;

import demo.Village;

public enum FruitType {
	GRAPES, PLUM, APRICOT;
	
	
	public static final FruitType getRandomFruitType() {
		FruitType[] fruits = FruitType.values();
		return fruits[Village.RNG(fruits.length)];
	}
}
