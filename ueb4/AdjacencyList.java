package ueb4;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjacencyList implements Representation {
	/* for using identifier linkedHashMap */
	Map<Integer, List<Edge>> adjList;

	AdjacencyList(Graph graph) {
		// graph.nodes.stream().collect(Collectors.toMap(Node::id, node -> graph.edges.contains(node) ? node : null));
		Map<Integer, List<Edge>> edgesByFromNodeId = graph.edges.stream().collect(Collectors.groupingBy(edge -> edge.from().id()));
		// System.out.println(edgesByFromNodeId.get(0));
		adjList = edgesByFromNodeId;
	}

	public Graph computeMST(Graph graph) {
		return null;
	}

	public String toString() {
		return adjList.toString();
	}

	public static void main(String[] args) {
		List<Node> nodes = new LinkedList<>();
		for (int i = 0; i < 8; i+=2) {
			nodes.add(new Node(i));
		}
		List<Edge> edges = new LinkedList<>();
		for (int i = 1; i < 4; i++) {
			edges.add(new Edge(nodes.get(i-1), nodes.get(i), 1));
		}
		edges.add(new Edge(nodes.get(0), nodes.get(3)));
		edges.add(new Edge(nodes.get(0), nodes.get(2)));
		AdjacencyList adjL = new AdjacencyList(new Graph(nodes, edges));
		System.out.println(adjL);
	}
}
