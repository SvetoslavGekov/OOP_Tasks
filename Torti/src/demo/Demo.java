package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import cakes.Cake;
import cakes.ChildCake;
import cakes.SpecialCake;
import cakes.StandardCake;
import cakes.WeddingCake;
import people.Client;
import people.CorporateClient;
import people.IClient;
import people.PrivateClient;
import people.Supplier;
import shop.CakeShop;

public class Demo {
	public static final int RNG(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}

	public static  final int RNG(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}
	
	public static final boolean validStr(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	public static void main(String[] args) {
		
		//1. Cake shop creation
		System.out.println("=============== CAKE SHOP CREATION =============");
		CakeShop shop = new CakeShop("Sweet Talents", "1234", 1500d);
		ArrayList<Supplier> sups = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Supplier sup = new Supplier("Supplier " + (i+1), "2134", 0d);
			shop.hireSupplier(sup);
		}
		
		//2. Create 30 random cakes
		for (int i = 0; i < 30; i++) {
			Cake cake = createRandomCake();
			shop.addCake(cake);
		}
		
		shop.printAllCakes();
		
		//3. Create 10 random clients
		System.out.println("\n=========== CLIENTS CREATION ==========");
		ArrayList<IClient> clients = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			IClient cl = null;
			String name = "Client " + (i+1);
			String phone = "Phone " + (i+1);
			Double startingMoney = (double) Demo.RNG(200, 1500);
			
			if(i < 5) {
				cl = new CorporateClient(name, phone, startingMoney);
			}
			else {
				cl = new PrivateClient(name, phone, startingMoney);
			}
			clients.add(cl);
			System.out.println(cl);
			
			//4. Clients order cakes	
			cl.orderCakes(shop);
		}
		
		//5. Print all cakes
		shop.printAllCakes();
		
		//6. Print shop info
		System.out.println("============ SHOP INFO========");
		shop.printProfits();
		
		//7. Print suppliers
		System.out.println("======= SUPPLIERS ======");
		shop.printSuppliers();
		
		//8. Print most sold cakes
		System.out.println("======== MOST SOLD CAKES =======");
		shop.printMostSoldCakes();
		
		//9. Print supplier with most orders
		System.out.println("\n=========== SUPP WITH MOST ORDERS ========");
		shop.printSupplierWithMostOrders();
		
		//10. Print client with maximum sum for cakes
		System.out.println("\n=========== CLIENT WITH MAX CAKE SUMS ========");
		IClient cl = null;
		double max = 0d;
		for (IClient iClient : clients) {
			if(iClient.getStartingMoney() - iClient.getMoney() > max) {
				cl = iClient;
				max = iClient.getStartingMoney() - iClient.getMoney();
			}
		}
		
		System.out.println("Client with max payment for cakes is " + cl + " with a max payment of " + max);
		
		Cake cake1 = new ChildCake("2", "3", 3d, 3, "2", ChildCake.CakeSubTypes.BIRTHDAY);
		Cake cake2 = new ChildCake("1", "2", 3d, 3, "2", ChildCake.CakeSubTypes.NAMING);
		System.out.println(cake1.hashCode() + "  " + cake2.hashCode());
		System.out.println(cake1.equals(cake2));
	}
	
	static Cake createRandomCake() {
		int cakeType = Demo.RNG(4);
		int cakeName = Demo.RNG(0, 15_000);
		double cost = Demo.RNG(20, 51);
		int slices = Demo.RNG(1, 21);
		int levels = Demo.RNG(1, 5);
		String eventName = "Event " + Demo.RNG(50); 
		String childName = "Child " + Demo.RNG(100);
		
		switch(cakeType) { 
			case 0: return new StandardCake("Cake " + cakeName, "Desc", cost, slices, new Random().nextBoolean(), StandardCake.getRNGCakeSubType());
			case 1: return new WeddingCake("Cake " + cakeName, "Desc", cost, slices, levels, WeddingCake.getRNGCakeSubType());
			case 2: return new SpecialCake("Cake " + cakeName, "Desc", cost, slices, eventName, SpecialCake.getRNGCakeSubType());
			case 3: return new ChildCake("Cake " + cakeName, "Desc", cost, slices, childName, ChildCake.getRNGCakeSubType());
		}
		
		return null;
	}
}
