package ueb7.b;

import ueb7.a.Alphabet;

/**
 * Interface of a sequence with alphabet and symbols from that alphabet.
 * Symbols are encoded as contiguous integer numbers to allow for
 * indexing arrays.
 * 
 * @author Jan Grau
 *
 */
public interface Sequence {

	/**
	 * Returns the integer code for the symbol at position index
	 * @param index the position
	 * @return the encoded symbol
	 */
	public int getCodeAt(int index);

	/**
	 * Returns the symbol at position index
	 * @param index the index
	 * @return the symbol
	 */
	public char getSymbolAt(int index);

	/**
	 * Returns the internal {@link Alphabet}
	 * @return the alphabet
	 */
	public Alphabet getAlphabet();

	/**
	 * Returns the length of this {@link Sequence}
	 * @return the length
	 */
	public int getLength();
	
	/**
	 * Returns a sub-sequence from start to end (exclusive)
	 * @param start the start position of the sub-sequence
	 * @param end the end position (exclusive) of the sub-sequence
	 * @return the sub-sequence
	 */
	public Sequence getSubSequence(int start, int end);
	
}