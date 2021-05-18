package it.polito.tdp.crimes.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	Map<Integer,Event> MapCrimes = new HashMap<>();
	List<Event> AllEvents;
	List<String> Nodi;
	EventsDao e = new EventsDao();
	List<String> PercorsoMigliore = new ArrayList<>();
	SimpleWeightedGraph<String,DefaultWeightedEdge> grafo;
	public Model(){
		AllEvents = new ArrayList(e.listAllEvents());
		for(Event x: AllEvents) {
			MapCrimes.put(x.getIncident_id().intValue(),x);
		}
		
	}
	
	public void creagrafo(int mese,String categoria) {
		
		List<Edges> DiInteresse = new ArrayList<>(e.selectPesoEdges(mese, categoria));
		Nodi = new ArrayList<String>(e.SelectFewNodes(mese, categoria));
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, Nodi);
		
		for(Edges e: DiInteresse) {
			
			Graphs.addEdgeWithVertices(this.grafo, e.getFirstcrime(), e.getSecondcrime(),e.getPeso());
			
		}
		
		System.out.println("Vertici: " +grafo.vertexSet().size());
		System.out.println("Edges: " +grafo.edgeSet().size());
	}
	
	public Graph<String,DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
	
	public List<Edges> getEdgesPESOMEDIO() {
		
		double peso = 0.0;
		
		for(DefaultWeightedEdge e:this.grafo.edgeSet()) {
			peso += this.grafo.getEdgeWeight(e);
		}
		peso = peso/this.grafo.edgeSet().size();
		
		List<Edges> edges = new ArrayList<>();
		for(DefaultWeightedEdge e:this.grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>peso) {
				edges.add(new Edges(grafo.getEdgeSource(e),grafo.getEdgeTarget(e),grafo.getEdgeWeight(e)));
			}
		}
		return edges;
	}
	public List<String> getCategorie(){
		List<String> categorie = new ArrayList<String>(e.selectcathegories());
		return categorie;
	}
	public List<String> trovapercorso(String Sorgente,String Destinazione){
		
		
		List<String> Parziale= new ArrayList<>();
		Parziale.add(Sorgente);
		cerca(Destinazione,Parziale);
		return PercorsoMigliore;
	}
	public void cerca(String Destinazione,List<String> Parziale) {
		
		if(Parziale.get(Parziale.size()-1).equals(Destinazione)) {
			if(Parziale.size()>PercorsoMigliore.size()) {
				PercorsoMigliore = new ArrayList<>(Parziale);
			}
			return;
		}
		for(String e: Graphs.neighborListOf(this.grafo, Parziale.get(Parziale.size()-1))) {
			if(!Parziale.contains(e)) {
				Parziale.add(e);
				cerca(Destinazione,Parziale);
			}
		}
	}
}
