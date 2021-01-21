package main;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
	/*
	public static void testWriteJson() {
		String jsonString = new JSONObject()
                .put("JSON1", "Hello World!")
                .put("JSON2", "Hello my World!")
                .put("JSON3", new JSONObject().put("key1", "value1"))
                .toString();

		System.out.println(jsonString);
	}
	*/
	
	public static void readJsonFromFile() {
	  JSONParser parser = new JSONParser();
	  
	  //JSONObject a = (JSONObject) parser.parse(new FileReader(FilePaths.environmentFile));
		Object obj;
		try {
			obj = parser.parse(new FileReader(FilePaths.environmentFile));
		 

		  JSONObject jsonObject = (JSONObject) obj;  
		  String pageName = jsonObject.get("FileName").toString();
		  System.out.println(pageName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}
	
	
	public static void main(String[] args) {
		//testWriteJson();
		readJsonFromFile();
	}

}
