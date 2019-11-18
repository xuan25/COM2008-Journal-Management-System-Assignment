package Utility;

import java.sql.*;
import java.util.*;

public class FindDrivers{
	
	/**
	 * This function will connect to the database (given you have the correct drivers)
	 * and run the sql given as the parameter to modify or query the database
	 * 
	 * @param sql - a string of SQL to be executed
	 * @param update - True if an update type query (changes the database in some way)
	 * @return null if update is true otherwise it returns a list of hashmaps which are the query result
	 * @throws SQLException - put in a try loop or throw exception 
	 */
	public List<HashMap> runSQL(String sql, Boolean update) throws SQLException {
		//below commented code is for making sure the drivers are set up.
		//uncomment if drivers are not working
		
//		System.out.println("\nDriversloaded as properties:");
//	    System.out.println(System.getProperty("jdbc.drivers"));
//	    System.out.println("\nDriversloaded by DriverManager:");
//	    Enumeration<Driver> list = DriverManager.getDrivers();
//	    while (list.hasMoreElements())
//	    System.out.println(list.nextElement());
	    
	    Connection con = null;  
	    // a Connection object
	    try {
	    	con = DriverManager.getConnection(
	    			"jdbc:mysql://stusql.dcs.shef.ac.uk/team018", "team018", "9ae70ba0");
	    		// use the open connection
	    		// for several queries
	    	Statement stmt= null;
	    	try {
	        	stmt = con.createStatement();
	        	if (update) {
	        		//execute as update if of update query type
		        	stmt.executeUpdate(sql);
	        		return null;
	        	}
	        	else {
	        		//get the result set for the sql
	        		ResultSet rs = stmt.executeQuery(sql);
	        		//get the metadata from the results
	        		ResultSetMetaData md = rs.getMetaData();
	        		int columns = md.getColumnCount();
	        		List<HashMap> result = new ArrayList<HashMap>();
	        		while (rs.next()) {
	        			//put each column and field value from the result set into a hashmap
	        			HashMap<String, Object> row = new HashMap<String, Object>(columns);
	        			for(int i=1; i<=columns; ++i){           
	        				row.put(md.getColumnName(i),rs.getObject(i));
	        			}
	        			//add to hashmap list
	        			result.add(row);
	        	      }
	        		return result;
	        	}
	    	}
	    	//code underneath make sure that all connections to the DB
	    	//are closed
		    catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
		    finally {
		    	if (stmt!= null) stmt.close();
		    }
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
	    finally {
	    	if (con != null) con.close();
	    }
		return null;
	}
}
