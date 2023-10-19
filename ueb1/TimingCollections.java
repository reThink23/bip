package ueb1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Collections
 */
public class TimingCollections {

	public static long timingFill(Collection<Integer> col, int n) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			col.add(i);
		}
		long end = System.currentTimeMillis();
		return end - start;

	}

	public static long timingIterate(Collection<Integer> col) {
		long start = System.currentTimeMillis();
		Iterator<Integer> iter = col.iterator();
		while (iter.hasNext()) {
			iter.next();
		}
		long end = System.currentTimeMillis();
		return  end - start;
	}


	public static long timingContains(Collection<Integer> col, int m) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < m; i++) {
			col.contains(i);
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	public static long timingGet(List<Integer> list, int k) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < k; i++) {
			list.get(i);
		}
		long end = System.currentTimeMillis();
		return end - start;
		
	}

	public static void main(String[] args) {
		HashSet<Integer> hashSet = new HashSet<>();
		TreeSet<Integer> treeSet = new TreeSet<>();
		LinkedList<Integer> linkedList = new LinkedList<>();
		ArrayList<Integer> arrayList = new ArrayList<>();

		long[][] time = new long[4][4];

		time[0][0] = timingFill(hashSet, 100);
		time[0][1] = timingIterate(hashSet);
		time[0][2] = timingContains(hashSet, 100);

		time[1][0] = timingFill(treeSet, 1000000);
		time[1][1] = timingIterate(treeSet);
		time[1][2] = timingContains(treeSet, 10000);
		
		time[2][0] = timingFill(linkedList, 1000000);
		time[2][1] = timingIterate(linkedList);
		time[2][2] = timingContains(linkedList, 10000);
		time[2][3] = timingGet(linkedList, 100000);

		time[3][0] = timingFill(arrayList, 1000000);
		time[3][1] = timingIterate(arrayList);
		time[3][2] = timingContains(arrayList, 10000);
		time[3][3] = timingGet(arrayList, 10000);
		time[0][0] = timingFill(hashSet, 100);

		String[] header = new String[] {"Fill", "Iterate", "Contains", "Get"};
		String[] properties = new String[] {"Hashset", "Treeset", "Link-L", "Array-L"};
		String res = Helper.printTable(time, header, properties);
		System.out.println(res);

		// System.out.println("hashSet fill: " + timingFill(hashSet, 1000000));
		// System.out.println("hashSet iterate: " + timingIterate(hashSet));
		// System.out.println("hashSet contains: " + timingContains(hashSet, 100));
		
		// System.out.println("treeSet fill: " + timingFill(treeSet, 1000000));
		// System.out.println("treeSet iterate: " + timingIterate(treeSet));
		// System.out.println("treeSet contains: " + timingContains(treeSet, 10000));
		
		// System.out.println("linked List fill: " + timingFill(linkedList, 1000000));
		// System.out.println("linked List iterate: " + timingIterate(linkedList));
		// System.out.println("linked List contains: " + timingContains(linkedList, 10000));
		// System.out.println("linked List get: " + timingGet(linkedList, 100000));
		
		// System.out.println("arrayList fill: " + timingFill(arrayList, 1000000));
		// System.out.println("arrayList iterate: " + timingIterate(arrayList));
		// System.out.println("arrayList contains: " + timingContains(arrayList, 10000));
		// System.out.println("arrayList get: " + timingGet(arrayList, 10000));



	}
}