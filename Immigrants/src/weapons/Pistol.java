package weapons;

import demo.Demo;

public class Pistol extends Weapon {

	public Pistol(double cost) {
		super(WeaponType.PISTOL, cost);
	}
	
	@Override
	public int fire() {
		int bullets = Demo.RNG(1, 17);
		System.out.println(String.format("Pistol fired %d rounds.", bullets));
		
		//return casualties
		return bullets * Demo.RNG(10, 70)/100;
	}

}
