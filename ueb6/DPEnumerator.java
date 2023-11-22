package ueb6;

import java.util.HashSet;
import java.util.Set;

public class DPEnumerator implements MolecularFormulaEnumerator {

	private Element[] allowed = new Element[] { 
		new Element("C", 12), 
		new Element("H", 1),
		new Element("N", 14), 
		new Element("O", 16), 
		new Element("P", 31),
		new Element("S", 32), 
	};

	public DPEnumerator() {}


	public DPEnumerator(Element[] allowed) {
		this.allowed = allowed;
	}

	private int multiple = 0;

	public int getMultiple() { return this.multiple; }

	public Molecule guessFromMass(int mass) {
		return enumerateFromMass(mass).stream().findFirst().get();
	}

	public Set<Molecule> enumerateFromMass(int mass) {
		/* Optimization 1: check for each molecule m if the same molecule is contained in L[i+m(m)-m(eToAdd)] */
		
		this.multiple = 0;
		
		@SuppressWarnings("unchecked")
		HashSet<Molecule>[] L = new HashSet[mass+1];

		for (int i = 0; i < mass+1; i++) L[i] = new HashSet<Molecule>();
		Molecule startM = new Molecule(0);
		// L[0] = new HashSet<Molecule>();
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
		return L[mass];
	}

	public static Set<Molecule> filterFormulaByRegex(Set<Molecule> molecules, String regex) {
		return molecules.stream().filter(m -> m.toString().matches(regex)).collect(java.util.stream.Collectors.toSet());
	} 

	public static void main(String[] args) {
		DPEnumerator mme = new DPEnumerator();
		Set<Molecule> molecules = mme.enumerateFromMass(180);

		System.out.println(molecules.size());
		molecules.stream().forEach(m -> System.out.println(m.getSumOfMasses() + " " + m.toString()));
		System.out.println(mme.getMultiple());
	}
	
}
