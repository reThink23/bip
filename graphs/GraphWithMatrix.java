package graphs;

import java.util.HashSet;
import java.util.Set;

import graphs.EdgeList.Edge;

public class GraphWithMatrix extends AbstractGraph{

	protected Double[][] adjacencyMatrix;
	
	public GraphWithMatrix(int numNodes, EdgeList edges) {
		adjacencyMatrix = new Double[numNodes][numNodes];
		
		for(Edge edge : edges) {
			adjacencyMatrix[edge.start()][edge.end()] = edge.weight();
			if(!edge.directed()) {
				adjacencyMatrix[edge.end()][edge.start()] = edge.weight();
			}
		}
	}

	@Override
	public int getNext(int node, Set<Integer> reached) {
		for (int i=0;i<adjacencyMatrix[node].length;i++) {
			// if (adjacencyMatrix[node][i] == null) continue;
			// if (reached.contains(i)) continue;
			// return i;
			if (adjacencyMatrix[node][i] != null) {
				if (!reached.contains(i)) {return i;}
			}
		}
		return -1;
	}

	public AbstractGraph computeMST() {
		Set<Integer> nodes = new HashSet<>();
		int start = 0;
		int next = start;
		while ((next = getNext(next, nodes)) != -1) {
			nodes.add(next);
		}

		EdgeList edges = new EdgeList();
		for (int i=0;i<adjacencyMatrix.length;i++) {
			for (int j=0;j<adjacencyMatrix[i].length;j++) {
				if (adjacencyMatrix[i][j] != null) {
					edges.add(new Edge(i,j,adjacencyMatrix[i][j],false));
				}
			}
		}

		EdgeList edgesInMST = new EdgeList();
		Set<Integer> nodesInMST = new HashSet<>();

		nodesInMST.add(start);
		nodes.remove(start);

		while(nodes.size() > 0) {
			Edge min = minWeightedEdge(nodesInMST, nodes, edges);
			nodesInMST.add(min.end());
			nodes.remove(min.end());
			edgesInMST.add(min);

		}

		return new GraphWithMatrix(nodesInMST.size(), edgesInMST);
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<adjacencyMatrix.length;i++) {
			for (int j=0;j<adjacencyMatrix[i].length;j++) {
				if (adjacencyMatrix[i][j] != null) {
					sb.append(i+" -> "+j+" ("+adjacencyMatrix[i][j]+")\n");
				}
			}
		}
		return sb.toString();
	}
}
