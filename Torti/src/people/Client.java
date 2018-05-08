package people;

import java.time.LocalDateTime;
import java.util.HashSet;

import cakes.Cake;
import demo.Demo;
import demo.Entity;
import orders.Order;
import shop.CakeShop;

public abstract class Client extends Entity implements IClient {
	
	//field
	private double tipPercent;
	
	public Client(String name, String phone, double startingMoney) {
		super(name, phone, startingMoney);
	}
	
	@Override
	public String toString() {
		return String.format("%s Phone: %s Money: %s", super.getName(),super.getPhone(), super.getMoney() );
	}
	
	//Setters
	protected void setTipPercent(double tipPercent) {
		if(tipPercent >= 0) {
			this.tipPercent = tipPercent;
		}
	}
	
	public double getTipPercent() {
		return this.tipPercent;
	}
	
	@Override
	public void orderCakes(CakeShop shop) {
		//Determine cake count
		int cakeCount = countCakes();
		
		//Create a set of random cakes
		HashSet<Cake> cakes = new HashSet<>();
		while(cakeCount > 0) {
			Cake cake = shop.getRandomCake();
			if(cake != null) {
				if(cakes.contains(cake)) {
					continue;
				}
				cakes.add(cake);
				cakeCount --;
			}
		}
		//Calculate cost
		double totalCost = 0d;
		for (Cake cake : cakes) {
			totalCost += cake.getCost();
		}
		
		totalCost = changeOrderCost(totalCost);
		
		//Create order
		Order ord = new Order(this, "Random addres", cakes, LocalDateTime.now().plusDays(Demo.RNG(1,2)), totalCost, shop);
		System.out.println(String.format("%s made an order of %d cakes worth %.2f", this, cakes.size(), totalCost));
		shop.fullfillOrder(ord);
	}
	
	protected abstract double changeOrderCost(double cost);
	protected abstract int countCakes();
	
}
