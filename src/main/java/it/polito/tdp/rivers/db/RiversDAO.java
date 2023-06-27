package it.polito.tdp.rivers.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}

	public LocalDate getInizio(River r) {
		final String sql = "SELECT MIN(f.`day`) AS data "
				+ "FROM flow f"
				+" WHERE f.river=? ";

		LocalDate data=null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			res.first();
			data=res.getDate("data").toLocalDate();
			

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return data;
		
	}
	
	public LocalDate getFine(River r) {
		final String sql = "SELECT MAX(f.`day`) AS data "
				+ "FROM flow f"
				+" WHERE f.river=? ";

		LocalDate data=null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			res.first();
			data=res.getDate("data").toLocalDate();
			

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return data;
		
	}

	public int getMisurazioni(River r) {
		final String sql = "SELECT COUNT(*) AS totale "
				+ "FROM flow f "
				+ "WHERE f.river=? ";

		int misurazioni=0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			res.first();
			misurazioni=res.getInt("totale");
			

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return misurazioni;
	}
	
	public Double getMedia(River r) {
		final String sql = "SELECT AVG(f.flow) AS media "
				+ "FROM flow f "
				+ "WHERE f.river= ? ";

		Double misurazioni=0.0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			res.first();
			misurazioni=res.getDouble("media");
			

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return misurazioni;
	}

	public Map<LocalDate, Double> getFlussi(River r){
		final String sql = "SELECT f.`day`,f.flow "
				+ "FROM flow f "
				+ "WHERE f.river=? ";

		Map<LocalDate, Double> flussi=new HashMap<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();


			while(res.next()){
				flussi.put(res.getDate("day").toLocalDate(),res.getDouble("flow")*86400);
				
			}
			

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flussi;
		
		
	}
	
}
