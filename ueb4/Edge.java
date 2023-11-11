package ueb4;

public record Edge (Node from, Node to, Double weight, boolean directed, String label) {
	Edge(Node from, Node to, Number weight) {
		this(from, to, weight.doubleValue(), true, null);
		// from.edgesOut().add(this);
		// to.edgesIn().add(this);
	}
	Edge(Node from, Node to) {
		this(from, to, null, true, null);
	}
	Edge(Node from, Node to, boolean directed) {
		this(from, to, null, directed, null);
	}
	Edge(Node from, Node to, Number weight, boolean directed) {
		this(from, to, weight.doubleValue(), directed, null);
	}

	public boolean hasWeight() { return weight != null; }
	public boolean hasLabel() { return label != null; }

	public boolean equals(Edge edge) {
		boolean sameSetAttributes =  (weight == null) == (edge.weight == null) && (label == null) == (edge.label == null);
		return sameSetAttributes && from.equals(edge.from) && to.equals(edge.to) && weight.equals(edge.weight) && directed == edge.directed && label.equals(edge.label);
	}

	public String toString() {
		return from+(hasWeight()? directed? " --["+weight+"]-> " : " --["+weight+"]-- " : directed? " -> " : " -- ")+to;
		// return "Edge("+from+(hasWeight()? directed? " -["+weight+"]> " : " -["+weight+"]- " : directed? " -> " : " -- ")+to+")";
	}
}
