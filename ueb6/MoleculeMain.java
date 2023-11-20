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
		
		int mass = 180; // 80, 180, 42
		
		// GreedyGuesser greedy = new GreedyGuesser(allowed);
		// Molecule mol = greedy.guessFromMass(mass);
		// System.out.println(mol);
		
		RecursiveEnumerator recursive = new RecursiveEnumerator(allowed);
		// Molecule mol = recursive.guessFromMass(mass);
		// System.out.println(mol);

		DPEnumerator dp = new DPEnumerator(allowed);
		// mol = dp.guessFromMass(mass);
		// System.out.println(mol);
		
		
		// long startRec = System.currentTimeMillis();
		// Set<Molecule> allRec = recursive.enumerateFromMass(mass);
		// long endRec = System.currentTimeMillis();
		// System.out.println("recursive for mass "+mass+"g/mol, time: "+(endRec-startRec));
		// System.out.println("rec amount: "+ allRec.size());
		// // all.stream().forEach(e -> System.out.println(e));
		// System.out.println("rec duplications: "+ recursive.getMultiple()); // 18: 12, 42: 1453, 60: 142097, 80: 3721487, 81: 4575117


		long startDP = System.currentTimeMillis();
		Set<Molecule> allDP = dp.enumerateFromMass(mass);
		long endDP = System.currentTimeMillis();
		System.out.println("dynamic programming for mass "+mass+"g/mol, time: "+(endDP-startDP));
		System.out.println("dp amount: "+ allDP.size());
		System.out.println("dp duplications: "+ dp.getMultiple()); // 18: 9, 42: 246, 60: 1542, 80: 4213, 81: 4461, 180: 224576
		
		// String regex = "^C\\d+H\\d+N\\d+O\\d+P\\d+S\\d+$";
		// String regex = "^C\\dH\\d+O\\d$";
		String regex = "^.*?C6H12.*?$";
		Set<Molecule> filtered = DPEnumerator.filterFormulaByRegex(allDP, regex);
		System.out.println(filtered);
		boolean hasC6H12O6 = allDP.stream().anyMatch(m -> m.toString() == "C6H12O6");
		System.out.println("has C6H12O6: "+ hasC6H12O6);
		// allDP.stream().forEach(e -> System.out.println(e));

		/* 
			Deutlich geringere Duplikationen bei DP-Algorithmus als bei dem Rekursiven, 
			was sich gerade bei "großen" Zahlen von 80 bemerkbar macht (auch in der Laufzeit) 
		*/
		
		
	}

}
