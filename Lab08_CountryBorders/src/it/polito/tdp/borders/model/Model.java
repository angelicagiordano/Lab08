package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	BordersDAO bdao;
	List <Country> nazioni;
	UndirectedGraph<Country, DefaultEdge> grafo ;
	
	public Model() {
	
		bdao= new BordersDAO();
		
	}
	
	

	public void creaGrafo(int anno){
		
		grafo= new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class); //tra parentesi specifico la classe del tipo di arco

		nazioni= new ArrayList<Country>(bdao.loadAllCountries());
		
		List<Border> confini=new ArrayList<Border> (bdao.getCountryPairs(anno));
		
		Graphs.addAllVertices(grafo, nazioni);
		
		for(Border b: confini){
			grafo.addEdge(getCountry(b.getC1()), getCountry(b.getC2()));
		}
				
		System.out.println(grafo);
		
		
	}
	
	public Country getCountry(Country c){
		for(Country c1: nazioni){
			if(c.equals(c1)){
			
				c.setNome(c1.getNome());
				c.setSigla(c1.getSigla());
				
			}
			
		}
		return c;
		
	}



	public List<Country> getCountries() {
		// TODO Auto-generated method stub
		return nazioni;
	}



	public Map<Country, Integer> getCountryCounts() {
		// TODO Auto-generated method stub
		Map<Country, Integer> mappa= new HashMap<Country, Integer>();
		
		for(Country c: nazioni){
			if(grafo.degreeOf(c)>0){
				mappa.put(c, grafo.degreeOf(c));
			}
		}
		return mappa;
	}



	public Integer getNumberOfConnectedComponents() {
		ConnectivityInspector<Country, DefaultEdge> conn= new ConnectivityInspector<Country, DefaultEdge>(grafo);
		
		return conn.connectedSets().size();
	}
}
