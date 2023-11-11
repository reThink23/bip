package ueb4;

import java.util.List;
// import java.util.UUID;

public record Node (Integer id, List<Edge> edgesOut, List<Edge> edgesIn, String name) {
	// Node() {
	// 	this(UUID.randomUUID().toString(), null, null, null)
	// }

	Node(Integer id) {
		this(id, null, null, null);
	}

	public boolean hasName() { return name != null; }
	public boolean hasEdgesOut() { return edgesOut != null; }
	public boolean hasEdgesIn() { return edgesIn != null; }

	public boolean hasIncomingEdges() { return hasEdgesIn() && edgesIn.size() > 0; }
	public boolean hasOutgoingEdges() { return hasEdgesOut() && edgesOut.size() > 0; }

	public boolean hasEdges() { return hasIncomingEdges() || hasOutgoingEdges(); }

	public boolean equalsId(Integer id) {
		return this.id == id;
	}

	public boolean equals(Node node) {
		// check if both are null or not null
		boolean sameSetAttributes =  (name == null) == (node.name == null) && (edgesOut == null) == (node.edgesOut == null) && (edgesIn == null) == (node.edgesIn == null);
		return sameSetAttributes && id.equals(node.id) && name.equals(node.name) && edgesOut.equals(node.edgesOut) && edgesIn.equals(node.edgesIn);
	}

	public String toString() {
		return "Node:"+id+ (hasName() ? "("+name+")" : ""); // + (hasEdgesIn() ? " Edges in: " + edgesIn : "") + (hasEdgesOut() ? "| Edges out: " + edgesOut : "");
	}

}
