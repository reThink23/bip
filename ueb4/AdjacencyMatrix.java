package ueb4;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AdjacencyMatrix implements Representation {
	Edge[][] matrix;
	Edge startingEdge;

	AdjacencyMatrix(Graph graph) {
		matrix = new Edge[graph.nodes.size()][graph.nodes.size()];
		boolean hasStart = false;
		for (Node node : graph.nodes) {
			List<Edge> edges = graph.edges.stream().filter(e -> e.from().equals(node, true)).collect(Collectors.toList());
			for (Edge edge : edges) {
				if (! hasStart) { startingEdge = edge; hasStart = true; }
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

		nodesInMST.add(startingEdge.from());

		Edge minEdge = startingEdge;
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[i].length; j++) {
				if (matrix[i][j] != null && minEdge.weight() >= matrix[i][j].weight()) minEdge = matrix[i][j]; 
			}

			Node to = minEdge.to();
			nodesInMST.add(to);
			edgesInMST.add(minEdge);
		}
		return new Graph(nodesInMST, edgesInMST);
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < matrix.length; i++) {
			s += "[";
			for (int j = 0; j < matrix[i].length; j++) {
				s += matrix[i][j] + ", ";
			}
			s += "]\n";
		}
		return s;
	}

	public static void main(String[] args) {
		List<Node> nodes = new LinkedList<>();
		for (int i = 0; i <= 8; i++) {
			nodes.add(new Node(i));
		}
		List<Edge> edges = new LinkedList<>();
		for (int i = 1; i <= 8; i++) {
			edges.add(new Edge(nodes.get(i - 1), nodes.get(i), 1));
		}
		edges.add(new Edge(nodes.get(0), nodes.get(3), 1));
		edges.add(new Edge(nodes.get(0), nodes.get(2), 1));
		AdjacencyMatrix adjM = new AdjacencyMatrix(new Graph(nodes, edges));
		System.out.println(adjM);
		System.out.println(adjM.computeMST());
	}
}
