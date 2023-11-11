package ueb4;

import java.util.List;

public interface Representation {

	// public boolean hasNode(Integer id);
	// public boolean hasEdge(Node from, Node to);

	/* uses Prim algorithm */
	public Graph computeMST();

	default public Edge minWeightedEdge(List<Node> nodesNotInMST, List<Node> nodesInMST, List<Edge> edges) {
		/* for faster implementation use while */
		return edges.stream().filter(e -> nodesInMST.contains(e.from()) && nodesNotInMST.contains(e.to()))
							 .reduce((prev, crt) -> (prev.weight() < crt.weight()) ? prev : crt)
							 .orElse(null);
	}
}
