package it.polito.tdp.rivers.db;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
			// e.printStackTrace();
			throw new RuntimeException();
		}

		return rivers;
	}

	public List<Flow> getAllFlows(List<River> rivers) {
		final String sql = "SELECT id, day, flow, river FROM flow";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				flows.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"),
						rivers.get(rivers.indexOf(new River(res.getInt("river"))))));
			}

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException();
		}

		return flows;
	}
	
	public LocalDate dataPrimaMisura(River r){
		
		String sql ="select day from flow where river = ? order by day limit 1";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			
			if( res.next() ){
				return res.getDate("day").toLocalDate();
			}
			else return null;
			
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
   public LocalDate dataUltimaMisura(River r){
		
		String sql ="select day from flow where river = ? order by day desc limit 1";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			
			if( res.next() ){
				return res.getDate("day").toLocalDate();
			}
			else return null;
			
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

 public int countMisure(River r){
	
	String sql ="select count(*) from flow where river = ? ";
	try {
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		
		st.setInt(1, r.getId());
		
		ResultSet res = st.executeQuery();
		
		res.first();	
		
		return res.getInt(1);
		
		
		
	} catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException();
		}
    }
 
	public List<Double> flussiDelFiume(River r){
		List<Double> flussi = new LinkedList<Double>();
		String sql ="select flow from flow where river = ? ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			
			while( res.next() ){
				flussi.add(res.getDouble("flow"));
			}
			res.close();
			conn.close();
			return flussi;
			
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException();
		}
	
}

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();

		List<River> rivers = dao.getAllRivers();
		System.out.println(rivers);

		List<Flow> flows = dao.getAllFlows(rivers);
		System.out.format("Loaded %d flows\n", flows.size());
		// System.out.println(flows) ;
	}

}
