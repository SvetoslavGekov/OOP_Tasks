package people;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

import demo.Entity;
import orders.Order;
import shop.CakeShop;

public final class Supplier extends Entity implements Comparable<Supplier> {
	//Fields
	private Set<Order> orders = new HashSet<>();
	private double tips = 0d;
	
	
	@Override
	public int compareTo(Supplier o) {
		return Double.compare(o.tips, this.tips) >= 0 ? 1 : -1;
	}
	
	//Constr
	public Supplier(String name, String phone, double startingMoney) {
		super(name, phone, startingMoney);
	}
	
	
	public static Comparator<Supplier> CompareByTipsDesc = new Comparator<Supplier>() {
		public int compare(Supplier o1, Supplier o2) {
			return Double.compare(o2.getTips(), o1.getTips()) >= 0 ? 1 : -1;
		};
	};

	@Override
	public String toString() {
		return String.format("Name: %s Tips: %.2f", this.getName(), this.getTips());
	}
	
	public void setTips(double tips) {
		if(tips > 0) {
			this.tips = tips;
		}
	}
	
	public double getTips() {
		return this.tips;
	}

	public void finalizeOrder(Order ord) {
		this.orders.add(ord);
		IClient client = ord.getClient();
		double tipAmount = ord.getTotalCost() * client.getTipPercent();
		
		//Grab money
		client.setMoney(client.getMoney() - tipAmount - ord.getTotalCost());
		//Grab tip
		this.setMoney(this.getMoney() + tipAmount);
		this.setTips(this.getTips() + tipAmount);
		
		//Send money to shopec
		CakeShop shop = ord.getShop();
		shop.setMoney(shop.getMoney() + ord.getTotalCost());
		System.out.println(String.format("%s finalized an order for %s\n", this, client));
	}

	public int getTotalOrders() {
		return this.orders.size();
	}
}
