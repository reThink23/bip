package ueb4;

import java.util.ArrayList;
import java.util.List;

/* For easier operations on Nodes & simplify usage */
public class Nodes {

	public enum NodeDirection {
		INCOMING, OUTGOING, BOTH
	}

	private List<Node> nodes;

	Nodes(List<Node> list) { this.nodes = (list != null) ? list : new ArrayList<Node>(); }
	Nodes() { this.nodes = new ArrayList<Node>(); }

	public void add(Integer id, Edges edgesOut, Edges edgesIn, String name) { nodes.add(new Node(id, edgesOut, edgesIn, name)); }
	public void add(Node node) { nodes.add(node); }
	public void remove(Node node) { nodes.remove(node); }
	public List<Node> toList() { return nodes; }

	public boolean hasNode(Integer id) {
		return nodes.stream().anyMatch(n -> n.id().equals(id));
	}

	public boolean hasNode(Node node) {
		return nodes.contains(node);
	}

	public Node findNodeById(Integer id) {
		return nodes.stream().filter(n -> n.id().equals(id)).findFirst().orElse(null);
	}
}
