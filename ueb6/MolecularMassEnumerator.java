package ueb6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MolecularMassEnumerator implements MolecularFormulaEnumerator {

	private Element[] allowed = new Element[] { 
		new Element("C", 12), 
		new Element("H", 1),
		new Element("N", 14), 
		new Element("O", 16), 
		new Element("P", 31),
		new Element("S", 32), 
	};

	private int multiple = 0;

	public int getMultiple() { return this.multiple; }

	public Molecule guessFromMass(int mass) {
		return null;
	}

	public Set<Molecule> enumerateFromMass(int mass) {
		this.multiple = 0;
		
		@SuppressWarnings("unchecked")
		Set<Molecule>[] L = (Set<Molecule>[]) new Set[mass+1];

		for (int i = 0; i < mass+1; i++) L[i] = new HashSet<Molecule>();
		Molecule startM = new Molecule(0);
		L[0] = new HashSet<Molecule>();
		L[0].add(startM);
		for (int i = 0; i < mass-1; i++) {
			for (Molecule molecule: L[i]) {
				for (Element e: allowed) {
					if (i + e.mass() <= mass) {
						Molecule m2 = molecule.addToCopy(e);
						boolean alreadyContained = ! L[(i + e.mass())].add(m2);
						if (alreadyContained) this.multiple++;
					}
				}
			}
		}
		return L[(mass)];
	}

	public static void main(String[] args) {
		MolecularMassEnumerator mme = new MolecularMassEnumerator();
		Set<Molecule> molecules = mme.enumerateFromMass(80);

		System.out.println(molecules.size());
		molecules.stream().forEach(e -> System.out.println(e));
		System.out.println(mme.getMultiple());
	}
	
}
