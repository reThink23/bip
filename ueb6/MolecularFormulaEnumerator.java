package ueb6;

import java.util.Set;

public interface MolecularFormulaEnumerator extends MolecularFormulaGuesser {

	public Set<Molecule> enumerateFromMass(int mass);
	
}
