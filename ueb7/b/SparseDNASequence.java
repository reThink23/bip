package ueb7.b;

import ueb7.a.DNAAlphabet;

/**
 * Sparse implementation of a DNA sequence, encoding each symbol
 * as two bits. Intended for large sequences (genomes), where a
 * specific position is accessed only occasionally.
 * 
 * @author Jan Grau
 *
 */
public class SparseDNASequence extends AbstractSequence {

	//hier speichern wir nur noch die ints, in die wir die
	//Zeichen kodiert haben. Achtung: Laenge nicht identisch
	//zur Laenge der Sequenz
	private int[] codedSequence;
	//daher hier separat gespeichert
	private int length;
	
	/**
	 * Constructs a DNA sequence from its {@link String} representation
	 * @param sequence the sequence as string
	 */
	public SparseDNASequence(String sequence) {
		super(DNAAlphabet.getInstance());
		this.length = sequence.length();
		
		//so viele Eintraege brauchen wir zum speichern, da wir 16 Zeichen auf einen int (32 bit) kriegen
		this.codedSequence = new int[(int)Math.ceil(this.length/16.0)]; 
		
		int off = -1;
		int shift = 0;
		for(int i=0;i<sequence.length();i++) {
			//alle 16 Zeichen bits zuruecksetzen
			if(i % 16 == 0) {
				off++;
				shift = 0;//Stelligkeit des aktuellen Zeichens
			}
			//code ist eine int-Zahl, aber im Hintergrund gibt es deren bit-Repraesentation
			int code = this.alphabet.getCode(sequence.charAt(i));
			
			//durch den bit-shift um 2*Stelligkeit "schreiben" wir den code auf
			//die entsprechenden bits
			this.codedSequence[off] += code << (2*shift);
			//Stelligkeit erhoehen
			shift++;
		}
	}

	@Override
	public int getCodeAt(int index) {
		//hier muessen wir jetzt aufpassen, weil der letzte Eintrag von codedSequence
		//eventuell nicht mehr ganz "gefuellt" ist
		if(index >= length) {
			throw new ArrayIndexOutOfBoundsException("Index "+index+" out of bounds for length "+this.length);
		}
		//hier codieren wir das Zeichen an index
		int idx = index / 16;
		//und diese bits innerhalb des Codes brauchen wir
		int shift = index % 16;
		// das & 3 sorgt dafuer, dass wir nur die letzten zwei bits betrachten
		return codedSequence[idx] >> (2*shift) & 3;
	}

	@Override
	public int getLength() {
		return length;
	}
	

}
