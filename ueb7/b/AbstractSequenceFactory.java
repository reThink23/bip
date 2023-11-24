package ueb7.b;

import ueb7.a.Alphabet;

/**
 * Super-class of the classes for creating sequence objects in an abstract factory
 * either creating {@link SimpleSequence} or {@link SparseDNASequence} objects.
 * 
 * @author Jan Grau
 *
 */
public abstract class AbstractSequenceFactory {

	/**
	 * Returns the sequence using the implementation defined by the
	 * sub-class
	 * @param alphabet the {@link Alphabet} of the created sequence
	 * @param sequence the {@link String} representation of the sequence
	 * @return the {@link Sequence} object
	 */
	public abstract Sequence createSequence(Alphabet alphabet, String sequence);
	
	
}
