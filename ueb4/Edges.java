package ueb4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* For easier operations on Edges simplify usage */
public class Edges {

	public enum NodeDirection {
		INCOMING, OUTGOING, BOTH
	}

	private List<Edge> edges;

	Edges(List<Edge> list) { this.edges = (list != null) ? list : new ArrayList<Edge>(); }
	Edges() { this.edges = new ArrayList<Edge>(); }

	public void add(Node from, Node to, Double weight, boolean directed, String label) { edges.add(new Edge(from, to, weight, directed, label)); }
	public void add(Edge edge) { edges.add(edge); }
	public void remove(Edge edge) { edges.remove(edge); }
	public List<Edge> toList() { return edges; }

	public boolean hasEdge(Node from, Node to) {
		return edges.stream().anyMatch(e -> e.from() == from && e.to() == to);
	}

	public Edge findEdgeByNodes(Node from, Node to) {
		return edges.stream().filter(e -> e.from().equals(from) && e.to().equals(to)).findFirst().orElse(null);
	}

	public Edges findEdgesByNode(Node node, NodeDirection direction) {
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
		return new Edges(filtered.collect(Collectors.toList()));
		// return edges.stream().filter(filter).collect(Collectors.toList());
	}

	public NodeDirection getDirection(Node node, Edge edge) {
		if (edge.from().equals(node) && edge.to().equals(node)) return NodeDirection.BOTH;
		if (edge.from().equals(node)) return NodeDirection.OUTGOING;
		if (edge.to().equals(node)) return NodeDirection.INCOMING;
		return null;
	}
}
