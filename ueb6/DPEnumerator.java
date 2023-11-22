package ueb6;

import java.util.Arrays;
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
		Arrays.sort(this.allowed, (e1, e2) -> Integer.compare(e1.mass(), e2.mass()));
	}

	private int multiple = 0;

	public int getMultiple() { return this.multiple; }

	public Molecule guessFromMass(int mass) {
		return enumerateFromMass(mass).stream().findFirst().get();
	}

	public Set<Molecule> enumerateFromMass(int mass) {
		/* 	Optimisierungs-Strategie: Wir überspringen jedes Element, was kleiner als die höchste Elementmasse des Moleküls ist, 
			da wir das im vorherigen Schritt dasselbe Molekül schon bearbeitet haben.
		*/
		
		this.multiple = 0;

		@SuppressWarnings("unchecked")
		HashSet<Molecule>[] L = new HashSet[mass+1];

		for (int i = 0; i < mass+1; i++) L[i] = new HashSet<Molecule>();
		L[0].add(new Molecule(0, allowed[0].mass()));
		for (int i = 0; i < mass-1; i++) {
			for (Molecule molecule: L[i]) {
				for (Element e: allowed) {
					if (i + e.mass() <= mass) {
						if (molecule.getHighestElementMass() > e.mass()) continue;
						Molecule m2 = molecule.addToCopy(e);
						m2.setHighestElementMass(e.mass());
						boolean alreadyContained = ! L[(i + e.mass())].add(m2);
						if (alreadyContained) this.multiple++;
					}
				}
			}
		}
		Molecule onlyH = new Molecule(mass);
		onlyH.add(new Element("H", 1), mass);
		L[mass].add(onlyH);
		return L[mass];
	}

	public static Molecule parseFromString(String formula) {
		Molecule mol = new Molecule(0);
		String[] elements = formula.split("(?=[A-Z])");
		for (String element: elements) {
			String symbol = element.replaceAll("[0-9]", "");
			int multiplicity = Integer.parseInt(element.replaceAll("[A-Z]", ""));
			mol.add(new Element(symbol, 0), multiplicity);
		}
		return mol;
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
