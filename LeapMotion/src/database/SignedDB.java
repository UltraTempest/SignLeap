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
	public void insertSignValues(String db,char sign, Map<String, Float> features, String table)
	  {
	    Connection conn = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      conn = DriverManager.getConnection("jdbc:sqlite:Databases/" + db);
	      conn.setAutoCommit(false);
	      
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
	  }
	
	public SimpleEntry<List<ArrayList<Double>>, List<Character>> getAlphabetData(String langauge, String hand){
	  final String query="SELECT * FROM alpha_data";
	  return getData(langauge, hand, query);
	}
	
	public SimpleEntry<List<ArrayList<Double>>, List<Character>> getNumberData(String langauge, String hand){
		  final String query="SELECT * FROM num_data";
		  return getData(langauge, hand, query);
		}
	
	@SuppressWarnings("unchecked")
	private SimpleEntry<List<ArrayList<Double>>, List<Character>> getData(String langauge, String hand, String query){
		  Connection conn = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      if(hand!=null)
		      conn = DriverManager.getConnection("jdbc:sqlite:Databases/" + langauge + "_" + hand + "_data.db");
		      else
		    	  conn = DriverManager.getConnection("jdbc:sqlite:Databases/" + langauge + "_data.db");
		      conn.setAutoCommit(false);
		      stmt = conn.createStatement();
		      
		      List<ArrayList<Double>> data=new ArrayList<ArrayList<Double>>();
		      List<Character> target=new ArrayList<Character>();
		      
		      ResultSet rs = stmt.executeQuery( query );
		      
		      ArrayList<Double> list=new ArrayList<Double>(60);
		      while(rs.next()){
		    	 for(int i=0; i<60;i++){
		    	list.add(rs.getDouble("feat"+i));
		    	 }
		    	 data.add((ArrayList<Double>) list.clone());
		    	 list.clear();
		    	 String signString= rs.getString("sign");
		    	 if(signString.isEmpty())
		    		 target.add('?');
		    	 else
		    	 target.add(signString.charAt(0));
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
