package mebeli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import eggs.Egg;
import eggs.EggType;
import people.Mom;

public class Fridge {
	//Fields
	private Map<EggType, ArrayList<Egg>> kori = new TreeMap<EggType, ArrayList<Egg>>();
	
	public Fridge() {
		for (EggType type : EggType.values()) {
			kori.put(type, new ArrayList<Egg>());
		}
	}
	
	//Methods
	public void putEggInFridge(Egg egg, Mom mom) {
		EggType type = egg.getEggType();
		for (Entry<EggType, ArrayList<Egg>> kora: this.kori.entrySet()) {
			if(type == kora.getKey()) {
				kora.getValue().add(egg);
				System.out.println(this + " put " + egg + " in the fridge");
			}
		}
	}
	
	public Map<EggType, ArrayList<Egg>> getKori() {
		return Collections.unmodifiableMap(this.kori);
	}
}
