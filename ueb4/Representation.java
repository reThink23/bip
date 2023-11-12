package ueb4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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

	default public Graph readTSV(String path) {
		List<Edge> edges = new ArrayList<>();
		List<Node> nodes = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine() ) != null) {
				String[] r = line.split("\t");
				Node from = new Node(Integer.parseInt(r[0]));
				Node to = new Node(Integer.parseInt(r[1]));
				Double weight = Double.parseDouble(r[2]);
				edges.add(new Edge(from, to, weight));
			}
			br.close();
			return new Graph(nodes, edges);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
