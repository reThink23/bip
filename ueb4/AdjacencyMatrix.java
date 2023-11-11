package ueb4;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AdjacencyMatrix implements Representation {
	Edge[][] matrix;

	AdjacencyMatrix(Graph graph) {
		matrix = new Edge[graph.nodes.size()][graph.nodes.size()];
		for (Node node : graph.nodes) {
			List<Edge> edges = graph.edges.stream().filter(e -> e.from().equals(node)).collect(Collectors.toList());
			for (Edge edge : edges) {
				matrix[edge.from().id()][edge.to().id()] = edge;
			}
		}
		// for (Edge edge : graph.edges) {
		// 	matrix[edge.from().id()][edge.to().id()] = edge.weight();
		// 	if (!edge.directed()) matrix[edge.to().id()][edge.from().id()] = edge.weight();
		// }
	}

	public Graph computeMST() {
		List<Edge> edgesInMST = new LinkedList<Edge>();
		List<Node> nodesInMST = new LinkedList<Node>();

		Node start = matrix[0][0].from();
		nodesInMST.add(start);

		for (int i = 1; i < matrix.length; i++) {
			Edge minEdge = matrix[i][0];
			for (int j = 1; j < matrix[i].length; j++) {
				if (minEdge.weight() > matrix[i][j].weight()) minEdge = matrix[i][j]; 
			}

			Node to = minEdge.to();
			nodesInMST.add(to);
			edgesInMST.add(minEdge);
		}
		return new Graph(nodesInMST, edgesInMST);
	}
}
