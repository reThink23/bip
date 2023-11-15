package ueb4;

public class GraphMain {
    public static void main(String[] args) {
        Edges li = new Edges();
		
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);

		li.add(new Edge(n0,n1,4,directed));
		li.add(new Edge(n0,n2,4,directed));
		li.add(new Edge(n1,n2,2,directed));
		li.add(new Edge(n2,n3,3,directed));
		li.add(new Edge(n2,n4,2,directed));
		li.add(new Edge(n2,n5,4,directed));
		li.add(new Edge(n3,n5,3,directed));
		li.add(new Edge(n4,n5,3,directed));

		g = new Graph(new List<Node>() {n0, n1, n2, n3, n4, n5, n6}, li);

	
		System.out.println(g);

		AdjacencyMatrix adjMatrix = new AdjacencyMatrix(g);
		AdjacencyList adjList = new AdjacencyList(g);


		Graph mstMatrix = g.computeMST(adjMatrix);
		Graph mstList = g.computeMST(adjList);

		System.out.println("MSTs:");
		System.out.println("Matrix: \n"+mstMatrix);
		System.out.println("Matrix: \n"+mstList);
    }
}