package people;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cakes.Cake;
import cakes.ChildCake;
import demo.Demo;
import orders.Order;
import shop.CakeShop;

public final class CorporateClient extends Client {
	private static final int MIN_CAKES = 3;
	private static final int MAX_CAKES = 5;
	private static final double DISCOUNT = 0.10d;
	private static final double TIP_PERCENT = 0.05d;
	
	//Fields
	private double discount = DISCOUNT;
	
	//Constr
	public CorporateClient(String name, String phone, double startingMoney) {
		super(name, phone, startingMoney);
		setTipPercent(TIP_PERCENT);
		
	}
	
	//Methods
	@Override
	public String toString() {
		return super.toString() + " Discount: " + this.discount;
	}
	
	@Override
	protected int countCakes() {
		return Demo.RNG(MIN_CAKES,MAX_CAKES);
	}

	
	@Override
	protected double changeOrderCost(double cost) {
		return cost - cost * this.discount;
	}

}
