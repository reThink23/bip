package ueb4;

import java.util.LinkedList;
import java.util.List;
// import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {

	public enum NodeDirection{
		INCOMING, OUTGOING, BOTH
	}

	List<Node> nodes;
	List<Edge> edges;

	Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public boolean hasNode(Integer id) {
		return nodes.stream().anyMatch(n -> n.id() == id);
	}

	public boolean hasEdge(Node from, Node to) {
		return edges.stream().anyMatch(e -> e.from() == from && e.to() == to);
	}

	public boolean existsEdge(Node from, Node to) {
		return edges.stream().filter(e -> {return e.from().equals(from) && e.to().equals(to);}).collect(Collectors.toList()).size() > 0;
	}

	public Node findNodeById(Integer id) {
		return nodes.stream().filter(n -> n.id() == id).findFirst().orElse(null);
	}
	
	public Edge findEdgeByNodes(Node from, Node to) {
		return edges.stream().filter(e -> e.from().equals(from) && e.to().equals(to)).findFirst().orElse(null);
	}

	public List<Edge> findEdgesByNode(Node node, NodeDirection direction) {
		// Predicate<Edge> filter = new Predicate<>() {
		// 	@Override
		// 	public boolean test(Edge e) {
		// 		return switch (direction) {
		// 			case INCOMING -> e.from().equals(node);
		// 			case OUTGOING -> e.to().equals(node);
		// 			case BOTH -> e.from().equals(node) || e.to().equals(node);
		// 			default -> false;
		// 		};
		// 	}
		// };
		Stream<Edge> es = edges.stream();
		Stream<Edge> filtered = switch (direction) {
			case INCOMING -> es.filter(e -> e.from().equals(node));
			case OUTGOING -> es.filter(e -> e.to().equals(node));
			case BOTH -> es.filter(e -> e.from().equals(node) || e.to().equals(node));
			// default -> Stream.empty();
		};
		return filtered.collect(Collectors.toList());
		// return edges.stream().filter(filter).collect(Collectors.toList());
	}


	public Node addNode(Integer id, List<Edge> edgesOut, List<Edge> edgesIn, String name) {
		Node node = new Node(id, edgesOut, edgesIn, name);
		nodes.add(node);
		return node;
	}

	public void addConnectedNodes(Integer id1, String name1, Integer id2, String name2, boolean directed) {
		Node node1 = addNode(id1, null, null, name1);
		Node node2 = addNode(id2, null, null, name2);
		edges.add(new Edge(node1, node2, directed));
	}

	private Edge minWeightedEdge(List<Node> nodesNotInMST, List<Node> nodesInMST, List<Edge> edges) {
		/* for faster implementation use while */
		return edges.stream().filter(e -> nodesInMST.contains(e.from()) && nodesNotInMST.contains(e.to()))
				.reduce((prev, crt) -> (prev.weight() < crt.weight()) ? prev : crt)
				.orElse(null);
	}

	public Graph computeMST() {
		List<Node> nodes = new LinkedList<Node>(this.nodes);
		List<Edge> edgesInMST = new LinkedList<Edge>();
		List<Node> nodesInMST = new LinkedList<Node>();

		Node start = nodes.get(0);
		nodesInMST.add(start);
		nodes.remove(start);

		while (!nodes.isEmpty()) {
			Edge minEdge = minWeightedEdge(nodes, nodesInMST, this.edges);
			Node to = minEdge.to();
			nodesInMST.add(to);
			nodes.remove(to);
			edgesInMST.add(minEdge);
		}
		return new Graph(nodesInMST, edgesInMST);
	}

	public Graph computeMST(Representation rep) {
		return rep.computeMST();
	}

	public String toString() {
		return "Nodes: " + nodes.toString() + "\nEdges: " + edges.toString();
	}
}
