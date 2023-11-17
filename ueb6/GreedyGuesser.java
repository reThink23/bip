package ueb6;

import java.util.Arrays;
import java.util.Comparator;

public class GreedyGuesser implements MolecularFormulaGuesser {

	private Element[] allowed;
	
	public GreedyGuesser(Element[] allowed) {
		this.allowed = allowed;
		Arrays.sort(this.allowed,new Comparator<Element>() {

			@Override
			public int compare(Element o1, Element o2) {
				return -Integer.compare(o1.mass(), o2.mass());
			}
			
		});
	}
	
	@Override
	public Molecule guessFromMass(int mass) {
		Molecule molecule = new Molecule(mass);
		while(mass > 0) {
			boolean found = false;
			for(int i=0;i<allowed.length;i++) {
				if(allowed[i].mass() <= mass) {
					molecule.add(allowed[i], 1);
					mass -= allowed[i].mass();
					found = true;
					break;
				}
			}
			if(!found) {
				return null;
			}
		}
		return molecule;
	}

}
