package graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import graphs.EdgeList.Edge;

public abstract class AbstractGraph implements Graph{
	
	@Override
	public Set<Integer> reachable(int start, boolean dfs, boolean verbose){
		//bereits gefundene erreichbare Knoten
		Set<Integer> reached = new HashSet<>();
		//gefundene Knoten mit noch unbesuchten Kanten
		LinkedList<Integer> curr = new LinkedList<>();
		//Startknoten in beide Mengen aufnehmen
		reached.add(start);
		curr.add(start);
		if(verbose) {System.out.println("starting from "+start);}
		//solange noch Knoten abzuarbeiten
		while(curr.size() > 0) {
			//Knoten aus Menge abfragen
			int node = curr.getFirst();
			//Nachfolger finden
			//Achtung! Diese Implementierung ist zwar generisch, aber die Effizienz haengt an der Implementierung von getNext
			int next = getNext(node,reached);
			//wenn es noch einen Nachfolger gibt
			if(next >= 0) {
				if(verbose) {System.out.println("next: ("+node+","+next+") -> "+next);}
				//zur Menge der erreichbaren Knoten hinzufuegen
				reached.add(next);
				//DFS -> Stack
				if(dfs) {
					curr.addFirst(next);
				//BFS -> Queue
				}else {
					curr.addLast(next);
				}
			}else {
				if(verbose) {System.out.println("removed: "+node);}
				//wenn Knoten abgearbeitet, aus curr entfernen
				curr.removeFirst();
			}
		}
		
		return reached;
	}

	public Edge minWeightedEdge(Set<Integer> s1, Set<Integer> s2, EdgeList edges) {
		Edge min = null;
		for (Edge e : edges) {
			if (s1.contains(e.start()) && s2.contains(e.end())) {
				if (min == null || e.weight() < min.weight()) {
					min = e;
				}
			}
		}
		return min;

	}

	public abstract AbstractGraph computeMST();
}
