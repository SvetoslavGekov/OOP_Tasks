package orders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cakes.Cake;
import demo.Demo;
import people.IClient;
import shop.CakeShop;

public class Order {
	
	//Fields
	private IClient client;
	private double totalCost;
	private String address;
	private Set<Cake> cakes = new HashSet<>();
	private LocalDateTime deliveryInfo;
	private CakeShop shop;

	
	//Constr
	public Order(IClient client, String address, HashSet<Cake> cakes, LocalDateTime deliveryInfo, double cost, CakeShop shop) {
		setClient(client);
		setAddress(address);
		setCakes(cakes);
		setDeliveryInfo(deliveryInfo);
		setTotalCost(cost);
		setShop(shop);
		
	}

	//Setters
	private void setClient(IClient client) {
		if(client != null) {
			this.client = client;
		}
	}


	private void setAddress(String address) {
		if(Demo.validStr(address)) {
			this.address = address;
		}
	}

	private void setShop(CakeShop shop) {
		this.shop = shop;
	}
	
	private void setDeliveryInfo(LocalDateTime deliveryInfo) {
		if(deliveryInfo != null) {
			this.deliveryInfo = deliveryInfo;
		}
	}
	
	private void setCakes(HashSet<Cake> cakes) {
		if(cakes != null) {
			this.cakes = new HashSet<>(cakes);
		}
	}
	
	public void setTotalCost(double totalCost) {
		if(totalCost >= 0) {
			this.totalCost = totalCost;
		}
	}

	public IClient getClient() {
		return this.client;
	}
	
	public double getTotalCost() {
		return this.totalCost;
	}
	
	public CakeShop getShop() {
		return this.shop;
	}
}
