package demo;

import java.util.ArrayList;
import java.util.Random;

import mebeli.Fridge;
import mebeli.Pot;
import mebeli.Table;
import people.Granny;
import people.Kid;
import people.Mom;

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
		//1. Create pot with eggs
		Pot pot = new Pot();
		
		//2. Create table and fridge
		Table table = new Table();
		Fridge fridge = new Fridge();
		
		
		//3. Create 3 kids
		ArrayList<Kid> kids = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Kid k = new Kid(pot, table);
		}
		
		//4. Create 1 mom and 1 father
		Mom mom = new Mom(table, fridge);
		
		//5. Create granny
		Granny granny = new Granny();
	}
}
