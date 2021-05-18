package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Edges;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<String> SelectFewNodes(int month, String cathegory){
		
		String sql = "SELECT DISTINCT offense_type_id FROM EVENTS WHERE offense_category_id = ? AND MONTH(reported_date)=? ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, cathegory);
			st.setInt(2, month);
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
		
			while(res.next()) {
				try {
					list.add(res.getString("offense_type_id"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	public List<Edges> selectPesoEdges(int month, String cathegory) {
		String sql = "SELECT distinct a.offense_type_id, b.offense_type_id, COUNT(distinct a.neighborhood_id) AS peso "
				+ "FROM EVENTS AS a, EVENTS AS b "
				+ "WHERE a.offense_type_id != b.offense_type_id AND a.offense_category_id = b.offense_category_id "
				+ "AND a.neighborhood_id = b.neighborhood_id AND a.offense_category_id = ? AND month(a.reported_date)=month(b.reported_date) "
				+ "AND MONTH( a.reported_date ) = ? AND a.offense_type_id>b.offense_type_id "
				+ "GROUP BY a.offense_type_id,b.offense_type_id ";
	
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, cathegory);
			st.setInt(2, month);
			
			List<Edges> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
		
			while(res.next()) {
				try {
					Edges e = new Edges(res.getString("a.offense_type_id"),res.getString("b.offense_type_id"),res.getInt("peso"));
					list.add(e);
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}
	public List<String> selectcathegories(){
		
		String sql = "SELECT Distinct offense_category_id FROM events " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_category_id"));
							
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
}

