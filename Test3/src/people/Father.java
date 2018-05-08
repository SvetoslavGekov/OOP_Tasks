package people;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import demo.DBManager;
import eggs.Egg;
import eggs.EggType;
import mebeli.Fridge;

public class Father extends Person {
	//Fields
	private Fridge fridge;
	private static final Connection CON = DBManager.getInstance().getCon();
	private static final String EGG_FILE = "Eggs.txt";
	
	public Father(Fridge fridge) {
		super("Father");
		setFridge(fridge);
	}
	
	//Methods
	public void archiveEggs() {
		Map<EggType, ArrayList<Egg>> kori = this.fridge.getKori();
		File f = new File(EGG_FILE);
		
		try(FileWriter fw = new FileWriter(f)){
			for (Entry<EggType, ArrayList<Egg>> kora: kori.entrySet()) {
				fw.write(kora.getKey() + " \n");
				for (Egg egg : kora.getValue()) {
					fw.write(egg.getInfo());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveEggInDb(Egg egg) {
		try(PreparedStatement ps = CON.prepareStatement("INSERT INTO eggs (type_id, is_partycolored, date, jar_id,kid_id)"
				+ "VALUES (?,?,?,?,?) ");){
			//Set egg info
			ps.setInt(1, egg.getEggType().getId());
			ps.setBoolean(2, egg.isPartyColored());
			ps.setDate(3, java.sql.Date.valueOf(egg.getColoredTime().toLocalDate()));
			ps.setInt(4, egg.getJar_id());
			ps.setInt(5, egg.getKid().getId());
			
			//Execute update
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setFridge(Fridge fridge) {
		if(fridge != null) {
			this.fridge = fridge;
		}
	}
}


