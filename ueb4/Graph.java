package ueb4;

import java.util.List;
import java.util.stream.Collectors;

public class Graph {
	List<Node> nodes;
	List<Edge> edges;

	Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public Graph computeMST(Representation rep) {
		return rep.computeMST(this);
	}

	public boolean existsEdge(Node from, Node to) {
		return edges.stream().filter(e -> {return e.from().equals(from) && e.to().equals(to);}).collect(Collectors.toList()).size() > 0;
	}
}
