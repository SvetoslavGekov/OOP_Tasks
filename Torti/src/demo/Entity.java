package demo;

public abstract class Entity {
	//Fields
	private String name;
	private String phone;
	private double money;
	private final double startingMoney;

	
	//Constr
	public Entity(String name, String phone, double startingMoney) {
		setName(name);
		setPhone(phone);
		setMoney(startingMoney);
		this.startingMoney = setStartingMoney(startingMoney);
	}


	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}


	private void setPhone(String phone) {
		if(Demo.validStr(phone)) {
			this.phone = phone;
		}
	}


	public void setMoney(double money) {
		if(money > 0d) {
			this.money = money;
		}
	}
	
	private double setStartingMoney(double money) {
		if(money > 0) {
			return money;
		}
		return 0d;
	}

	//Getters
	protected String getName() {
		return this.name;
	}


	protected String getPhone() {
		return this.phone;
	}


	public double getMoney() {
		return this.money;
	}
	public double getStartingMoney() {
		return this.startingMoney;
	}
	
	
}
