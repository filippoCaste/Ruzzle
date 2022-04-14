package it.polito.tdp.ruzzle.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DizionarioDAO {
	
	/**
	 * Ritorna tutte le parole presenti nel dizionario
	 * 
	 * @return
	 */
	public List<String> listParola() {
		List<String> result = new ArrayList<>() ;
		
		String query = "SELECT nome FROM parola ORDER BY nome" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(query) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getString("nome")) ;
			}
			
			res.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result ;

	}
	
	public List<Integer> getIdentificativi() {
		List<Integer> result = new ArrayList<>() ;

		String query = "SELECT id FROM parola ORDER BY nome" ;

		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(query) ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				result.add(res.getInt("id")) ;
			}

			res.close();
			conn.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result ;
	}
	
	public Set<String> paroleAPartireDaLettera(String primaLettera) {
		String sql = "SELECT p.nome FROM parola p WHERE p.primaLettera = ?";
		Set<String> result = new HashSet<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, primaLettera);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("nome"));
			}
			rs.close();
			st.close();
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void popolaConPrimaLettera() {
		String sql = "UPDATE `dizionario`.`parola` SET `primaLettera`=? WHERE  `id`=?;";
		List<String> parole = this.listParola();
		List<Integer> id = this.getIdentificativi();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			for(int i=0; i<parole.size(); i++) {
				ps.setString(1, ""+ parole.get(i).charAt(0));
				ps.setInt(2, id.get(i));
//				ps.setString(2, parole.get(i));
				ps.executeQuery();

			}
			ps.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore DB");
		}
	}

}
