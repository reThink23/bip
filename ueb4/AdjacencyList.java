package ueb4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjacencyList implements Representation {
	/* for using identifier linkedHashMap */
	Map<Node, List<Edge>> adjList;

	AdjacencyList(Graph graph) {
		// graph.nodes.stream().collect(Collectors.toMap(Node::id, node -> graph.edges.contains(node) ? node : null));
		Map<Node, List<Edge>> edgesByFromNodeId = graph.edges.stream().collect(Collectors.groupingBy(edge -> edge.from()));
		// System.out.println(edgesByFromNodeId.get(0));
		adjList = edgesByFromNodeId;
	}

	public Graph computeMST() {
		Map<Node, List<Edge>> nodes = new HashMap<Node,List<Edge>>(adjList);
		List<Edge> edgesInMST = new LinkedList<Edge>();
		List<Node> nodesInMST = new LinkedList<Node>();

		Iterator<Node> iter = adjList.keySet().iterator();

		if (!iter.hasNext()) return new Graph(new ArrayList<>(), new ArrayList<>());
		Node start = iter.next();
		nodesInMST.add(start);
		nodes.remove(start);

		while (iter.hasNext()) {
			Edge minEdge = minWeightedEdge(new ArrayList<Node>(nodes.keySet()), nodesInMST, adjList.get(iter.next()));
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
	}
}
