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
	 public static void main( String args[] )
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Databases/" + "isl_two_hand" + "_data.db");
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      StringBuilder sql = new StringBuilder("CREATE TABLE left_data (");
	      for(int i=0; i<120;i++){
	    	  sql.append("'feat" + i + "' NUMERIC, ");
	      }
	      stmt.executeUpdate(sql + " sign	VARCHAR(1));");
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	  }
	
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
	
	public SimpleEntry<List<ArrayList<Double>>, List<Character>> getOneHandAlphabetData(String langauge, String hand, final int resultSize){
	  final String db=getTableName(langauge, hand);
	  final String query="SELECT * FROM alpha_data";
	  return getData(db, query, resultSize);
	}
	
	public SimpleEntry<List<ArrayList<Double>>, List<Character>> getOneHandNumberData(String langauge, String hand,final int resultSize){
		final String db=getTableName(langauge, hand);
		  final String query="SELECT * FROM num_data";
		  return getData(db, query, resultSize);
		}
	
	private String getTableName(String langauge, String hand){
		 if(hand!=null)
		      return "jdbc:sqlite:Databases/" + langauge + "_" + hand + "_data.db";
		    	  return "jdbc:sqlite:Databases/" + langauge + "_data.db";
	}
	
	public SimpleEntry<List<ArrayList<Double>>, List<Character>> getTwoHandData(String langauge, String hand,final int resultSize){
		  final String db="jdbc:sqlite:Databases/" + langauge + "two_hand_data.db";
		  final String query="SELECT * FROM " + hand + "_data";
		  return getData(db, query, resultSize);
		}
	
	@SuppressWarnings("unchecked")
	private SimpleEntry<List<ArrayList<Double>>, List<Character>> getData(String db, String query,final int resultSize){
		  Connection conn = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      conn = DriverManager.getConnection(db);
		      conn.setAutoCommit(false);
		      stmt = conn.createStatement();
		      
		      List<ArrayList<Double>> data=new ArrayList<ArrayList<Double>>();
		      List<Character> target=new ArrayList<Character>();
		      
		      ResultSet rs = stmt.executeQuery( query );
		      
		      ArrayList<Double> list=new ArrayList<Double>(resultSize);
		      while(rs.next()){
		    	 for(int i=0; i<resultSize;i++){
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
