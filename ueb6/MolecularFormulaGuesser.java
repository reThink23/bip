package ueb6;

import java.util.TreeMap;
import java.util.TreeSet;

public interface MolecularFormulaGuesser {

	public static record Element(String symbol, int mass) implements Comparable<Element> {
		public int compareTo(Element e2) {
			//return -Integer.compare(this.mass, e2.mass());
			return this.symbol.compareTo(e2.symbol);
		}
	}
	
	public static class Molecule implements Cloneable{
		
		private int totalMass;
		private int sumOfMasses;
		private TreeMap<Element,Integer> multiplicities;
		
		public Molecule(int totalMass) {
			this.totalMass = totalMass;
			this.multiplicities = new TreeMap<>();
			this.sumOfMasses = 0;
		}
		
		
		public Molecule clone() throws CloneNotSupportedException {
			Molecule mol = (Molecule) super.clone();
			mol.multiplicities = new TreeMap<>();
			for(Element key : multiplicities.keySet()) {
				mol.multiplicities.put(key, multiplicities.get(key));
			}
			return mol;
		}
		
		public Molecule addToCopy(Element el){
			try {
				Molecule mol = this.clone();
				mol.totalMass += el.mass();
				mol.add(el, 1);
				return mol;
			}catch(CloneNotSupportedException ex) {
				throw new RuntimeException("doesnothappen");
			}
			
		}
		
		public void add(Element el, int multiplicity) {
			if(el.mass()*multiplicity+sumOfMasses > totalMass) {
				throw new IllegalArgumentException("Mass of element "+el+" with multiplicity "+multiplicity+" would exceed total mass of "+totalMass);
			}
			if(!multiplicities.containsKey(el)) {
				multiplicities.put(el, 0);
			}
			multiplicities.put(el, multiplicities.get(el)+multiplicity);
			sumOfMasses += el.mass()*multiplicity;
		}
		
		public void removeELement(Element el, int multiplicity) {
			if(!multiplicities.containsKey(el)) {
				throw new IllegalArgumentException("Element "+el+" not contained in molecule "+toString());
			}
			int curr = multiplicities.get(el);
			if(multiplicity > curr) {
				throw new IllegalArgumentException("Element "+el+" not contained "+multiplicity+" times in molecule "+toString());
			}
			if(curr - multiplicity == 0) {
				multiplicities.remove(el);
			}else {
				multiplicities.put(el, multiplicities.get(el)-multiplicity);
			}
			sumOfMasses -= el.mass()*multiplicity;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			TreeSet<Element> sorted = new TreeSet<Element>(multiplicities.keySet());
			Element[] ch = sorted.stream().filter(e -> e.symbol == "C" || e.symbol == "H").toArray(Element[]::new);
			if (ch.length > 1) {
				sb.append(ch[0].symbol()+(multiplicities.get(ch[0]) != 1 ? multiplicities.get(ch[1]) : ""));
				sorted.remove(ch[0]);
				sb.append(ch[1].symbol()+(multiplicities.get(ch[1]) != 1 ? multiplicities.get(ch[1]) : ""));
				sorted.remove(ch[1]);
			}
			for(Element el: sorted) {
				sb.append(el.symbol()+(multiplicities.get(el) != 1 ? multiplicities.get(el) : ""));
			}
			return sb.toString();
		}
		
		public int getSumOfMasses() {
			return sumOfMasses;
		}
		
		public int getRemainingMass() {
			return totalMass - sumOfMasses;
		}
		
		public int hashCode() {
			/*int hash = 0;
			int base = 0;
			for(Element key : multiplicities.keySet()) {
				int h = key.hashCode();
				if(h > base) {
					base = h;
				}
			}
			int curr = 1;
			for(Element key : multiplicities.keySet()) {
				hash += key.hashCode()*curr + multiplicities.get(key);
				curr*=base;
			}
			return hash;*/
			return toString().hashCode();
		}
		
		public boolean equals(Object o2) {
			if(o2 instanceof Molecule m2) {
				if(this.multiplicities.size() != m2.multiplicities.size()) {
					
					return false;
				}
				for(Element key : this.multiplicities.keySet()) {
					if(m2.multiplicities.get(key) == null) {
						return false;
					}
					if(m2.multiplicities.get(key) - this.multiplicities.get(key) != 0) {
						return false;
					}
				}
				return true;
			}else {
				return false;
			}
		}

		public int getTotalMass() {
			return totalMass;
		}


		public int getLast(Element[] allowed) {
			for(int j=allowed.length-1;j>=0;j--) {
				if(this.multiplicities.containsKey(allowed[j])) {
					return j;
				}
			}
			return 0;
		}

		
	}
	
	public Molecule guessFromMass(int mass);
	
}
