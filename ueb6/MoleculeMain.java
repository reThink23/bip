package ueb6;

import java.util.Set;

import ueb6.MolecularFormulaGuesser.Element;
import ueb6.MolecularFormulaGuesser.Molecule;

public class MoleculeMain {

	public static void main(String[] args) {
		Element[] allowed = new Element[] {
				new Element("C",12),
				new Element("H",1),
				new Element("N",14),
				new Element("O",16),
				new Element("P",31),
				new Element("S",32)
		};
		
		int mass = 42;//180, 42
		
		
		GreedyGuesser greedy = new GreedyGuesser(allowed);
		
		Molecule mol = greedy.guessFromMass(mass);
		
		System.out.println(mol);
		
		
		RecursiveEnumerator recursive = new RecursiveEnumerator(allowed);

		mol = recursive.guessFromMass(mass);
		
		System.out.println(mol);
		

		
		
		long time = System.currentTimeMillis();
		Set<Molecule> all = recursive.enumerateFromMass(mass);
		System.out.println("recursive: "+(System.currentTimeMillis()-time));
		
		System.out.println(all.size());
		all.stream().forEach(e -> System.out.println(e));
		
		System.out.println(recursive.getMultiple());
		
		
	}

}
