package demo;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import people.InvalidPersonDataException;
import people.mailman.Mailman;

public class JSON_DEMO {

	public static void main(String[] args) throws InvalidPersonDataException, IOException {
		Mailman m = new Mailman("Gosho", 5, true);
		
		Gson g = new Gson();
		FileWriter fw = new FileWriter("Jik_tak.json",true);
		g.toJson(m, fw);
		fw.close();
	}

}
