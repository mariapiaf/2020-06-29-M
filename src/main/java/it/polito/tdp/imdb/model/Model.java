package it.polito.tdp.imdb.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private ImdbDAO dao;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	private List<Director> best;
	private int totAttoriCondivisi;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<>();
		
	}
	
	
	public void creaGrafo(Year anno) {
		this.dao.listAllDirectors(idMap);
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(anno, idMap));
		
		// aggiungo archi
		for(Arco a: this.dao.getArchi(anno, idMap)) {
			if(grafo.containsVertex(a.getD1()) && grafo.containsVertex(a.getD2()))
				Graphs.addEdge(this.grafo, a.getD1(), a.getD2(), a.getPeso());
		}
	}
	
	public List<Adiacente> getVicini(Director partenza){
		List<Adiacente> result = new ArrayList<>();
		for(Director vicino: Graphs.neighborListOf(this.grafo, partenza)) {
			result.add(new Adiacente(vicino, (int) grafo.getEdgeWeight(grafo.getEdge(partenza, vicino))));
		}
		Collections.sort(result);
		return result;
	}
	
	public List<Director> getRegistiAffini(Director partenza, int c){
		best = new ArrayList<>();
		totAttoriCondivisi = 0;
		List<Director> parziale = new ArrayList<>();
		parziale.add(partenza);
		cerca(parziale, c);
		
		return best;
		
	}
	
	public void cerca(List<Director> parziale, int c) {
		Director ultimo = parziale.get(parziale.size()-1);
		// prendo i vicini
		List<Director> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, ultimo));
		
		for(Director vicino: vicini) {
			if(!parziale.contains(vicino) && parziale.size() == 1 && grafo.getEdgeWeight(grafo.getEdge(ultimo, vicino))<=c) {
				parziale.add(vicino);
				cerca(parziale, c);
				parziale.remove(vicino);
			}
			else if(!parziale.contains(vicino) && grafo.getEdgeWeight(grafo.getEdge(ultimo, vicino))<=c) {
				parziale.add(vicino);
				cerca(parziale, c);
				parziale.remove(vicino);
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<Director> getVertici(){
		return this.grafo.vertexSet();
	}
	
	
}
