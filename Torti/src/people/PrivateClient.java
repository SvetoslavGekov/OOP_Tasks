package people;

import java.util.ArrayList;
import java.util.Iterator;

import demo.Demo;
import shop.CakeShop;

public final class PrivateClient extends Client {
	private static final int MAX_VAUCHER_PRICE = 30;
	private static final int MIN_VAUCHER_PRICE = 10;
	private static final int MAX_VAUCHERS = 4;
	private static final int MIN_VAUCHERS = 1;
	private static final int MIN_CAKES = 1;
	private static final int MAX_CAKES = 3;
	private static final double TIP_PERCENT = 0.02d;
	
	//Fields
	private ArrayList<Double> vauchers = new ArrayList<>();
	
	
	//Constr
	public PrivateClient(String name, String phone, double startingMoney) {
		super(name, phone, startingMoney);
		setVauchers();
		setTipPercent(TIP_PERCENT);
	}
	
	
	//Methods
	@Override
	public String toString() {
		return super.toString() + String.format(" %s", this.vauchers);
	}
	
	//Setters
	public void setVauchers() {
		for (int i = 0; i < Demo.RNG(MIN_VAUCHERS, MAX_VAUCHERS + 1); i++) {
			this.vauchers.add((double) Demo.RNG(MIN_VAUCHER_PRICE, MAX_VAUCHER_PRICE));
		}
	}
	
	@Override
	protected int countCakes() {
		return Demo.RNG(MIN_CAKES,MAX_CAKES);
	}


	@Override
	protected double changeOrderCost(double cost) {
		for (Iterator iterator = vauchers.iterator(); iterator.hasNext();) {
			Double double1 = (Double) iterator.next();
			cost -= double1;
			iterator.remove();
		}
		return cost > 0 ? cost : 0d;
	}

}
