package graphs;

import java.util.Set;

public interface Graph {

	/**
	 * Liefert den naechsten Knoten, der ueber eine Kante mit node verbunden ist,
	 * aber nicht in der Menge reached enthalten ist
	 * @param node the Startknoten der Kante
	 * @param reached die Menge der Knoten, die nicht betrachtet werden sollen
	 * @return den Index des naechsten Knotens oder -1, falls dieser nicht existiert
	 */
	public int getNext(int node, Set<Integer> reached);
	
	/**
	 * Findet ausgehend von einem Startknoten alle erreichbaren Knoten
	 * @param start der Index des Startknotens
	 * @param dfs Suche mit DFS (true) oder BFS (false)
	 * @param verbose wenn true Ausgaben zu einzelnen Schritten
	 * @return die Indices der erreichbaren Knoten 
	 */
	public Set<Integer> reachable(int start, boolean dfs, boolean verbose);
	
	
	
}
