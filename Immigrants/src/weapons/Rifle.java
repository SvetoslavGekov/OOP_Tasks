package weapons;

import demo.Demo;

public class Rifle extends Weapon {

	public Rifle(double cost) {
		super(WeaponType.RIFLE, cost);
	}

	@Override
	public int fire() {
		int bullets = Demo.RNG(1,30);
		System.out.println(String.format("Rifle fired %d rounds.", bullets));
		
		//return casualties
		return bullets * Demo.RNG(10, 70)/100;
	}

}
