package ueb6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class RecursiveEnumerator implements MolecularFormulaEnumerator {

	protected Element[] allowed;
	protected int multiple;
	
	public RecursiveEnumerator(Element[] allowed) {
		this.allowed = allowed;
		Arrays.sort(this.allowed,new Comparator<Element>() {

			@Override
			public int compare(Element o1, Element o2) {
				return -Integer.compare(o1.mass(), o2.mass());
			}
			
		});
		this.multiple = 0;
	}
	
	@Override
	public Molecule guessFromMass(int mass) {
		return guessFromMass(mass,mass);
	}
	
	
	private Molecule guessFromMass(int remainingMass, int totalMass) {
		if(remainingMass == 0) {
			return new Molecule(totalMass);
		}else {
			for(int i=0;i<allowed.length;i++) {
				if(allowed[i].mass() <= remainingMass) {
					Molecule temp = guessFromMass(remainingMass-allowed[i].mass(),totalMass);
					if(temp != null) {
						//if(temp.getSumOfMasses()+allowed[i].mass() == remainingMass) {
							temp.add(allowed[i], 1);
							return temp;
						//}
					}
				}
			}
			return null;
		}
	}

	@Override
	public Set<Molecule> enumerateFromMass(int mass) {
		this.multiple = 0;
		return enumerateFromMass(mass, mass);
	}
	
	private HashSet<Molecule> enumerateFromMass(int remainingMass, int totalMass){
		if(remainingMass == 0) {
			HashSet<Molecule> temp = new HashSet<>();
			temp.add(new Molecule(totalMass));
			return temp;
		}else {
			HashSet<Molecule> total = new HashSet<>();
			for(int i=0;i<allowed.length;i++) {
				if(allowed[i].mass() <= remainingMass) {
					HashSet<Molecule> temp = enumerateFromMass(remainingMass-allowed[i].mass(),totalMass);
					for(Molecule mol : temp) {
						if(mol.getSumOfMasses()+allowed[i].mass() == remainingMass) {
							mol.add(allowed[i], 1);
							
							boolean test = total.add(mol);
							if(!test) {
								this.multiple++;
							}
						}else {
							System.out.println("ALARM");
						}
					}
				}
			}
			return total;
		}
	}
	
	public int getMultiple() {
		return multiple;
	}
}
