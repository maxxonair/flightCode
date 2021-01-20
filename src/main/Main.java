package main;

import org.json.JSONObject;

public class Main {
	
	public static void testWriteJson() {
		String jsonString = new JSONObject()
                .put("JSON1", "Hello World!")
                .put("JSON2", "Hello my World!")
                .put("JSON3", new JSONObject().put("key1", "value1"))
                .toString();

		System.out.println(jsonString);
	}
	
	
	public static void main(String[] args) {
		testWriteJson();
	}

}
