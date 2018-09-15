package com.json.compare.CompareJSON;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * @author m.prasad
 *
 */
public class CompareJsonData {
	
	static Set<JsonElement> setOfElements(JsonArray arr) {
		Set<JsonElement> set = new HashSet<JsonElement>();
	    for (JsonElement j: arr) {
	    	set.add(j);
	    }
	    return set;
	}
	
	/**
	 * Method compareJSON
	 * @param mapObject
	 * @return boolean
	 */
	public static boolean compareJSON(Map<String,Object> mapObject) {
		
		Gson gson = null;
		JsonParser gsonParser = null;
		
		boolean status = false;
		
		String actualOutput = "";
		String expectedOutput = "";
		
		try {
			
			gson = new Gson();
			gsonParser = new JsonParser();
			
			if (mapObject != null && mapObject.size() > 0) {
				for (Map.Entry<String, Object> map: mapObject.entrySet()) {
					String key = map.getKey();
					Object value = map.getValue();
					if ("actualOutput".equals(key.trim())) {
						actualOutput = gson.toJson(value);
					} else if ("expectedOutput".equals(key.trim())) {
						expectedOutput = gson.toJson(value);
					}
				}
				
				Set<JsonElement> arr1elems = setOfElements(gsonParser.parse(actualOutput).getAsJsonArray());
		        Set<JsonElement> arr2elems = setOfElements(gsonParser.parse(expectedOutput).getAsJsonArray());
		        
		        status = arr1elems.equals(arr2elems);
			}
			
		} catch (Exception e) {
			System.out.println(":: Exception raised in compareJSON method is >>"+e.getMessage());
			e.printStackTrace();
		}
		return status;
	}
	
	public static void main(String[] args) {
		
		Map<String,Object> mapObject = null;
		Map<String,String> responseMap = null;
		
		ObjectMapper objectMapper = null;
		Gson gson = null;
		
		String fileName = "Input.json";
		String responseString = "";
		
		boolean responseStatus = false;
		
		try {
			
			objectMapper = new ObjectMapper();
			gson = new Gson();
			
			responseMap = new HashMap<String,String>();
			
			mapObject = objectMapper.readValue(new File("./resources/"+fileName), 
											   new TypeReference<Map<String, Object>>(){});
			responseStatus = compareJSON(mapObject);
			
			if (responseStatus) {
				responseMap.put("status", "Passed");
				responseString = gson.toJson(responseMap);
			} else {
				responseMap.put("status", "Failed");
				responseString = gson.toJson(responseMap);
			}
			
			System.out.println(responseString);
			
		} catch (Exception e) {
			System.out.println(":: Exception raised in main method is >>"+e.getMessage());
			e.printStackTrace();
		}
	}
	
}
