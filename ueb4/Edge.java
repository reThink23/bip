package ueb4;

public record Edge (Node from, Node to, Double weight, boolean directed, String label) {
	public Edge(Node from, Node to, Double weight, boolean directed, String label) {
		if (from == null || to == null) throw new IllegalArgumentException("from and to must not be null");
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.directed = directed;
		this.label = label;
	}

	public Edge(Node from, Node to, Number weight) {
		this(from, to, weight.doubleValue(), true, null);
		// from.edgesOut().add(this);
		// to.edgesIn().add(this);
	}
	public Edge(Node from, Node to) {
		this(from, to, null, true, null);
	}
	public Edge(Node from, Node to, boolean directed) {
		this(from, to, null, directed, null);
	}
	public Edge(Node from, Node to, Number weight, boolean directed) {
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
