package graphs;

import java.util.ArrayList;

public class EdgeList extends ArrayList<EdgeList.Edge> {

	public static record Edge(int start, int end, double weight, boolean directed) {}
}
