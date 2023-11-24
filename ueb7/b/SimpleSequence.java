package ueb7.b;

import ueb7.a.Alphabet;

/**
 * Simple implementation of a {@link Sequence},
 * backed by an int-array.
 * 
 * @author Jan Grau
 *
 */
public class SimpleSequence extends AbstractSequence {

	//hier speichern wir die codierten Zeichen
	private int[] codedSequence;
	
	/**
	 * Constructs a sequence from its {@link Alphabet} and {@link String}
	 * represenation
	 * 
	 * @param alphabet the alphabet
	 * @param sequence the sequence as {@link String}
	 */
	public SimpleSequence(Alphabet alphabet, String sequence) {
		super(alphabet);
		this.codedSequence = new int[sequence.length()];
		for(int i=0;i<sequence.length();i++) {
			this.codedSequence[i] = this.alphabet.getCode(sequence.charAt(i));
		}
	}

	@Override
	public int getCodeAt(int index) {
		return codedSequence[index];
	}
	
	@Override
	public int getLength() {
		return codedSequence.length;
	}
	
	
	
	
}
