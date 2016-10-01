package database;

import java.sql.*;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SignedDB
{
	public void insertSignValues(char sign, Map<String, Float> features, String table)
	  {
	    Connection conn = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      conn = DriverManager.getConnection("jdbc:sqlite:Databases/signed_data.db");
	      conn.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      
	      stmt = conn.createStatement();
	      StringBuilder insertion= new StringBuilder();
	      StringBuilder columns = new StringBuilder(" (");
	      for(Entry<String, Float> i: features.entrySet()){
	    	  if(insertion.length()>0){
	    		 insertion.append(" , "); 
	    		 columns.append(",");
	    	  }
	    	  columns.append(i.getKey());
	    	  insertion.append(i.getValue());
	      }
	      columns.append(",sign)");
	      insertion.append(" , '" + sign + "');");
	      String sql = "INSERT INTO "+ table + columns + " VALUES ("+insertion;
	      stmt.executeUpdate(sql);
	      stmt.close();
	      conn.commit();
	      conn.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	  }
	
	@SuppressWarnings("unchecked")
	public SimpleEntry<List<ArrayList<Double>>, List<Character>> getAllData(){
		  Connection conn = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      conn = DriverManager.getConnection("jdbc:sqlite:Databases/asl_data.db");
		      conn.setAutoCommit(false);
		      System.out.println("Opened database successfully");
		      stmt = conn.createStatement();
		      
		      List<ArrayList<Double>> data=new ArrayList<ArrayList<Double>>();
		      List<Character> target=new ArrayList<Character>();
		      
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM tagged_data;" );
		      
		      ArrayList<Double> list=new ArrayList<Double>(60);
		      while(rs.next()){
		    	 for(int i=0; i<60;i++){
		    	list.add(rs.getDouble("feat"+i));
		    	 }
		    	 data.add((ArrayList<Double>) list.clone());
		    	 list.clear();
		    	 target.add(rs.getString("sign").charAt(0));
		      }
		      
		      stmt.close();
		      conn.commit();
		      conn.close();
		      
		      return new AbstractMap.SimpleEntry<List<ArrayList<Double>>, List<Character>>(data, target);
		    }catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }
			return null;
			}
		}
