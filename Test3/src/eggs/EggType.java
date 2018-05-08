package eggs;

import demo.Demo;

public enum EggType {
	CHICKEN(10,1), DUCK(3,2), GYSOK(5,3);
	
	private int processingTime;
	private int id;
	
	EggType(int processingTime, int id){
		this.processingTime = processingTime;
		this.id = id;
	}
	
	public static EggType getRandomEggType() {
		EggType[] types = EggType.values();
		return types[Demo.RNG(types.length)];
	}
	
	public int getProcessingTime() {
		return processingTime;
	}
	
	public int getId() {
		return id;
	}
}
