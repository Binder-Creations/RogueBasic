package com.RogueBasic.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RogueUtilities {
	private static final Logger log = LogManager.getLogger(RogueUtilities.class);
	
	public String readFileToString(String name, String type){
		try{
			StringBuilder sb = new StringBuilder();
			Files.readAllLines(Paths.get("src/main/resources/" + name + type))
				 .forEach((s)->sb.append(s + "\n"));
			return sb.toString().substring(0, sb.length()-1);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public List<String[]> readFileToArrays(String name, String type){
		try {
			List<String[]> result = new ArrayList<>();
			Files.readAllLines(Paths.get("src/main/resources/" + name + type))
				 .forEach((s)->result.add(s.split("~")));
			return result;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> readFileToList(String name, String type){
		try {
			List<String> result = new ArrayList<>();
			Files.readAllLines(Paths.get("src/main/resources/" + name + type))
				 .forEach((s)->result.add(s));
			return result;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
