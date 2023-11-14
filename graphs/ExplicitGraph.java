package graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExplicitGraph extends AbstractGraph {

	public class Node{
		
		private String label;
		private int index;
		private LinkedList<Edge> out;
		
		public Node(String label, int index) {
			this.label = label;
			this.index = index;
			this.out = new LinkedList<>();
		}
		
		public void addEdge(Edge edge) {
			out.add(edge);
		}

		public int getNext(Set<Integer> reached) {
			if(out != null) {
				for(Edge e : out) {
					int idx = e.getEndNode().index;
					if(!reached.contains(idx)) {
						return idx;
					}
				}
			}
			return -1;
		}

		public void reachableRecursive(Set<Integer> set, boolean verbose) {
			if(!set.contains(index)) {
				set.add(index);
				for(Edge e : out) {
					Node next = e.getEndNode();
					if(verbose) {System.out.println("next: ("+index+","+next.index+") -> "+next);}
					next.reachableRecursive(set, verbose);
				}
				if(verbose) {System.out.println("finished with "+index);}
			}
		}
		
		public String toString() {
			return index+" ("+label+")";
		}
	}
	
	public class Edge{
		private Node endNode;
		private double weight;
		
		public Edge(Node endNode, double weight) {
			this.endNode = endNode;
			this.weight = weight;
		}
		
		public double getWeight() {
			return weight;
		}
		
		public Node getEndNode() {
			return endNode;
		}
	}
	
	
	private Node[] nodes;
	
	public ExplicitGraph(String[] nodeLabels, EdgeList edges) {
		nodes = new Node[nodeLabels.length];
		for(int i=0;i<nodes.length;i++) {
			nodes[i] = new Node(nodeLabels[i],i);
		}
		for(EdgeList.Edge e : edges) {
			nodes[e.start()].addEdge(new Edge(nodes[e.end()],e.weight()));
			if(!e.directed()) {
				nodes[e.end()].addEdge(new Edge(nodes[e.start()],e.weight()));
			}
		}
	}

	@Override
	public int getNext(int node, Set<Integer> reached) {
		return nodes[node].getNext(reached);
	}
	
	
	public Set<Integer> reachableRecursive(int start, boolean verbose){
		if(verbose) {System.out.println("starting at "+start);}
		HashSet<Integer> set = new HashSet<>();
		nodes[start].reachableRecursive(set,verbose);
		return set;
	}

	@Override
	public AbstractGraph computeMST() {
		Set<Integer> nodes = new HashSet<>();
		for (int i = 0; i < this.nodes.length; i++) { nodes.add(i); }

		EdgeList edgesInMST = new EdgeList();
		Set<Node> nodesInMST = new HashSet<>();

		EdgeList edges = new EdgeList();
		for (int i = 0; i < this.nodes.length; i++) {
			for (Edge e : this.nodes[i].out) {
				edges.add(new EdgeList.Edge(i, e.getEndNode().index, e.getWeight(), true));
			}
		}

		Node start = this.nodes[0];

		nodesInMST.add(start);
		nodes.remove(start.index);

		while(nodes.size() > 0) {
			EdgeList.Edge min = minWeightedEdge(nodesInMST.stream().map(n -> n.index).collect(Collectors.toSet()), nodes, edges);
			nodesInMST.add(new Node(Integer.toString(min.end()), min.end()));
			nodes.remove(min.end());
			edgesInMST.add(min);

		}

		return new ExplicitGraph(new String[] {""}, edgesInMST);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Node n : nodes) {
			sb.append(n.toString());
			sb.append(" -> ");
			for(Edge e : n.out) {
				sb.append(e.getEndNode().toString());
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
}
