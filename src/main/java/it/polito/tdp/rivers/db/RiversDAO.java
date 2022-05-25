package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RiversDAO {

	public void getAllRivers(Map<Integer,River> idMap) {
		
		final String sql = "SELECT id, name FROM river";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
					idMap.put(res.getInt("id"), new River(res.getInt("id"),res.getString("name")));
				}
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	public List<LocalDate> getFirstAndLastDate(River r) {
		
		final String sql = "SELECT day "
				+ "FROM flow "
				+ "WHERE river=? "
				+ "ORDER BY day";

		List<LocalDate> date = new LinkedList<LocalDate>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			res.first();
			date.add(res.getDate("day").toLocalDate());
			
			res.last();
			date.add(res.getDate("day").toLocalDate());

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return date;
	}
	
	public Double getAverage(River r) {
		
		final String sql = "SELECT AVG(flow) AS media "
				+ "FROM flow "
				+ "WHERE river=?";

		Double media;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			res.first();
			media = res.getDouble("media");

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return media ;
	}
	
	public int getNumMisurazioni(River r) {
		
		final String sql = "SELECT COUNT(*) AS tot "
				+ "FROM flow "
				+ "WHERE river=?";

		int tot;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			res.first();
			tot = res.getInt("tot");

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return tot ;
	}
	
	public List<Flow> getAllFlows(River r) {
		final String sql = "SELECT * "
				+ "FROM flow "
				+ "WHERE river=? "
				+ "ORDER BY day";

		List<Flow> ret = new ArrayList<Flow>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				ret.add(new Flow(res.getDate("day").toLocalDate(),res.getFloat("flow"),r));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return ret;
	}
}
