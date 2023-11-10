package ueb4;

import java.util.LinkedList;
import java.util.List;

public class AdjacencyMatrix implements Representation {
	Double[][] matrix;

	AdjacencyMatrix(Graph graph) {
		matrix = new Double[graph.nodes.size()][graph.nodes.size()];
		for (Edge edge : graph.edges) {
			matrix[edge.from().id()][edge.to().id()] = edge.weight();
			if (!edge.directed()) matrix[edge.to().id()][edge.from().id()] = edge.weight();
		}
	}

	public Graph computeMST(Graph graph) {
		List<Node> nodes = new LinkedList<Node>(graph.nodes);
		List<Edge> edgesInMST = new LinkedList<Edge>();
		List<Node> nodesInMST = new LinkedList<Node>();

		Node start = nodes.get(0);
		nodesInMST.add(start);
		nodes.remove(start);

		while (!nodes.isEmpty()) {
			Edge minEdge = minWeightedEdge(nodes, nodesInMST, graph.edges);
			Node to = minEdge.to();
			nodesInMST.add(to);
			nodes.remove(to);
			edgesInMST.add(minEdge);
		}
		return new Graph(nodesInMST, edgesInMST);
	}
}
