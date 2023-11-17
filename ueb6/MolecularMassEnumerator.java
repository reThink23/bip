package ueb6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MolecularMassEnumerator implements MolecularFormulaEnumerator {

	private Element[] allowed;

	public Molecule guessFromMass(int mass) {
		return null;
	}

	public Set<Molecule> enumerateFromMass(int mass) {
		List<Set<Element>> L = new ArrayList<Set<Element>>(mass+1);
		L.set(0, new HashSet<Element>());
		for (int i = 0; i < mass-1; i++) {
			for (Set<Element> elements: L) {
				for (Element e: elements) {
					if (i + e.mass() <= mass) {
						Set<Element> m2 = new HashSet<>(elements);
						m2.add(e);
						L.get( + e.mass()).addAll(m2);
					}
				}
			}
		}
		return L.get(mass);
	}
	
}
