package ueb3;

import java.util.Comparator;

public class BEDChromComparator implements Comparator<BEDChrom> {
	@Override
	public int compare(BEDChrom obj1, BEDChrom obj2) {
		return obj1.compareTo(obj2);
	}
}
