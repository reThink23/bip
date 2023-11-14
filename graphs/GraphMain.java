package graphs;

import java.util.Set;

import graphs.EdgeList.Edge;

public class GraphMain {

	public static void main(String[] args) {
		
		boolean directed = false;
		int start = 3;
		
		EdgeList li = new EdgeList();
		
		li.add(new Edge(0,1,4,directed));
		li.add(new Edge(0,2,4,directed));
		li.add(new Edge(1,2,2,directed));
		li.add(new Edge(2,3,3,directed));
		li.add(new Edge(2,4,2,directed));
		li.add(new Edge(2,5,4,directed));
		li.add(new Edge(3,5,3,directed));
		li.add(new Edge(4,5,3,directed));
		
		AbstractGraph g = new GraphWithMatrix(6, li);

		System.out.println(g);
		
		// Set<Integer> reached = g.reachable(start, true, true);
		
		// System.out.println(reached);
		
		AbstractGraph mstMatrix = g.computeMST();
		
		System.out.println(mstMatrix);
		
		System.out.println("########################");
		
		g = new GraphWithList(6,li);
		
		System.out.println(g);
		
		// reached = g.reachable(start, true, true);
		
		// System.out.println(reached);
		
		AbstractGraph mstList = g.computeMST();

		System.out.println(mstList);
		
		// System.out.println("########################");
		
		// String[] labels = new String[] {"A","B","C","D","E","F"};
		// g = new ExplicitGraph(labels, li);
		
		// reached = g.reachable(start, true, true);

		// System.out.println(reached);
		
		// System.out.println("-----------------------");
		
		// reached = ((ExplicitGraph)g).reachableRecursive(start, true);
		
		// System.out.println(reached);
	}

}
