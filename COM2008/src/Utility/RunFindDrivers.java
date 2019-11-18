package Utility;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class RunFindDrivers {
	public static void main(String[] args) {
		//create Driver Object
		FindDrivers fd = new FindDrivers();
		
		//example of an update statement (DO NOT RUN THIS EXACT STATEMENT)
//		fd.runSQL("INSERT INTO Author VALUES ('jmarsh3@sheffield.ac.uk','Jordan','Marsh','UniOfShef');", true);
		
		//example of an non update statement
		List<HashMap> rs = null;
		
		try {
			rs = fd.runSQL("SELECT * FROM Author", false);
			//returns a list of hashmaps which can then be referenced
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//print all hashmap items
		for (HashMap h: rs) {
			for (Object i : h.keySet()) {
				System.out.println(i+" "+h.get(i)+" ");
			}
		}
		
	}
}
