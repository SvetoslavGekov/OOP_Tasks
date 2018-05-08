package shop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import cakes.Cake;
import cakes.ICakeSubType;
import cakes.ICakeType;
import cakes.WeddingCake;
import people.Supplier;
import demo.Demo;
import demo.Entity;
import orders.Order;

public class CakeShop extends Entity {
	//Fields
	private String address;
	private TreeSet<Supplier> suppliers = new TreeSet<>(Supplier.CompareByTipsDesc);
	private TreeMap<ICakeType, TreeMap<ICakeSubType, TreeSet<Cake>>> cakes = new TreeMap<ICakeType, TreeMap<ICakeSubType, TreeSet<Cake>>>();
	
	//Constr
	public CakeShop(String name, String phone, double startingMoney) {
		super(name, phone, startingMoney);
		System.out.println(this);
	}

	//Methods
	@Override
	public String toString() {
		return String.format("Name: %s	Phone: %s	Money: %.2f", this.getName(), this.getPhone(), this.getMoney());
	}
	
	public void printAllCakes() {
		int counter = 0;
		System.out.println("\n ============== CAKES IN SHOP ==========");
		for (Entry<ICakeType,TreeMap<ICakeSubType, TreeSet<Cake>>> cakeTypeEntry : this.cakes.entrySet()) {
			
			for (Entry<ICakeSubType, TreeSet<Cake>> cakeSubTypeEntry : cakeTypeEntry.getValue().entrySet()) {
				for (Cake cakeSet : cakeSubTypeEntry.getValue()) {
					System.out.println(cakeSet);
					counter ++;
				}
			}
		}
		System.out.println(String.format("Total cakes in shop: %d", counter));
	}
	
	public void addCake(Cake cake) {
		ICakeType cakeType = cake.getType();
		ICakeSubType subType = cake.getSubType();
		
		//If there is no such CakeType
		if(!this.cakes.containsKey(cakeType)) {
			this.cakes.put(cakeType, new TreeMap<ICakeSubType, TreeSet<Cake>>());
		}
		
		//If there is such CakeType, but no CakeSubType
		if(this.cakes.containsKey(cakeType)){
			if(!this.cakes.get(cakeType).containsKey(subType)) {
				this.cakes.get(cakeType).put(subType, new TreeSet<Cake>());
			}
		}
		
		//Add the cake
		this.cakes.get(cakeType).get(subType).add(cake);
	}

	public Cake getRandomCake() {
		//Cake type
		ArrayList<ICakeType> cakeTypeList = new ArrayList<>(this.cakes.keySet());
		ICakeType cakeType = cakeTypeList.get(Demo.RNG(cakeTypeList.size()));
		
		//Cake sub type
		ArrayList<ICakeSubType> cakeSubTypeList = new ArrayList<>(this.cakes.get(cakeType).keySet());
		ICakeSubType cakeSubType = cakeSubTypeList.get(Demo.RNG(cakeSubTypeList.size()));
		
		//Cake
		ArrayList<Cake> cakes = new ArrayList<>(this.cakes.get(cakeType).get(cakeSubType));
		if(cakes.size() > 0) {
			Cake cake = cakes.get(Demo.RNG(cakes.size()));
			cake.addSale();
			
			//Remove cake
			cakes.remove(cake);
			this.cakes.get(cakeType).put(cakeSubType, new TreeSet<Cake>(cakes));
			
			return cake;
		}
		return null;
	}

	private Supplier getRandomSupplier() {
		ArrayList<Supplier> sups = new ArrayList<>(this.suppliers);
		Supplier sup = sups.get(Demo.RNG(sups.size()));
		return sup;
	}
	
	public void fullfillOrder(Order ord) {
		//Assign supplier
		Supplier sup = getRandomSupplier();
		sup.finalizeOrder(ord);
	}

	public void hireSupplier(Supplier sup) {
		if(sup != null) {
			this.suppliers.add(sup);
		}
	}

	public void printSuppliers() {
		TreeSet<Supplier> newSet = new TreeSet<>();
		for (Iterator iterator = suppliers.iterator(); iterator.hasNext();) {
			Supplier supplier = (Supplier) iterator.next();
			newSet.add(supplier);
		}
		
		for (Iterator iterator = newSet.iterator(); iterator.hasNext();) {
			Supplier supplier = (Supplier) iterator.next();
			System.out.println(supplier);
		}
		this.suppliers = newSet;
	}
	
	public void printProfits() {
		System.out.println(this);
		System.out.println(String.format("Total profits: %.2f", this.getMoney() - this.getStartingMoney()));
	}
	
	public void printSupplierWithMostOrders() {
		Supplier sup = null;
		int maxOrders = 0;
		for (Supplier supplier : suppliers) {
			if(supplier.getTotalOrders() > maxOrders) {
				sup = supplier;
				maxOrders = supplier.getTotalOrders();
			}
		}
		
		System.out.println(String.format("Supplier with most orders is: %s  and a total of %d orders", sup, maxOrders));
	}
	
	public void printMostSoldCakes() {
		Cake cake = null;
		int maxSales = 0;
		System.out.println("\n ============== CAKES IN SHOP ==========");
		for (Entry<ICakeType,TreeMap<ICakeSubType, TreeSet<Cake>>> cakeTypeEntry : this.cakes.entrySet()) {
			
			for (Entry<ICakeSubType, TreeSet<Cake>> cakeSubTypeEntry : cakeTypeEntry.getValue().entrySet()) {
				for (Cake cakeSet : cakeSubTypeEntry.getValue()) {
					System.out.println(cakeSet);
					if(cakeSet.getSales() > maxSales) {
						cake = cakeSet;
						maxSales = cakeSet.getSales();
					}
				}
			}
		}
		System.out.println(String.format("Cakes with maximum sales are %s with a total of %d sales. ", cake.getType(), cake.getSales()));
	}
}
