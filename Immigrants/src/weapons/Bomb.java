package weapons;

public class Bomb extends Weapon {

	public Bomb(double cost) {
		super(WeaponType.BOMB, cost);
	}

	@Override
	public int fire() {
		System.out.println("Boom goes the bomb!");
		return 100;
	}

}
