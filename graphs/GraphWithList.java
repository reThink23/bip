package graphs;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import graphs.EdgeList.Edge;

public class GraphWithList extends AbstractGraph {

	protected TreeSet<ListEntry>[] adjacencyList;

	protected record ListEntry(int end, double weight) implements Comparable<ListEntry>{
		
		public int compareTo(ListEntry e2) {
			int c = Double.compare(this.weight(), e2.weight());
			if(c == 0) {
				c = Integer.compare(this.end(), e2.end());
			}
			return c;
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public GraphWithList(int numNodes, EdgeList edges) {
		TreeSet[] adjacencyList = new TreeSet[numNodes];
		for(Edge edge : edges) {
			add(adjacencyList, edge.start(),edge.end(),edge.weight());
			if(!edge.directed()) {
				add(adjacencyList, edge.end(),edge.start(),edge.weight());
			}
		}
		this.adjacencyList = adjacencyList;
	}
	
	private void add(TreeSet[] adjacencyList, int start, int end, double weight) {
		if(adjacencyList[start] == null) {
			adjacencyList[start] = new TreeSet<ListEntry>();
		}
		adjacencyList[start].add(new ListEntry(end,weight));
	}
	

	@Override
	public int getNext(int node, Set<Integer> reached) {
		if(adjacencyList[node] != null) {
			for(ListEntry en : adjacencyList[node]) {
				if(!reached.contains(en.end())) {
					return en.end();
				}
			}
		}
		return -1;
	}

	@Override
	public AbstractGraph computeMST() {
		Set<Integer> nodes = new HashSet<>();
		for (int i = 0; i < adjacencyList.length; i++) { nodes.add(i); }

		EdgeList edges = new EdgeList();
		for (int i = 0; i < adjacencyList.length; i++) {
			if (adjacencyList[i] != null) {
				for (ListEntry en : adjacencyList[i]) {
					edges.add(new Edge(i, en.end(), en.weight(), false));
				}
			}
		}
		
		int start = 0;

		Set<Integer> nodesInMST = new HashSet<>();
		EdgeList edgesInMST = new EdgeList();
		

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
		for(int i=0;i<adjacencyList.length;i++) {
			sb.append(i+": ");
			if(adjacencyList[i] != null) {
				for(ListEntry en : adjacencyList[i]) {
					sb.append(en.end()+"("+en.weight()+") ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
