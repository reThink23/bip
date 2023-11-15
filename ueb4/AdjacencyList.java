package ueb4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjacencyList implements Representation {
	/* 
		Map with Node as key because it contains more info and there can be ids that can be skipped 
		List of Edge could be also a Set but order could be practical in some cases
	*/
	Map<Node, List<Edge>> adjList;

	AdjacencyList(Graph graph) {
		// graph.nodes.stream().collect(Collectors.toMap(Node::id, node -> graph.edges.contains(node) ? node : null));
		Map<Node, List<Edge>> edgesByFromNodeId = new LinkedHashMap<>(graph.edges.stream().collect(Collectors.groupingBy(edge -> edge.from())));
		// System.out.println(edgesByFromNodeId.get(0));
		adjList = edgesByFromNodeId;
	}

	public Graph computeMST() {
		// Map<Node,List<Edge>> nodes = new HashMap<Node,List<Edge>>(adjList);
		List<Node> nodes = new ArrayList<>(adjList.keySet());
		List<Edge> edgesInMST = new LinkedList<Edge>();
		List<Node> nodesInMST = new LinkedList<Node>();

		Iterator<Node> iter = adjList.keySet().iterator();
		List<Edge> edges = new ArrayList<>();
		while (iter.hasNext()) {
			edges.addAll(adjList.get(iter.next()));
		}

		// if (!iter2.hasNext()) return new Graph(new ArrayList<>(), new ArrayList<>());
		Node start = nodes.get(0);
		nodesInMST.add(start);
		nodes.remove(start);
		
		while (nodes.size() > 0) {
			Edge minEdge = minWeightedEdge(nodes, nodesInMST, edges); // adjList.get(iter.next()));
			Node to = minEdge.to();
			nodesInMST.add(to);
			nodes.remove(to);
			edgesInMST.add(minEdge);
		}
		return new Graph(nodesInMST, edgesInMST);
	}

	public String toString() {
		return adjList.toString();
	}

	public static void main(String[] args) {
		List<Node> nodes = new LinkedList<>();
		for (int i = 0; i <= 8; i++) {
			nodes.add(new Node(i));
		}
		List<Edge> edges = new LinkedList<>();
		for (int i = 1; i <= 8; i++) {
			edges.add(new Edge(nodes.get(i-1), nodes.get(i), 1));
		}
		edges.add(new Edge(nodes.get(0), nodes.get(3), 1));
		edges.add(new Edge(nodes.get(0), nodes.get(2), 1));
		AdjacencyList adjL = new AdjacencyList(new Graph(nodes, edges));
		System.out.println(adjL);
		System.out.println(adjL.computeMST());
	}
}
