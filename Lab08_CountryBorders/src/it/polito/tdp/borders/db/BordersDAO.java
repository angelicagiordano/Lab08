package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode,StateAbb,StateNme " + "FROM country " + "ORDER BY StateAbb ";

		List <Country> nazioni= new ArrayList<Country>();
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			
 
				Country c= new Country( rs.getString("StateAbb"),rs.getInt("ccode"), rs.getString("StateNme"));
				nazioni.add(c);
			}

			conn.close();
			return nazioni;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- loadAllCountries");
			throw new RuntimeException("Database Error");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		String sql = "SELECT state1no,state2no " + "FROM contiguity " + "WHERE year<=? AND conttype=? ";

		List <Border> confini= new ArrayList<Border>();
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1,anno);
			st.setInt(2, 1);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %d \n", rs.getInt("state1no"), rs.getInt("state2no"));
				Country c1= new Country(rs.getInt("state1no"));
				Country c2= new Country(rs.getInt("state2no"));
				Border b= new Border(c1,c2);
				confini.add(b);
			}

			conn.close();
			return confini;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- loadAllCountries");
			throw new RuntimeException("Database Error");
		}

		
	}
}
